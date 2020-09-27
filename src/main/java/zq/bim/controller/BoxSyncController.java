package zq.bim.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import zq.bim.dao.DeviceDao;
import zq.bim.dao.FileDataDao;
import zq.bim.dao.FileListDao;
import zq.bim.dao.SoftwareUpdateDao;
import zq.bim.dao.SoftwareUpdateRecordDao;
import zq.bim.entity.Device;
import zq.bim.entity.FileData;
import zq.bim.entity.FileList;
import zq.bim.entity.SoftwareUpdate;
import zq.bim.entity.SoftwareUpdateRecord;
import zq.bim.entity.dto.HeartbeatCheckDto;
import zq.bim.entity.dto.InitDataSyncDto;
import zq.bim.entity.dto.KeyDto;
import zq.bim.entity.dto.SoftwareUpdateCallbackDto;
import zq.bim.entity.dto.StreamCallbackDto;
import zq.bim.entity.dto.validate.DeviceNumberView;
import zq.bim.result.ReturnView;
import zq.bim.service.BoxSyncService;
import zq.bim.util.UserUtil;

@RestController
public class BoxSyncController {

	@Autowired
	private SoftwareUpdateDao softwareUpdateDao;
	
	@Autowired
	private SoftwareUpdateRecordDao softwareUpdateRecordDao;
	
	@Autowired
	private FileListDao fileListDao;
	
	@Autowired
	private FileDataDao fileDataDao;
	
	@Autowired
	private DeviceDao deviceDao;
	
	
	@Autowired
	private BoxSyncService boxSyncService;
	
	/***
	 * 拉取设备流数据
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/cmdKey",produces="application/json;charset=utf-8")
	public String cmdKey(@RequestBody String json) {
		KeyDto dto=UserUtil.validate(json, KeyDto.class,DeviceNumberView.class);
		String deviceNumber=dto.getDeviceNumber();
		String userLock = (deviceNumber).intern();
		synchronized (userLock) {
			Object rtn=boxSyncService.cmdKey(deviceNumber);
			return JSON.toJSONString(rtn);
		}
	}
	
	/****
	 * 发送设备/设备留地址是否在线/离线
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/sendStatus",produces="application/json;charset=utf-8")
	public String sendStatus(@RequestBody String json) {
		HeartbeatCheckDto dto=UserUtil.validate(json, HeartbeatCheckDto.class);
		
		ReturnView rtn=boxSyncService.sendStatus(dto);
		
		return rtn.toJson();
	}
	
	/***
	 * 设备流拉取回调
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/streamCallback",produces="application/json;charset=utf-8")
	public String streamCallback(@RequestBody String json) {
		System.out.println(json);
		StreamCallbackDto dto=UserUtil.validate(json, StreamCallbackDto.class);
		
		ReturnView rtn=boxSyncService.streamCallback(dto);
		
		return rtn.toJson();
	}
	
	/***
	 * 初始化 设备数据校对
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/initDataSync",produces="application/json;charset=utf-8")
	public String initDataSync(@RequestBody String json) {
		InitDataSyncDto dto=UserUtil.validate(json, InitDataSyncDto.class);
		
		Object rtn=boxSyncService.initDataSync(dto);
		
		return JSON.toJSONString(rtn);
	}
	
	
	/**
	 * 查询版本更新
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/selectSoftwareUpdate",produces="application/json;charset=utf-8")
	public String selectSoftwareUpdate(@RequestBody String json) {
		InitDataSyncDto dto=UserUtil.validate(json, InitDataSyncDto.class);
		String deviceNumber=dto.getDeviceNumber();
		String rtn=boxSyncService.selectSoftwareUpdate(deviceNumber);
		
		return rtn;
	}
	
	/****
	 * 设备更新回调
	 * @param json
	 * @return
	 */
	@RequestMapping(value="/softwareUpdateCallback",produces="application/json;charset=utf-8")
	public String softwareUpdateCallback(@RequestBody String json) {
		SoftwareUpdateCallbackDto dto=UserUtil.validate(json, SoftwareUpdateCallbackDto.class);
		
		Object rtn=boxSyncService.softwareUpdateCallback(dto);
		
		return JSON.toJSONString(rtn);
	}
	
	/***
	 * 更新包下载
	 * @param json
	 * @param response
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value="/softwareUpdateDownload",produces="application/json;charset=utf-8")
	public String softwareUpdateDownload(String equipmentId,HttpServletResponse response) {
		
	  Device device=deviceDao.selectByDeviceNumber(equipmentId);
	  
	  Long deviceId=device.getDeviceId();
	  
	  SoftwareUpdateRecord
	  record=softwareUpdateRecordDao.selectByDeviceId(deviceId, 3);
	  
	  Long updateId=record.getSoftwareUpdateId();
	  
	  SoftwareUpdate su=softwareUpdateDao.selectByPrimaryKey(updateId);
		 
		
	   String fileId=su.getFileId();
		
		//String fileId="2e068b5f47e84a298e6c4f7c2141e5f4";
		
		FileList fileList=fileListDao.selectByPrimaryKey(fileId);
		
		String fileMd5=fileList.getFileMd5();
		
		FileData fileData=fileDataDao.selectByPrimaryKey(fileMd5);
		
		String filePath=fileData.getFilePath();
		String fileName=fileList.getFileName();
		
		File file = new File(filePath);
		if (!file.exists()) {
			return ReturnView.error("文件不存在!").toString();
		}
		
		ServletOutputStream outputStream = null;	//响应流
		FileChannel channel =null;					//读取流
		
		fileName=encodeZH(fileName, "utf-8");
		response.addHeader("Content-disposition", "attachment; filename=\""+fileName+"\"");
		
		try {
			channel = new RandomAccessFile(file, "rw").getChannel();
			
			int step = 1024 * 1024;
			
			Long start=0L;
			Long end=fileList.getFileSize();
			ByteBuffer src = null;
			if(end < step){
				src=ByteBuffer.allocate(end.intValue());
			}else{
				src=ByteBuffer.allocate(step);
			}
			channel.position(start); 
			outputStream = response.getOutputStream();
			
			Long length=0L; 
			while ((channel.read(src)) != -1) {
				length=(end-start);
				if(length > step){
					length=(long) step;
				}
				outputStream.write(src.array(),0,length.intValue());
				src.clear();
				start += step;
				if (start >= end) {
					break;
				}
				channel.position(start); 

			}
			channel.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "download_fail";
		}finally{
			if(channel!=null){
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!= null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return "success";
	}
	
	
	private String encodeZH(String str,String charset){
		StringBuilder sb=new StringBuilder(str.length());
		for (int i = 0; i < str.length(); i++) {
			String c = str.charAt(i)+"";
			if(c.getBytes().length>1){
				try {
					sb.append(URLEncoder.encode(c,"utf-8"));
				} catch (UnsupportedEncodingException e) { 
					e.printStackTrace();
					sb.append(c);
				}
			}else{
				sb.append(c);
			}
			
		}
		return sb.toString();
	}
}

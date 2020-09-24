package zq.bim.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import zq.bim.dao.FileDataDao;
import zq.bim.dao.FileDataUploadDao;
import zq.bim.dao.FileListDao;
import zq.bim.entity.FileData;
import zq.bim.entity.FileDataUpload;
import zq.bim.entity.FileList;
import zq.bim.result.ReturnView;
import zq.bim.service.FileService;
import zq.bim.util.ApplicationUtil;
import zq.bim.util.FileUtil;
import zq.bim.util.ImageUtil;

@Service
public class FileServiceImpl implements FileService{
	
	
	private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
	
	private static final String lock_md5="lock_md5_insert";
	
	private static final String lock_update="lock_md5_update";
	
	@Autowired
	private FileDataDao fileDataDao;
	
	@Autowired
	private FileDataUploadDao fileDataUploadDao;
	
	@Autowired
	private FileListDao fileListDao;

	
	@Override
	public ReturnView insertFile(String fileName, Long fileSize, String fileMd5) {
		String fileId = ApplicationUtil.randomUUID();
		String createUserId = "00";
		Date createTime = new Date();
		String fileType = null;
		int index = fileName.lastIndexOf(".");
		int length = fileName.length();
		if(index != -1 && length > (index +1)){
			fileType = fileName.substring(index + 1);
		}
		fileName = fileName.replace(" ", "");
		FileList sgFileList = new FileList(fileId , "0", fileName, fileSize, fileType, fileMd5, createUserId, createTime);
		fileListDao.insertSelective(sgFileList);
		return ReturnView.success2(fileId);
	}
	
	@Override
	public ReturnView preparedUpload(String fileChildId, String fileMd5, Long fileSize, Long fileStep) throws IOException {

		if(StringUtils.isEmpty(fileMd5)||StringUtils.isEmpty(fileSize)||StringUtils.isEmpty(fileStep)){
			return ReturnView.error("参数错误!");
		}
		
		Date date=new Date();
		
		//判断是否是新的上传md5
		FileData fileData = fileDataDao.selectByPrimaryKey(fileMd5);
		
		if(fileData==null){
			synchronized (lock_md5) { 
				fileData = fileDataDao.selectByPrimaryKey(fileMd5);
				if(fileData == null){
					//插入数据
					String isDeleted = "0";
					String isFinished = "0";
					String filePath = FileUtil.createFilePath(fileMd5);
					Long writeByte = 0l;
					fileData=new FileData(fileMd5, isDeleted, isFinished, fileSize, filePath, writeByte , date);
					fileDataDao.insertSelective(fileData);
					//创建同大小的临时文件
					File file = new File(filePath); 
					if(!file.exists()){
						boolean ok = file.createNewFile();
						if(!ok){
							return ReturnView.error("请求失败,文件创建失败");
						} 
					}
					RandomAccessFile raf = new RandomAccessFile(file, "rw");
					raf.setLength(fileSize);
					raf.close();
					
				}
			}
			
		}else{
			String existPath = fileData.getFilePath();
			File existfile = new File(existPath);
			if(existfile.exists()){
				
			}else{
				//清除信息
				fileDataDao.deleteByPrimaryKey(fileMd5);
				fileDataUploadDao.deleteByFileMd5(fileMd5);
				
				synchronized (lock_md5) { 
					fileData = fileDataDao.selectByPrimaryKey(fileMd5);
					if(fileData == null){
						
						//插入数据
						String isDeleted = "0";
						String isFinished = "0";
						String filePath = FileUtil.createFilePath(fileMd5);
						Long writeByte = 0l;
						fileData=new FileData(fileMd5, isDeleted, isFinished, fileSize, filePath, writeByte , date);
						fileDataDao.insertSelective(fileData);
						//创建同大小的临时文件
						File file = new File(filePath); 
						if(!file.exists()){
							boolean ok = file.createNewFile();
							if(!ok){
								return ReturnView.error("请求失败,文件创建失败");
							} 
						}
						RandomAccessFile raf = new RandomAccessFile(file, "rw");
						raf.setLength(fileSize);
						raf.close();
					}
				}
			}
			
			
		}
		
		//判断该md5文件是否完成
		if("1".equalsIgnoreCase(fileData.getIsFinished())){
			return finish(fileMd5);
		}
		
		//新上传，还是续传
		if(StringUtils.isEmpty(fileChildId)){
			fileChildId=ApplicationUtil.randomUUID();
		}
		
		long complete = 0l;	//上传数据量
		return getStart(fileMd5, fileChildId, fileStep, complete ,fileSize);
	}

	@Override
	public ReturnView upload(MultipartFile file, String fileChildId, String fileMd5, Long start, Long fileStep) throws IOException {
		
		if(StringUtils.isEmpty(fileMd5)||StringUtils.isEmpty(start) ||StringUtils.isEmpty(fileStep)||StringUtils.isEmpty(fileChildId)){
			return  ReturnView.error("参数错误!");
		}
		
		FileData fileData = fileDataDao.selectByPrimaryKey(fileMd5);
		
		if(fileData == null){
			return  ReturnView.error("文件上传失败,所标示的文件不存在");
		}
		
		long fileSize = fileData.getFileSize();
		String filePath = fileData.getFilePath();

		File newFile = new File(filePath);
		if(!newFile.exists()){
			return  ReturnView.error("文件上传失败,该文件不存在");
		}
		/**
		 * 文件已经上传成功
		 */
		if("1".equalsIgnoreCase(fileData.getIsFinished())){
			return finish(fileMd5);
		}
		
		//写入数据
		FileDataUpload fileDataUpload = fileDataUploadDao.selectByPrimaryKey(fileChildId);
		if(fileDataUpload == null){ 
			return  ReturnView.error("文件上传失败,该传输进度不存在");
		}
		
		long complete = 0l;
		if("0".equalsIgnoreCase(fileDataUpload.getIsFinished())){
			if(file!=null && file.getSize()>0){
				@SuppressWarnings("resource")
				FileChannel fc = new RandomAccessFile(newFile, "rw").getChannel();
				ByteBuffer src=ByteBuffer.wrap(file.getBytes());
				fc.position(start);
				fc.write(src);
				src.clear();
				fc.close();
				complete= file.getSize();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}else{
			//当前数据完成，当前请求休眠30秒
			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		if(file!=null && file.getSize()>0){
			complete = file.getSize();
		}
		return getStart(fileMd5, fileChildId, fileStep, complete, fileSize);
	}
	
	
	
	/**
	 * 文件上传完成 
	 */
	private ReturnView finish(String fileMd5){
		FileData data = fileDataDao.selectByPrimaryKey(fileMd5);
		if(data == null){
			
			return ReturnView.error("文件上传失败，不存在该fileMd5文件");
		}
		
		
		fileDataUploadDao.finish(fileMd5);
		data.setIsFinished("1");
		fileDataDao.updateByPrimaryKeySelective(data);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", 0);  
		
		FileList fileList = fileListDao.selectByMd5(fileMd5);
		String fileType = fileList.getFileType();
		String filePath = data.getFilePath();
		Boolean isThumbnail=false;
		if("JPEG".equalsIgnoreCase(fileType) || "BMP".equalsIgnoreCase(fileType) || "GIF".equalsIgnoreCase(fileType) 
				|| "PNG".equalsIgnoreCase(fileType) || "jpg".equalsIgnoreCase(fileType) || "svg".equalsIgnoreCase(fileType) ){
			isThumbnail = true;
		}
		
		//调用压缩
		if(isThumbnail != null && isThumbnail){
			log.error("调用压缩");
			thumbnail(filePath);
			compress(filePath);
			log.error("调用压缩结束");
		}
		//调用缩略
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ReturnView.success(map);
	}
	
	
	/**
	 * 返回上传信息
	 * @return
	 */
	private ReturnView getStart(String fileMd5,String fileChildId,long fileStep,long complete,long fileSize){
		long start=0l; 
		int status=2;
		synchronized (lock_update) {
			FileDataUpload fileDataUpload = fileDataUploadDao.selectByPrimaryKey(fileChildId);
			if(fileDataUpload ==null){
				//新上传
				String isFinished = "0";
				Long byteBegin = 0l;
				Long byteEnd = 0l;
				Long byteCount = 0l;
				Date updateTime = new Date();
				fileDataUpload = new FileDataUpload(fileChildId, fileMd5, isFinished, byteBegin, byteEnd, byteCount, updateTime);
				fileDataUploadDao.insertSelective(fileDataUpload);
			}
			
			FileData fileData = fileDataDao.selectByPrimaryKey(fileMd5); 
			//当前进度上传位置
			start = fileData.getWriteByte();
			//完成调用操作
			if("1".equalsIgnoreCase(fileData.getIsFinished())){
				return finish(fileMd5);
			}
			
			if(start >= fileSize){
				//当前进度上传完成，不需要在上传
				if("1".equalsIgnoreCase(fileDataUpload.getIsFinished())){
					//等待其他进度
				}else{
					//完成当前进度
					fileDataUpload.setUpdateTime(new Date());	
					fileDataUpload.setByteCount(fileDataUpload.getByteCount()+complete);	//已完成字节
					fileDataUpload.setIsFinished("1");
					fileDataUploadDao.updateByPrimaryKeySelective(fileDataUpload); 
				}
				
				//判断是否全部上传完成
				if(fileDataUploadDao.judgeFinish(fileMd5)==0){
					return finish(fileMd5);
				}
				
				//寻找可替换的上传进度
				FileDataUpload newUpload = fileDataUploadDao.getNewUpload(fileMd5);
				if(newUpload!=null){
					start =newUpload.getByteBegin();
					fileChildId = newUpload.getUploadId();
				}else{
					status=1;
				}
				
				
			}else{
				//当前进度继续上传
				fileDataUpload.setByteBegin(start);
				fileDataUpload.setUpdateTime(new Date());	
				fileDataUpload.setByteCount(fileDataUpload.getByteCount()+complete);	//已完成字节
				fileDataUploadDao.updateByPrimaryKeySelective(fileDataUpload);
				
				//下一次进度上传位置
				long newStart= start + fileStep;
				if(newStart >= fileSize) newStart = fileSize;
				fileData.setWriteByte(newStart);
				fileDataDao.updateByPrimaryKeySelective(fileData);
			}
			
			
		}
		
		//统计完成的进度
		Long  num = fileDataUploadDao.sumFinish(fileMd5); 
		if(num==null) num=0l;
		double progress = num  * 100.0/fileSize;
		
		if(progress > 100){
			progress = 100;
		}
		
		//返回结果
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("status", status);	//0、成功	1、等待他人上传	2、上传中
		map.put("progress", progress);
		map.put("fileChildId", fileChildId);
		map.put("start", start); 
		return ReturnView.success(map);
	}
	
	private void thumbnail(String path){
		try {
			 File file = new File(path);
			if(!file.exists()){
				return;
			}
			
			if(file.length() < 500*1024L){
				//小于500kb不压缩
				return;
			}
			String srcPath = path;
			
			String newPath = path+"_thumbnail";
			if( new File(newPath).exists()){
				return;	//已存在，不需要缩略
			}
			int width = 256;
			int height	= 256;
			int type = 1;	//压缩类型，1、等比	2、百分比
			String quality = "100";	//压缩质量
			ImageUtil.thumbnail(srcPath, newPath, width, height, type, quality );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//压缩
		private void compress(String path){
			try {
				File srcFile = new File(path);
				if(!srcFile.exists()){
					//文件不存在，
					return;
				}
				if(srcFile.length() < 500*1024L){
					//小于500kb不压缩
					return;
				}
				
				String srcPath = path;
				String newPath = path+"_compress";
				File file = new File(newPath);
				if(file.exists()){
					return;	//已存在，不需要压缩
				}
				
				int width = 1920;
				int height	= 1080;
				int type = 1;	//压缩类型，1、等比	2、百分比
				String quality = "90";	//压缩质量
				ImageUtil.thumbnail(srcPath, newPath, width, height, type, quality );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}

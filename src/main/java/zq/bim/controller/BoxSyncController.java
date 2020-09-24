package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import zq.bim.entity.dto.HeartbeatCheckDto;
import zq.bim.entity.dto.InitDataSyncDto;
import zq.bim.entity.dto.KeyDto;
import zq.bim.entity.dto.StreamCallbackDto;
import zq.bim.entity.dto.validate.DeviceNumberView;
import zq.bim.result.ReturnView;
import zq.bim.service.BoxSyncService;
import zq.bim.util.UserUtil;

@RestController
public class BoxSyncController {

	
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
}

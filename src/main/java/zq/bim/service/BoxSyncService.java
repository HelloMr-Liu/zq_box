package zq.bim.service;

import zq.bim.entity.dto.HeartbeatCheckDto;
import zq.bim.entity.dto.InitDataSyncDto;
import zq.bim.entity.dto.SoftwareUpdateCallbackDto;
import zq.bim.entity.dto.StreamCallbackDto;
import zq.bim.result.ReturnView;

public interface BoxSyncService {
	
	/****
	 * 同步平台设备流地址数据
	 * @return
	 */
	public Object cmdKey(String deviceNumber);
	
	/***
	 * 同步设备/流状态
	 * @return
	 */
	public ReturnView sendStatus(HeartbeatCheckDto dto);
	
	/***
	 * 同步设备流数据的回调接口
	 * @return
	 */
	public ReturnView streamCallback(StreamCallbackDto dto);
	
	/***
	 * 设备启动后 数据校对
	 * @param dto
	 * @return
	 */
	public Object initDataSync(InitDataSyncDto dto);
	
	
	/**
	 * 查询设备更新情况
	 * @return
	 */
	public String selectSoftwareUpdate(String deviceNumber);
	
	
	/***
	 * 设备更新回调
	 * @param dto
	 * @return
	 */
	public Object softwareUpdateCallback(SoftwareUpdateCallbackDto dto);
}

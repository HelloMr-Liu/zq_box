package zq.bim.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dreamlu.mica.core.utils.BeanUtil;
import zq.bim.constant.DEVICE_RELEVANCE_STATE;
import zq.bim.constant.STREAM_OP_STATE;
import zq.bim.dao.DeviceDao;
import zq.bim.dao.DeviceStreamDao;
import zq.bim.entity.Device;
import zq.bim.entity.DeviceStream;
import zq.bim.entity.dto.HeartbeatCheckDto;
import zq.bim.entity.dto.StreamCallbackDto;
import zq.bim.entity.dto.StreamStatusDto;
import zq.bim.entity.vo.DeviceStreamSyncVo;
import zq.bim.result.ReturnView;
import zq.bim.service.BoxSyncService;

@Service
public class BoxSyncServiceImpl implements BoxSyncService{
	
	@Autowired
	private DeviceStreamDao deviceStreamDao;
	
	@Autowired
	private DeviceDao deviceDao;

	@Override
	public ReturnView cmdKey(String deviceTag) {
		
		Device device=deviceDao.selectByDeviceNumber(deviceTag);
		if(device==null) {
			return ReturnView.error("设备不存在!");
		}
		
		Long deviceId=device.getDeviceId();
		
		List<DeviceStream> streamList=deviceStreamDao.selectUpdateData(deviceId);
		
		List<DeviceStreamSyncVo> voList=BeanUtil.copy(streamList, DeviceStreamSyncVo.class);
		
		List<Long> streamIds=voList.stream().map(DeviceStreamSyncVo::getStreamId).collect(Collectors.toList());
		
		if(!streamIds.isEmpty()) {
			deviceStreamDao.updateOpStatus(streamIds, STREAM_OP_STATE.operation_USEING.STATE);
		}
		
		return ReturnView.success(voList);
	}

	@Override
	public ReturnView sendStatus(HeartbeatCheckDto dto) {
		
		String deviceNumber=dto.getDeviceNumber();
		
		List<StreamStatusDto> streamDtos=dto.getStreamList();
		
		Device device=deviceDao.selectByDeviceNumber(deviceNumber);
		
		Long deviceId=device.getDeviceId();
		
		//更新设备心跳时间
		Device record=new Device();
		record.setDeviceId(deviceId);
		record.setHeartbeatTime(new Date());
		record.setRelevanceStatus(DEVICE_RELEVANCE_STATE.YES.STATE);
		deviceDao.updateByPrimaryKeySelective(record);
		
		List<Map<String,Object>> listMap=new ArrayList<>();
		List<Long> streamIds=new ArrayList<Long>();
		if(streamDtos!=null && streamDtos.size()>0) {
			
			streamDtos.forEach(s->{
				Long streamId=s.getStreamId();
				Boolean status=s.getStatus();
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("streamId", streamId);
				map.put("status", status);
				listMap.add(map);
				
				streamIds.add(streamId);
			});
			//更新状态
			deviceStreamDao.updateStreamStatus(listMap);
		}
		return ReturnView.success("操作成功!");
	}

	@Override
	public ReturnView streamCallback(StreamCallbackDto dto) {
		
		String deviceNumber=dto.getDeviceNumber();
		Long streamId=dto.getStreamId();
		Boolean status=dto.getStatus();
		Boolean opSuccess=dto.getOpSuccess();
		
		DeviceStream stream=deviceStreamDao.selectByPrimaryKey(streamId);
		
		DeviceStream record=new DeviceStream();
		record.setStreamId(streamId);
		record.setLiefStatus(status);
		record.setOpStatus(STREAM_OP_STATE.OPERATION_NONE.STATE);
		
		deviceStreamDao.updateByPrimaryKeySelective(record);
		
		return ReturnView.success("操作成功!");
	}
}

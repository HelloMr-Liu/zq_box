package zq.bim.entity.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

public class InitDataSyncDto {
	
	@JSONField(name="equipment_id")
	@NotNull(message = "设备号不能为空")
	private String deviceNumber;
	
	@JSONField(name="stream_list")
	private List<StreamDto> streamList;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public List<StreamDto> getStreamList() {
		return streamList;
	}

	public void setStreamList(List<StreamDto> streamList) {
		this.streamList = streamList;
	}
}

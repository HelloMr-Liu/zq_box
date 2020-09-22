package zq.bim.entity.dto;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class HeartbeatCheckDto {
	
	@JSONField(name="equipment_id")
	private String deviceNumber;
	
	private String cpu;
	
	private String memory;
	
	private String disk;
	
	private String temperature;
	
	private List<StreamStatusDto> streamList;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public List<StreamStatusDto> getStreamList() {
		return streamList;
	}

	public void setStreamList(List<StreamStatusDto> streamList) {
		this.streamList = streamList;
	}
}

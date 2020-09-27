package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class SoftwareUpdateCallbackDto {
	
	@JSONField(name="equipment_id")
	private String deviceNumber;
	
	@JSONField(name="op_success")
	private Boolean opSuccess;
	
	private String msg;
	
	@JSONField(name="version")
	private Long softwareUpdateId;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Boolean getOpSuccess() {
		return opSuccess;
	}

	public void setOpSuccess(Boolean opSuccess) {
		this.opSuccess = opSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getSoftwareUpdateId() {
		return softwareUpdateId;
	}

	public void setSoftwareUpdateId(Long softwareUpdateId) {
		this.softwareUpdateId = softwareUpdateId;
	}
}

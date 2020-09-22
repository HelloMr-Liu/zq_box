package zq.bim.entity.dto;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

public class StreamCallbackDto {
	
	@JSONField(name="equipment_id")
	private String deviceNumber;
	
	@JSONField(name="stream_id")
	@NotNull(message = "流编号不能为空")
	private Long streamId;
	
	@NotNull(message = "流状态不能为空")
	private Boolean status;
	
	@JSONField(name="op_success")
	private Boolean opSuccess;

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getOpSuccess() {
		return opSuccess;
	}

	public void setOpSuccess(Boolean opSuccess) {
		this.opSuccess = opSuccess;
	}
}

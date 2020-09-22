package zq.bim.entity.dto;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

public class StreamStatusDto {
	
	@JSONField(name="stream_id")
	@NotNull(message = "流编号不能为空")
	private Long streamId;
	
	@NotNull(message = "流状态不能为空")
	private Boolean status;

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

}

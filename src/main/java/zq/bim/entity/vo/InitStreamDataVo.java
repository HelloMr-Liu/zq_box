package zq.bim.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class InitStreamDataVo {

	@JSONField(name="stream_id")
	private Long streamId;
	
    private Integer pid;

	@JSONField(name="pull_url")
    private String pullStreamAddress;

	@JSONField(name="push_url")
    private String pushStreamAddress;

    private Boolean status;

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPullStreamAddress() {
		return pullStreamAddress;
	}

	public void setPullStreamAddress(String pullStreamAddress) {
		this.pullStreamAddress = pullStreamAddress;
	}

	public String getPushStreamAddress() {
		return pushStreamAddress;
	}

	public void setPushStreamAddress(String pushStreamAddress) {
		this.pushStreamAddress = pushStreamAddress;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public InitStreamDataVo(Long streamId, Integer pid, String pullStreamAddress, String pushStreamAddress,
			Boolean status) {
		super();
		this.streamId = streamId;
		this.pid = pid;
		this.pullStreamAddress = pullStreamAddress;
		this.pushStreamAddress = pushStreamAddress;
		this.status = status;
	}

	public InitStreamDataVo() {
		super();
		// TODO Auto-generated constructor stub
	}
}

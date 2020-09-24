package zq.bim.entity.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


public class DeviceStreamSyncVo {

	@JSONField(name="stream_id")
	private Long streamId;
	
	@JSONField(serialize = false)
    private String deviceId;

	@JSONField(name="pull_url")
    private String pullStreamAddress;

	@JSONField(name="push_url")
    private String pushStreamAddress;

	@JSONField(name="op_type")
    private Integer opStatus;

    @JSONField(serialize = false)
    private Date createTime;

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public Integer getOpStatus() {
		return opStatus;
	}

	public void setOpStatus(Integer opStatus) {
		this.opStatus = opStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

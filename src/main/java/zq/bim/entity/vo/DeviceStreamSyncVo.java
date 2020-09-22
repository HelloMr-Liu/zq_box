package zq.bim.entity.vo;

import java.util.Date;


public class DeviceStreamSyncVo {

	private Long streamId;

    private String deviceId;

    private String pullStreamAddress;

    private String pushStreamAddress;

    private Integer opStatus;

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

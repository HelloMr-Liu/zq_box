package zq.bim.entity;

import java.util.Date;

public class SoftwareUpdateRecord {
    private Long id;

    private Long deviceId;

    private Long softwareUpdateId;

    private Integer status;

    private Date createTime;

    private Long createUserId;
    
    private String msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getSoftwareUpdateId() {
        return softwareUpdateId;
    }

    public void setSoftwareUpdateId(Long softwareUpdateId) {
        this.softwareUpdateId = softwareUpdateId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
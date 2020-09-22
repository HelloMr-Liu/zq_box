package zq.bim.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceStream {
    private Long streamId;

    private Long projectId;

    private String deviceId;

    private String pullStreamAddress;

    private String pushStreamAddress;

    private String playAddress;

    private Integer opStatus;

    private Boolean liefStatus;

    @JSONField(serialize = false)
    private Boolean isDeleted;

    private Date updateTime;

    private Date createTime;

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getPullStreamAddress() {
        return pullStreamAddress;
    }

    public void setPullStreamAddress(String pullStreamAddress) {
        this.pullStreamAddress = pullStreamAddress == null ? null : pullStreamAddress.trim();
    }

    public String getPushStreamAddress() {
        return pushStreamAddress;
    }

    public void setPushStreamAddress(String pushStreamAddress) {
        this.pushStreamAddress = pushStreamAddress == null ? null : pushStreamAddress.trim();
    }

    public String getPlayAddress() {
        return playAddress;
    }

    public void setPlayAddress(String playAddress) {
        this.playAddress = playAddress == null ? null : playAddress.trim();
    }

    public Integer getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(Integer opStatus) {
        this.opStatus = opStatus;
    }

    public Boolean getLiefStatus() {
        return liefStatus;
    }

    public void setLiefStatus(Boolean liefStatus) {
        this.liefStatus = liefStatus;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
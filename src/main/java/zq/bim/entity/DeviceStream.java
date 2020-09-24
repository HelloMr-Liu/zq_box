package zq.bim.entity;

import java.util.Date;

public class DeviceStream {
    private Long streamId;

    private Long projectId;

    private Long deviceId;

    private String streamName;

    private String pullStreamAddress;

    private String pushStreamAddress;

    private String playAddress;

    private Integer opStatus;

    private Boolean liefStatus;

    private Boolean isDeleted;

    private Long createUserId;

    private Date createTime;

    private Date updateTime;

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName == null ? null : streamName.trim();
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

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
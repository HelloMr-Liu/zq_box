package zq.bim.entity.dto;

import java.util.Date;

/**
 * 功能：映射设备信息表
 * 作者：刘梓江
 * 时间：2020/9/21  16:29
 */
public class DeviceDTO {

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 设备标识
     */
    private String deviceNumber;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备描述
     */
    private String deviceDesc;

    /**
     * 设备状态
     */
    private Boolean deviceStatus;

    /**
     * 关联状态
     */
    private Boolean relevanceStatus;

    /**
     * 心跳检测时间
     */
    private Date heartbeatTime;

    private Boolean isDeleted;

    private Date createTime;

    private Date updateTime;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber == null ? null : deviceNumber.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc == null ? null : deviceDesc.trim();
    }

    public Boolean getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Boolean getRelevanceStatus() {
        return relevanceStatus;
    }

    public void setRelevanceStatus(Boolean relevanceStatus) {
        this.relevanceStatus = relevanceStatus;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
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
    
    
    
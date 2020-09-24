package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 功能：封装设备流属性
 * 作者：刘梓江
 * 时间：2020/9/22  13:44
 */
public class DeviceStreamDefinition {

    /**
     * 流id
     */
    private Long streamId;

    /**
     * 流名称
     */
    private String streamName;

    /**
     * 拉流地址
     */
    private String pullStreamAddress;

    /**
     * 推流地址
     */
    private String pushStreamAddress;

    /**
     * 播放地址
     */
    private String playAddress;

    /**
     * 流状态 (离线、在线)
     */
    private String liefStatus;

    /**
     * 流状态嘛
     */
    private String liefStatuCode;

    public String getLiefStatuCode() {
        return liefStatuCode;
    }

    public void setLiefStatuCode(String liefStatuCode) {
        this.liefStatuCode = liefStatuCode;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
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

    public String getPlayAddress() {
        return playAddress;
    }

    public void setPlayAddress(String playAddress) {
        this.playAddress = playAddress;
    }

    public String getLiefStatus() {
        return liefStatus;
    }

    public void setLiefStatus(String liefStatus) {
        this.liefStatus = liefStatus;
    }
}
    
    
    
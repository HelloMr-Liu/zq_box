package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能：接收设备流属性
 * 作者：刘梓江
 * 时间：2020/9/21  16:01
 */
public class ParameterDeviceStreamDefinition {

    /**
     * 设备id
     */
    @NotNull(message = "设备id为空",groups = {AddValid.class})
    private Long deviceId;


    /**
     * 项目id
     */
    @NotNull(message = "项目id为空",groups = {AddValid.class})
    private Long projectId;


    /**
     * 流id
     */
    @NotNull(message = "流id为空",groups = {UpdateValid.class})
    private Long streamId;

    /**
     * 拉流地址
     */
    @NotBlank(message = "拉流地址为空",groups = {AddValid.class,UpdateValid.class})
    private String pullStreamAddress;

    /**
     * 推流地址
     */
    @NotBlank(message = "推流地址为空",groups = {AddValid.class,UpdateValid.class})
    private String pushStreamAddress;

    /**
     * 播放地址
     */
    @NotBlank(message = "播放地址为空",groups = {AddValid.class,UpdateValid.class})
    private String playAddress;


    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStreamId() {
        return streamId;
    }

    public void setStreamId(Long streamId) {
        this.streamId = streamId;
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
}
    
    
    
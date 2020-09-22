package zq.bim.entity.dto.delete;

import zq.bim.valid.DeleteValid;

import javax.validation.constraints.NotNull;

/**
 * 功能：接收删除设备流属性
 * 作者：刘梓江
 * 时间：2020/9/21  16:06
 */
public class DeleteDeviceStreamDefinition extends DeleteGlobalDefinition {

    /**
     * 设备id
     */
    @NotNull(message = "设备id为空",groups = {DeleteValid.class})
    private Long deviceId;


    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
    
    
    
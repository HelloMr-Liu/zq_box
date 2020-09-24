package zq.bim.entity.dto.query;

import zq.bim.valid.QueryValid;
import javax.validation.constraints.NotNull;

/**
 * 功能：封装查询项目属性
 * 作者：刘梓江
 * 时间：2020/9/21  15:57
 */
public class QueryDeviceStreamDefinition extends QueryGlobalDefinition{

    /**
     * 设备id
     */
    @NotNull(message = "设备id为空",groups = {QueryValid.class})
    private Long deviceId;

    /**
     * 流名称
     */
    private String streamName;

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}

    
    
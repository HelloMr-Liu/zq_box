package zq.bim.entity.dto.query;

import zq.bim.valid.QueryValid;

import javax.validation.constraints.NotNull;

/**
 * 功能：封装查询设备属性
 * 作者：刘梓江
 * 时间：2020/9/21  15:57
 */
public class QueryDeviceDefinition extends QueryGlobalDefinition{

    /**
     * 项目id
     */
    @NotNull(message = "项目id为空",groups = {QueryValid.class})
    private Long projectId;

    /**
     * 设备名称
     */
    private String deviceName;

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
        this.deviceName = deviceName;
    }
}

    
    
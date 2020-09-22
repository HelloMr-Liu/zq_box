package zq.bim.entity.dto.delete;

import zq.bim.valid.DeleteValid;

import javax.validation.constraints.NotNull;

/**
 * 功能：接收删除设备属性
 * 作者：刘梓江
 * 时间：2020/9/21  16:06
 */
public class DeleteDeviceDefinition extends DeleteGlobalDefinition {


    /**
     * 项目id
     */
    @NotNull(message = "项目id为空",groups = {DeleteValid.class})
    private Long projectId;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
    
    
    
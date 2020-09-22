package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 功能：接收项目属性
 * 作者：刘梓江
 * 时间：2020/9/21  16:01
 */
public class ParameterProjectDefinition {

    /**
     * 项目id
     */
    @NotNull(message = "项目id为空",groups = {QueryValid.class,UpdateValid.class})
    private Long projectId;

    /**
     * 项目名称
     */
    @Length(message = "项目名称长度不能超过50",groups = {AddValid.class, UpdateValid.class})
    @NotBlank(message = "项目名称为空",groups = {AddValid.class, UpdateValid.class})
    private String projectName;


    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
    
    
    
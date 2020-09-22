package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 功能：封装项目属性
 * 作者：刘梓江
 * 时间：2020/9/21  17:00
 */
public class ProjectDefinition {

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
    
    
    
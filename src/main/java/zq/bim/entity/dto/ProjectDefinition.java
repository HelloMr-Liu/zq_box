package zq.bim.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 项目负责人
     */
    private String projectLeader;

    /**
     * 项目账号
     */
    private String projectAccount;

    /**
     * 项目访问密码
     */
    @JSONField(serialize = false)
    private String projectAccessPass;


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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectAccount() {
        return projectAccount;
    }

    public void setProjectAccount(String projectAccount) {
        this.projectAccount = projectAccount;
    }

    public String getProjectAccessPass() {
        return projectAccessPass;
    }

    public void setProjectAccessPass(String projectAccessPass) {
        this.projectAccessPass = projectAccessPass;
    }
}
    
    
    
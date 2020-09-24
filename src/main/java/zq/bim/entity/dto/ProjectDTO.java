package zq.bim.entity.dto;

import java.util.Date;

/**
 *  功能：映射项目信息表
 *  作者：刘梓江
 *  时间：2020/9/22 17:33
 */
public class ProjectDTO {

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 项目账号
     */
    private String projectAccount;

    /**
     * 项目扶着人
     */
    private String projectLeader;

    /**
     * 项目访问密码
     */
    private String projectAccessPass;

    private Date createTime;

    private Boolean isDeleted;

    private Date updateTime;

    private Long createUserId;

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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
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
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getProjectAccount() {
        return projectAccount;
    }

    public void setProjectAccount(String projectAccount) {
        this.projectAccount = projectAccount == null ? null : projectAccount.trim();
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader == null ? null : projectLeader.trim();
    }

    public String getProjectAccessPass() {
        return projectAccessPass;
    }

    public void setProjectAccessPass(String projectAccessPass) {
        this.projectAccessPass = projectAccessPass == null ? null : projectAccessPass.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
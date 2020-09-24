package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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

    /**
     * 电话号码
     */
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[1-9]))\\d{8}$", message = "请输入正确的手机号",groups = {AddValid.class,UpdateValid.class})
    @NotBlank(message = "电话号码为空",groups = {AddValid.class,UpdateValid.class})
    private String projectAccount;

    /**
     * 项目负责人
     */
    @Length(message = "项目负责人长度不能超过50",groups = {AddValid.class, UpdateValid.class})
    @NotBlank(message = "项目负责人为空",groups = {AddValid.class, UpdateValid.class})
    private String projectLeader;

//    /**
//     * 项目访问密码
//     */
//    @Length(message = "项目访问密码长度不能超过50",max = 50,groups = {UpdateValid.class})
//    @NotBlank(message = "项目访问密码为空",groups = {UpdateValid.class})
//    private String projectAccessPass;

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String fileName;

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

    public String getProjectAccount() {
        return projectAccount;
    }

    public void setProjectAccount(String projectAccount) {
        this.projectAccount = projectAccount;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
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
}
    
    
    
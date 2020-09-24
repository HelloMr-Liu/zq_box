package zq.bim.entity.dto;

/**
 * 功能：映射用户信息表额外扩展信息
 * 作者：刘梓江
 * 时间：2020/9/22  15:28
 */
public class UserExtendDTO extends UserDTO {
    /**
     * 项目名称
     */
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
    
    
    
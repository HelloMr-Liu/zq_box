package zq.bim.entity.dto.query;

/**
 * 功能：封装查询项目属性
 * 作者：刘梓江
 * 时间：2020/9/21  15:57
 */
public class QueryProjectDefinition extends QueryGlobalDefinition{

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

    
    
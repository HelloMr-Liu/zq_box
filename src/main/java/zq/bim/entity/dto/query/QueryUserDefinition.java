package zq.bim.entity.dto.query;

/**
 * 功能：封装查询设备属性
 * 作者：刘梓江
 * 时间：2020/9/21  15:57
 */
public class QueryUserDefinition extends QueryGlobalDefinition{

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 用户账号
     */
    private String userAccount;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
}

    
    
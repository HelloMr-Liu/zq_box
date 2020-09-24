package zq.bim.entity.dto;

/**
 * 功能：封装用户属性
 * 作者：刘梓江
 * 时间：2020/9/22  18:55
 */
public class UserDefinition {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPass;

    /**
     * 用户类型 普通人员  管理人员
     */
    private String userType;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
    
    
    
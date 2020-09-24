package zq.bim.entity.dto;

/**
 * 功能：封装用户登录成功属性
 * 作者：刘梓江
 * 时间：2020/7/24  10:42
 */
public class UserLoginOKDefinition {
    /**
     * 用户id或项目id
     */
    private Long  uniqueId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 登录成功标识
     */
    private String accessToken;

    /**
     * 用户类型 1:前台用户 2：后台普通用户 3：后台高级用户
     */
    private String userType;


    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

    
    
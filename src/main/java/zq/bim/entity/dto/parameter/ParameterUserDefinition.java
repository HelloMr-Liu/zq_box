package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 功能：接收用户信息属性
 * 作者：刘梓江
 * 时间：2020/9/22  15:37
 */
public class ParameterUserDefinition {

    /**
     * 用户id
     */
    @NotNull(message = "用户id为空",groups = {QueryValid.class,UpdateValid.class})
    private Long userId;

    /**
     * 用户名称
     */
    @NotBlank(message = "用户名称为空",groups = {AddValid.class,UpdateValid.class})
    private String userName;

    /**
     * 电话号码
     */
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[1-9]))\\d{8}$", message = "请输入正确的手机号",groups = {AddValid.class,UpdateValid.class})
    @NotBlank(message = "电话号码为空",groups = {AddValid.class,UpdateValid.class})
    private String userAccount;

    /**
     * 用户类型：普通人员  管理人员
     */
    @NotBlank(message = "用户类型为空",groups = {AddValid.class,UpdateValid.class})
    private String userType;

//    /**
//     * 用户密码
//     */
//    @Length(message = " 用户密码长度不能超过50",max = 50,groups = {UpdateValid.class})
//    @NotBlank(message = "用户密码为空",groups = {UpdateValid.class})
//    private String userPass;

//
//    public String getUserPass() {
//        return userPass;
//    }
//    public void setUserPass(String userPass) {
//        this.userPass = userPass;
//    }
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
    
    
    
package zq.bim.entity.dto.parameter;

import org.hibernate.validator.constraints.Length;
import zq.bim.valid.AddValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 功能：接收登录属性
 * 作者：刘梓江
 * 时间：2020/9/22  19:18
 */
public class ParameterLoginDefinition {

    /**
     * 登录类型
     */
    @Pattern(regexp = "^1|2$", message = "登录类型异常 1:用户端登录  2:后台端登录 ")
    @NotBlank(message = "登录类型为空")
    private String loginType;

    /**
     * 电话号码
     */
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[1-9]))\\d{8}$", message = "请输入正确的手机号",groups = {AddValid.class, UpdateValid.class})
    @NotBlank(message = "电话号码为空")
    private String userAccount;

    /**
     * 用户密码
     */
    @Length(message = " 用户密码长度不能超过50",max = 50)
    @NotBlank(message = "用户密码为空")
    private String userPass;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
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
}
    
    
    
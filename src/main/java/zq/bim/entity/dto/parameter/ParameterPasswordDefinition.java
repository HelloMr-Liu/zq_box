package zq.bim.entity.dto.parameter;

import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 功能：封装操作密码属性
 * 作者：刘梓江
 * 时间：2020/9/23  19:00
 */
public class ParameterPasswordDefinition {

    /**
     * 操作类型
     */
    @Pattern(regexp = "^1|2$", message = "操作类型异常 1:项目  2:用户 ",groups = {AddValid.class})
    @NotBlank(message = "操作类型为空",groups = {AddValid.class})
    private String operationType;

    /**
     * 是否操作当前账户 1:是  0：不是
     */
    @Pattern(regexp = "^1|2$", message = "账户类型异常 1:是  0:不是 ",groups = {UpdateValid.class})
    @NotBlank(message = "账户类型为空",groups = {UpdateValid.class})
    private String isCurrentAccount;

    /**
     * 操作密码
     */
    @NotBlank(message = "密码不能为空",groups = {QueryValid.class})
    private String password;

    /**
     * 唯一id
     */
    @NotNull(message = "唯一id不能为空",groups = {AddValid.class})
    private Long uniqueId;

    /**
     * 操作密码token     查看密码  修改密码
     */
    @NotBlank(message = "操作token",groups = {AddValid.class,UpdateValid.class})
    private String token;

    public String getIsCurrentAccount() {
        return isCurrentAccount;
    }

    public void setIsCurrentAccount(String isCurrentAccount) {
        this.isCurrentAccount = isCurrentAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
    
    
    
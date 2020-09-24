package zq.bim.entity.dto.parameter;

import zq.bim.valid.UpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 功能：封装修改账户信息属性
 * 作者：刘梓江
 * 时间：2020/9/24  10:05
 */
public class ParameterAccountInfoDefinition {

    /**
     * 修改类型
     */
    @Pattern(regexp = "^1|2$", message = "修改类型 1:用户名称  2:账户 ",groups = {UpdateValid.class})
    @NotBlank(message = "修改为空",groups = {UpdateValid.class})
    private String updateType;

    /**
     * 操作内容
     */
    @NotBlank(message = "操作内容为空",groups = {UpdateValid.class})
    private String updateContent;


    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
    
    
    
package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.annotations.AccessToken;
import zq.bim.entity.dto.parameter.ParameterLoginDefinition;
import zq.bim.entity.dto.parameter.ParameterPasswordDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.OperatingPasswordService;
import zq.bim.util.UserUtil;
import zq.bim.valid.AddValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

/**
 * 功能：操作密码请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/23  20:52
 */
@RestController
@RequestMapping("/box/password")
public class OperatingPasswordController {

    @Autowired
    private OperatingPasswordService passwordService;

    /**
     * 查看密码是否一致
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/check/pass",produces="application/json;charset=utf-8")
    public String operatingPasswordConsistency(String json){
        ParameterPasswordDefinition definition = UserUtil.validate(json, ParameterPasswordDefinition.class, QueryValid.class);
        ReturnView one = passwordService.operatingPasswordConsistency(definition);
        return one.toJson();
    }

    /**
     * 查看显示密码
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/pass",produces="application/json;charset=utf-8")
    public String showPass(String json){
        ParameterPasswordDefinition definition = UserUtil.validate(json, ParameterPasswordDefinition.class, AddValid.class);
        ReturnView one = passwordService.showPass(definition);
        return one.toJson();
    }

    /**
     * 更新密码
     * @return
     */
    @AccessToken
    @RequestMapping(value="/update/pass",produces="application/json;charset=utf-8")
    public String updatePass(String json){
        ParameterPasswordDefinition definition = UserUtil.validate(json, ParameterPasswordDefinition.class, UpdateValid.class);
        ReturnView one = passwordService.updatePass(definition);
        return one.toJson();
    }
}
    
    
    
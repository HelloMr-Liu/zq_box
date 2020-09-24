package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.entity.dto.parameter.ParameterLoginDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.LoginService;
import zq.bim.util.UserUtil;

/**
 * 功能：登录请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/22  19:59
 */
@RestController
@RequestMapping("/box/access")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * @param json
     * @return
     */
    @RequestMapping(value="/login",produces="application/json;charset=utf-8")
    public String login(String json){
        ParameterLoginDefinition definition = UserUtil.validate(json, ParameterLoginDefinition.class);
        ReturnView one = loginService.login(definition);
        return one.toJson();
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value="/login/out",produces="application/json;charset=utf-8")
    public String loginOut(){
        ReturnView one = loginService.loginOut();
        return one.toJson();
    }
}
    
    
    
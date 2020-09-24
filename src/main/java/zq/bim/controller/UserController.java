package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.annotations.AccessToken;
import zq.bim.entity.dto.delete.DeleteUserDefinition;
import zq.bim.entity.dto.parameter.ParameterAccountInfoDefinition;
import zq.bim.entity.dto.parameter.ParameterUserDefinition;
import zq.bim.entity.dto.query.QueryUserDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.UserService;
import zq.bim.util.UserUtil;
import zq.bim.valid.AddValid;
import zq.bim.valid.DeleteValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

/**
 * 功能：用户请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/22  19:15
 */
@RestController
@RequestMapping("/box/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取指定信息
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/one",produces="application/json;charset=utf-8")
    public String findOne(String json){
        ParameterUserDefinition definition = UserUtil.validate(json, ParameterUserDefinition.class, QueryValid.class);
        ReturnView one = userService.findOne(definition);
        return one.toJson();
    }

    /**
     * 条件查询对应的信息列表
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/all/query",produces="application/json;charset=utf-8")
    public String findAllByQuery(String json){
        QueryUserDefinition definition = UserUtil.validate(json, QueryUserDefinition.class, QueryValid.class);
        ReturnView findAllByQuery = userService.findAllByQuery(definition);
        return findAllByQuery.toJson();
    }

    /**
     * 添加
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/add",produces="application/json;charset=utf-8")
    public String add(String json){
        ParameterUserDefinition definition = UserUtil.validate(json, ParameterUserDefinition.class, AddValid.class);
        ReturnView add = userService.add(definition);
        return add.toJson();
    }

    /**
     * 修改
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/update",produces="application/json;charset=utf-8")
    public String update(String json){
        ParameterUserDefinition definition = UserUtil.validate(json, ParameterUserDefinition.class, UpdateValid.class);
        ReturnView update = userService.update(definition);
        return update.toJson();
    }

    /**
     * 修改账户
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/update/account",produces="application/json;charset=utf-8")
    public String updateAccount(String json){
        ParameterAccountInfoDefinition definition = UserUtil.validate(json, ParameterAccountInfoDefinition.class, UpdateValid.class);
        ReturnView update = userService.updateAccount(definition);
        return update.toJson();
    }
    /**
     * 删除
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/delete/ids",produces="application/json;charset=utf-8")
    public String deleteByIds(String json){
        DeleteUserDefinition definition = UserUtil.validate(json, DeleteUserDefinition.class, DeleteValid.class);
        ReturnView deleteByIds = userService.deleteByIds(definition);
        return deleteByIds.toJson();
    }
}
    
    
    
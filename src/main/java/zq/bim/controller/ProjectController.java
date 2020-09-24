package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.annotations.AccessToken;
import zq.bim.entity.dto.HeartbeatCheckDto;
import zq.bim.entity.dto.delete.DeleteProjectDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceStreamDefinition;
import zq.bim.entity.dto.parameter.ParameterProjectDefinition;
import zq.bim.entity.dto.parameter.ParameterUserDefinition;
import zq.bim.entity.dto.query.QueryProjectDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.ProjectService;
import zq.bim.util.UserUtil;
import zq.bim.valid.AddValid;
import zq.bim.valid.DeleteValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

/**
 * 功能：项目请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/22  14:31
 */
@RestController
@RequestMapping("/box/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 获取指定信息
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/one",produces="application/json;charset=utf-8")
    public String findOne(String json){
        ParameterProjectDefinition definition = UserUtil.validate(json, ParameterProjectDefinition.class, QueryValid.class);
        ReturnView one = projectService.findOne(definition);
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
        QueryProjectDefinition definition = UserUtil.validate(json, QueryProjectDefinition.class, QueryValid.class);
        ReturnView findAllByQuery = projectService.findAllByQuery(definition);
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
        ParameterProjectDefinition definition = UserUtil.validate(json, ParameterProjectDefinition.class, AddValid.class);
        ReturnView add = projectService.add(definition);
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
        ParameterProjectDefinition definition = UserUtil.validate(json,ParameterProjectDefinition.class, UpdateValid.class);
        ReturnView update = projectService.update(definition);
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
        DeleteProjectDefinition definition = UserUtil.validate(json, DeleteProjectDefinition.class, DeleteValid.class);
        ReturnView deleteByIds = projectService.deleteByIds(definition);
        return deleteByIds.toJson();
    }
}
    
    
    
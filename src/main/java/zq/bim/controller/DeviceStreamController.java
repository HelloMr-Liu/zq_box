package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.annotations.AccessToken;
import zq.bim.entity.dto.delete.DeleteDeviceStreamDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceStreamDefinition;
import zq.bim.entity.dto.query.QueryDeviceStreamDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.DeviceStreamService;
import zq.bim.util.UserUtil;
import zq.bim.valid.AddValid;
import zq.bim.valid.DeleteValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

/**
 * 功能：设备流请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/22  14:44
 */
@RestController
@RequestMapping("/box/stream")
public class DeviceStreamController {
    
    @Autowired
    private DeviceStreamService streamService;

    /**
     * 获取指定信息
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/one",produces="application/json;charset=utf-8")
    public String findOne(String json){
        ParameterDeviceStreamDefinition definition = UserUtil.validate(json, ParameterDeviceStreamDefinition.class, QueryValid.class);
        ReturnView one = streamService.findOne(definition);
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
        QueryDeviceStreamDefinition definition = UserUtil.validate(json, QueryDeviceStreamDefinition.class, QueryValid.class);
        ReturnView findAllByQuery = streamService.findAllByQuery(definition);
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
        ParameterDeviceStreamDefinition definition = UserUtil.validate(json, ParameterDeviceStreamDefinition.class, AddValid.class);
        ReturnView add = streamService.add(definition);
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
        ParameterDeviceStreamDefinition definition = UserUtil.validate(json, ParameterDeviceStreamDefinition.class, UpdateValid.class);
        ReturnView update = streamService.update(definition);
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
        DeleteDeviceStreamDefinition definition = UserUtil.validate(json, DeleteDeviceStreamDefinition.class, DeleteValid.class);
        ReturnView deleteByIds = streamService.deleteByIds(definition);
        return deleteByIds.toJson();
    }
}
    
    
    
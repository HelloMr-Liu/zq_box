package zq.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.bim.annotations.AccessToken;
import zq.bim.entity.dto.delete.DeleteDeviceDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceDefinition;
import zq.bim.entity.dto.query.QueryDeviceDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.DeviceService;
import zq.bim.util.UserUtil;
import zq.bim.valid.AddValid;
import zq.bim.valid.DeleteValid;
import zq.bim.valid.QueryValid;
import zq.bim.valid.UpdateValid;

/**
 * 功能：设备请求接口控制器
 * 作者：刘梓江
 * 时间：2020/9/22  14:41
 */
@RestController
@RequestMapping("/box/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 获取指定信息
     * @param json
     * @return
     */
    @AccessToken
    @RequestMapping(value="/find/one",produces="application/json;charset=utf-8")
    public String findOne(String json){
        ParameterDeviceDefinition definition = UserUtil.validate(json, ParameterDeviceDefinition.class, QueryValid.class);
        ReturnView one = deviceService.findOne(definition);
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
        QueryDeviceDefinition definition = UserUtil.validate(json, QueryDeviceDefinition.class, QueryValid.class);
        ReturnView findAllByQuery = deviceService.findAllByQuery(definition);
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
        ParameterDeviceDefinition definition = UserUtil.validate(json, ParameterDeviceDefinition.class, AddValid.class);
        ReturnView add = deviceService.add(definition);
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
        ParameterDeviceDefinition definition = UserUtil.validate(json, ParameterDeviceDefinition.class, UpdateValid.class);
        ReturnView update = deviceService.update(definition);
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
        DeleteDeviceDefinition definition = UserUtil.validate(json, DeleteDeviceDefinition.class, DeleteValid.class);
        ReturnView deleteByIds = deviceService.deleteByIds(definition);
        return deleteByIds.toJson();
    }
}
    
    
    
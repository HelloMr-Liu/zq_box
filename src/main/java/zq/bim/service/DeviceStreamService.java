package zq.bim.service;

import zq.bim.entity.dto.delete.DeleteDeviceStreamDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceStreamDefinition;
import zq.bim.entity.dto.query.QueryDeviceStreamDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：操作设备流业务接口
 * 作者：刘梓江
 * 时间：2020/9/22  13:43
 */
public interface DeviceStreamService {

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterDeviceStreamDefinition definition);

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryDeviceStreamDefinition definition);

    /**
     * 添加
     * @param definition
     * @return
     */
    public ReturnView add(ParameterDeviceStreamDefinition definition);

    /**
     * 修改
     * @param definition
     * @return
     */
    public ReturnView update(ParameterDeviceStreamDefinition definition);

    /**
     * 删除
     * @param definition
     * @return
     */
    public ReturnView deleteByIds(DeleteDeviceStreamDefinition definition);
}

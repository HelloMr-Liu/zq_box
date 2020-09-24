package zq.bim.service;

import zq.bim.entity.dto.delete.DeleteDeviceDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceDefinition;
import zq.bim.entity.dto.query.QueryDeviceDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：操作设备业务接口
 * 作者：刘梓江
 * 时间：2020/9/22  13:16
 */
public interface DeviceService {

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterDeviceDefinition definition);

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryDeviceDefinition definition);

    /**
     * 添加
     * @param definition
     * @return
     */
    public ReturnView add(ParameterDeviceDefinition definition);

    /**
     * 修改
     * @param definition
     * @return
     */
    public ReturnView update(ParameterDeviceDefinition definition);

    /**
     * 删除
     * @param definition
     * @return
     */
    public ReturnView deleteByIds(DeleteDeviceDefinition definition);
}

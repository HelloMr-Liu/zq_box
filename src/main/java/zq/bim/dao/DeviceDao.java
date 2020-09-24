package zq.bim.dao;

import org.apache.ibatis.annotations.Param;
import zq.bim.entity.Device;
import zq.bim.entity.DeviceStream;
import zq.bim.entity.SystemOperationLogWithBLOBs;
import zq.bim.entity.dto.DeviceDTO;
import zq.bim.entity.dto.delete.DeleteDeviceDefinition;
import zq.bim.entity.dto.query.QueryDeviceDefinition;

import java.util.List;
import java.util.Map;

public interface DeviceDao {
    int deleteByPrimaryKey(Long deviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long deviceId);
    
    Device selectByDeviceNumber(String deviceNumber);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);


    /**
     * 获取已关联设备的有效设备数量
     * @param parameters
     * @return
     */
    int findRelevanceDeviceNumber(Map<String,Object> parameters);

    /**
     * 获取指定信息
     * @param deviceId 设备id
     * @return
     */
    DeviceDTO findOne(Long deviceId);

    /**
     * 条件查询列表信息
     * @param definition
     * @return
     */
    List<DeviceDTO> findByQuery(QueryDeviceDefinition definition);

    /**
     * 获取已激活的所有设备情况
     * @param parameters
     * @return
     */
    List<DeviceDTO> findAllOnActivation(Map<String,Object> parameters);

    /**
     * 查看设备内容是否重复
     * @param parameters
     * @return
     */
    DeviceDTO checkContentIsExist(Map<String,Object> parameters);

    /**
     * 批量删除信息
     * @param definition
     */
    void batchDelete(DeleteDeviceDefinition definition);

    /**
     * 批量更新设备状态
     * @param propertyValues
     */
    void batchUpdateDeviceState(@Param("contents") List<Device> propertyValues);

}
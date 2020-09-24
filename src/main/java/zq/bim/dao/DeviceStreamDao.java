package zq.bim.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import zq.bim.entity.DeviceStream;
import zq.bim.entity.dto.DeviceDTO;
import zq.bim.entity.dto.DeviceStreamDTO;
import zq.bim.entity.dto.StreamStatusDto;
import zq.bim.entity.dto.delete.DeleteDeviceDefinition;
import zq.bim.entity.dto.delete.DeleteDeviceStreamDefinition;
import zq.bim.entity.dto.query.QueryDeviceDefinition;
import zq.bim.entity.dto.query.QueryDeviceStreamDefinition;

public interface DeviceStreamDao {
    int deleteByPrimaryKey(Long streamId);

    int insert(DeviceStream record);

    int insertSelective(DeviceStream record);

    DeviceStream selectByPrimaryKey(Long streamId);
    
    int updateByPrimaryKeySelective(DeviceStream record);

    int updateByPrimaryKey(DeviceStream record);
    
    List<DeviceStream> selectUpdateData(Long deviceId);
    
    void updateStreamStatus(@Param("list") List<Map<String,Object>> list);
    
    void updateOpStatus(@Param("list") List<Long> streamIds,@Param("status") Integer status);
    
    List<DeviceStream> selectByDeviceId(Long deviceId);


    /**
     * 获取有效设备流数量
     * @param parameters
     * @return
     */
    int findValidDeviceStreamNumber(Map<String,Object> parameters);

    /**
     * 获取指定信息
     * @param deviceId 设备id
     * @return
     */
    DeviceStreamDTO findOne(Long deviceId);

    /**
     * 条件查询列表信息
     * @param definition
     * @return
     */
    List<DeviceStreamDTO> findByQuery(QueryDeviceStreamDefinition definition);

    /**
     * 查看信息是否重复
     * @param parameters
     * @return
     */
    DeviceStreamDTO checkContentIsExist(Map<String,Object> parameters);

    /**
     * 批量删除信息
     * @param definition
     */
    void batchDelete(DeleteDeviceStreamDefinition definition);
}
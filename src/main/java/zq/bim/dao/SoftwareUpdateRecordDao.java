package zq.bim.dao;

import org.apache.ibatis.annotations.Param;

import zq.bim.entity.SoftwareUpdateRecord;

public interface SoftwareUpdateRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(SoftwareUpdateRecord record);

    int insertSelective(SoftwareUpdateRecord record);

    SoftwareUpdateRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SoftwareUpdateRecord record);

    int updateByPrimaryKey(SoftwareUpdateRecord record);
    
    SoftwareUpdateRecord selectByDeviceIdAndUpdateId(@Param("deviceId") Long deviceId,
    		@Param("softwareUpdateId") Long softwareUpdateId,@Param("status") Integer stataus);
    
    SoftwareUpdateRecord selectUpdateIng(@Param("deviceId") Long deviceId,
    		@Param("softwareUpdateId") Long softwareUpdateId);
    
    
    SoftwareUpdateRecord selectByDeviceId(@Param("deviceId") Long deviceId,@Param("status") Integer stataus);
}
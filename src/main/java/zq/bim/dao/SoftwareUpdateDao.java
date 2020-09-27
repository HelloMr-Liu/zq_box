package zq.bim.dao;

import zq.bim.entity.SoftwareUpdate;

public interface SoftwareUpdateDao {
    int deleteByPrimaryKey(Long id);

    int insert(SoftwareUpdate record);

    int insertSelective(SoftwareUpdate record);

    SoftwareUpdate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SoftwareUpdate record);

    int updateByPrimaryKey(SoftwareUpdate record);
    
    SoftwareUpdate selectMaxData();
}
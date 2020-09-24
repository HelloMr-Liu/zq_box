package zq.bim.dao;

import zq.bim.entity.FileData;

public interface FileDataDao {
    int deleteByPrimaryKey(String fileMd5);

    int insert(FileData record);

    int insertSelective(FileData record);

    FileData selectByPrimaryKey(String fileMd5);

    int updateByPrimaryKeySelective(FileData record);

    int updateByPrimaryKey(FileData record);
}
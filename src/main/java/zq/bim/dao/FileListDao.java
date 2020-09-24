package zq.bim.dao;

import zq.bim.entity.FileList;

public interface FileListDao {
    int deleteByPrimaryKey(String fileId);

    int insert(FileList record);

    int insertSelective(FileList record);

    FileList selectByPrimaryKey(String fileId);

    int updateByPrimaryKeySelective(FileList record);

    int updateByPrimaryKey(FileList record);
    
    FileList selectByMd5(String md5);
}
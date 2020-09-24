package zq.bim.dao;

import zq.bim.entity.FileDataUpload;

public interface FileDataUploadDao {
    int deleteByPrimaryKey(String uploadId);

    int insert(FileDataUpload record);

    int insertSelective(FileDataUpload record);

    FileDataUpload selectByPrimaryKey(String uploadId);

    int updateByPrimaryKeySelective(FileDataUpload record);

    int updateByPrimaryKey(FileDataUpload record);
    
    int deleteByFileMd5(String fileMd5);
    
    void finish(String fileMd5);
    
    /**
     * 判断上传完成
     * @param fileMd5  md5码
     * @return 0、上传完成
     */
    int judgeFinish(String fileMd5);
    
    
    /**
     * 获取需要替代上传的数据
     * @param fileMd5
     * @return
     */
    FileDataUpload getNewUpload(String fileMd5);
    
    
    /**
     * 获取统计完成的量
     * @param fileMd5
     * @return
     */
    Long sumFinish(String fileMd5);
    
    
}
package zq.bim.dao;

import org.apache.ibatis.annotations.Param;
import zq.bim.entity.SystemOperationLog;
import zq.bim.entity.SystemOperationLogWithBLOBs;

import java.util.List;

/**
 *  功能：系统操作日志表(system_operation_log)数据扩展接口
 *  作者：刘梓江
 *  时间：2020/9/22 16:50
 */
public interface SystemOperationLogDao {
    int deleteByPrimaryKey(Long logId);

    int insert(SystemOperationLogWithBLOBs record);

    int insertSelective(SystemOperationLogWithBLOBs record);

    SystemOperationLogWithBLOBs selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(SystemOperationLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SystemOperationLogWithBLOBs record);

    int updateByPrimaryKey(SystemOperationLog record);

    void batchInsert(@Param("contents") List<SystemOperationLogWithBLOBs> propertyValues);
}
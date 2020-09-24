package zq.bim.dao;

import zq.bim.entity.Project;
import zq.bim.entity.dto.ProjectDTO;
import zq.bim.entity.dto.delete.DeleteProjectDefinition;
import zq.bim.entity.dto.query.QueryProjectDefinition;

import java.util.HashMap;
import java.util.List;

public interface ProjectDao {
    int deleteByPrimaryKey(Long projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Long projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);


    /**
     * 获取指定信息
     * @param projectId
     * @return
     */
    ProjectDTO findOne(Long projectId);

    /**
     * 获取项目信息
     * @param parameterMap
     * @return
     */
    ProjectDTO findProjectByAccountAndPass(HashMap<String,Object> parameterMap);

    /**
     * 查看名称是否存在
     * @param parameterMap
     * @return
     */
    ProjectDTO checkContentIsExist(HashMap<String,Object> parameterMap);

    /**
     * 按照条件获取信息列表
     * @param definition
     * @return
     */
    List<ProjectDTO> findByQuery(QueryProjectDefinition definition);

    /**
     * 批量修改(删除)
     * @param definition
     */
    void batchDelete(DeleteProjectDefinition definition);


}
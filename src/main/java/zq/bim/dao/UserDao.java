package zq.bim.dao;

import zq.bim.entity.User;
import zq.bim.entity.dto.ProjectDTO;
import zq.bim.entity.dto.UserDTO;
import zq.bim.entity.dto.delete.DeleteUserDefinition;
import zq.bim.entity.dto.query.QueryUserDefinition;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 获取指定信息
     * @param userId
     * @return
     */
    UserDTO findOne(Long userId);

    /**
     * 获取用户信息
     * @param parameterMap
     * @return
     */
    UserDTO findUserByAccountAndPass(HashMap<String,Object> parameterMap);

    /**
     * 查看名称是否存在
     * @param parameterMap
     * @return
     */
    UserDTO checkContentIsExist(HashMap<String,Object> parameterMap);

    /**
     * 按照条件获取信息列表
     * @param definition
     * @return
     */
    List<UserDTO> findByQuery(QueryUserDefinition definition);

    /**
     * 批量修改(删除)
     * @param definition
     */
    void batchDelete(DeleteUserDefinition definition);
}
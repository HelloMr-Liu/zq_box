package zq.bim.service;

import zq.bim.entity.dto.delete.DeleteUserDefinition;
import zq.bim.entity.dto.parameter.ParameterAccountInfoDefinition;
import zq.bim.entity.dto.parameter.ParameterUserDefinition;
import zq.bim.entity.dto.query.QueryUserDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：用户信息业务接口
 * 作者：刘梓江
 * 时间：2020/9/22  18:52
 */
public interface UserService {

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterUserDefinition definition);

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryUserDefinition definition);


    /**
     * 添加
     * @param definition
     * @return
     */
    public ReturnView add(ParameterUserDefinition definition);

    /**
     * 修改
     * @param definition
     * @return
     */
    public ReturnView update(ParameterUserDefinition definition);

    /**
     * 修改账户
     * @param definition
     * @return
     */
    public ReturnView updateAccount(ParameterAccountInfoDefinition definition);

    /**
     * 删除
     * @param definition
     * @return
     */
    public ReturnView deleteByIds(DeleteUserDefinition definition);
}

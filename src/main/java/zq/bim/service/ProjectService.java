package zq.bim.service;

import zq.bim.entity.dto.delete.DeleteProjectDefinition;
import zq.bim.entity.dto.parameter.ParameterProjectDefinition;
import zq.bim.entity.dto.query.QueryProjectDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：描述当前接口
 * 作者：刘梓江
 * 时间：2020/9/21  15:35
 */
public interface ProjectService {

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterProjectDefinition definition);

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryProjectDefinition definition);

    /**
     * 添加
     * @param definition
     * @return
     */
    public ReturnView add(ParameterProjectDefinition definition);

    /**
     * 修改
     * @param definition
     * @return
     */
    public ReturnView update(ParameterProjectDefinition definition);

    /**
     * 删除
     * @param definition
     * @return
     */
    public ReturnView deleteByIds(DeleteProjectDefinition definition);
}

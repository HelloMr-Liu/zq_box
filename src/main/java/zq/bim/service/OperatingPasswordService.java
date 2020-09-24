package zq.bim.service;

import zq.bim.entity.dto.parameter.ParameterPasswordDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：操作密码业务接口
 * 作者：刘梓江
 * 时间：2020/9/23  19:45
 */
public interface OperatingPasswordService {

    /**
     * 查看密码是否一致
     * @return
     */
    public ReturnView operatingPasswordConsistency(ParameterPasswordDefinition definition);

    /**
     * 查看显示密码
     * @param definition
     * @return
     */
    public ReturnView showPass(ParameterPasswordDefinition definition);

    /**
     * 更新密码
     * @param definition
     * @return
     */
    public ReturnView updatePass(ParameterPasswordDefinition definition);
}

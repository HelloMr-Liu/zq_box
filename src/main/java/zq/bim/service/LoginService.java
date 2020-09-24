package zq.bim.service;

import zq.bim.entity.dto.parameter.ParameterLoginDefinition;
import zq.bim.result.ReturnView;

/**
 * 功能：登录业务接口
 * 作者：刘梓江
 * 时间：2020/9/22  19:17
 */
public interface LoginService {

    /**
     * 用户登录
     * @param definition
     * @return
     */
    public ReturnView login(ParameterLoginDefinition definition);

    /**
     * 退出登录
     * @return
     */
    public ReturnView loginOut();

}

package zq.bim.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import zq.bim.constant.GLOBAL_STATE;
import zq.bim.constant.REDIS_KEY;
import zq.bim.constant.USER_TABLE_STATE;
import zq.bim.dao.ProjectDao;
import zq.bim.dao.UserDao;
import zq.bim.entity.dto.ProjectDTO;
import zq.bim.entity.dto.UserDTO;
import zq.bim.entity.dto.UserLoginOKDefinition;
import zq.bim.entity.dto.parameter.ParameterLoginDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.LoginService;
import zq.bim.util.ApplicationUtil;
import zq.bim.util.EncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 功能：登录业务接口实现
 * 作者：刘梓江
 * 时间：2020/9/22  19:23
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户登录
     * @param definition
     * @return
     */
    public ReturnView login(ParameterLoginDefinition definition){
        UserLoginOKDefinition userLoginOKDefinition=new UserLoginOKDefinition();
        //判断登录类型
        if(definition.getLoginType().equals("1")){
            //用户端登录
            HashMap<String,Object> parameterMap=new HashMap<>();
            parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL.STATE);
            parameterMap.put("projectAccount",definition.getUserAccount());
            parameterMap.put("projectAccessPass", EncryptionUtil.getEncryptString(definition.getUserPass()));
            ProjectDTO projectInfo = projectDao.findProjectByAccountAndPass(parameterMap);
            if(projectInfo==null){
                return ReturnView.error("账户密码错误");
            }
            userLoginOKDefinition.setUniqueId(projectInfo.getProjectId());          //项目id
            userLoginOKDefinition.setUserName(projectInfo.getProjectLeader());      //项目负责人
            userLoginOKDefinition.setUserAccount(projectInfo.getProjectAccount());  //项目账号
            userLoginOKDefinition.setUserType("1");                                 //用户类型
        }else{
            //后台端登录
            HashMap<String,Object> parameterMap=new HashMap<>();
            parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL.STATE);
            parameterMap.put("userAccount",definition.getUserAccount());
            parameterMap.put("userPass",EncryptionUtil.getEncryptString(definition.getUserPass()));
            UserDTO userInfo = userDao.findUserByAccountAndPass(parameterMap);
            if(userInfo==null){
                return ReturnView.error("账户密码错误");
            }
            userLoginOKDefinition.setUniqueId(userInfo.getUserId());                //用户id
            userLoginOKDefinition.setUserName(userInfo.getUserName());              //用户名称
            userLoginOKDefinition.setUserAccount(userInfo.getUserAccount());        //用户账号
            if(userInfo.getUserType().equals(USER_TABLE_STATE.USER_TYPE_T1.STATE)){
                userLoginOKDefinition.setUserType("2");                                 //用户类型 普通人员
            }else{
                userLoginOKDefinition.setUserType("3");                                 //用户类型 管理人员
            }
        }
        //将用户信息存储到缓存中有效token一个小时
        String token= ApplicationUtil.getUUID();
        userLoginOKDefinition.setAccessToken(token);
        redisTemplate.opsForValue().set(REDIS_KEY.PREFIX.LOGIN_SUCCESS+userLoginOKDefinition.getUniqueId(),token,3600, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(token,JSON.toJSONString(userLoginOKDefinition),3600, TimeUnit.SECONDS);
        return ReturnView.success(userLoginOKDefinition);
    }

    /**
     * 退出登录
     * @return
     */
    public ReturnView loginOut(){
        Object token = request.getHeader("token");
        if(token!=null){
            Object tokenUser = redisTemplate.opsForValue().get(token);
            if(tokenUser!=null){
                //转换用户信息
                UserLoginOKDefinition userLoginOKDefinition = JSON.parseObject(tokenUser.toString(), UserLoginOKDefinition.class);
                redisTemplate.delete(REDIS_KEY.PREFIX.LOGIN_SUCCESS+userLoginOKDefinition.getUniqueId());
                redisTemplate.delete(token.toString());
            }
        }
        return ReturnView.success("退出成功");
    }
}
    
    
    
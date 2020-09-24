package zq.bim.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import zq.bim.constant.REQUEST_MESS;
import zq.bim.dao.ProjectDao;
import zq.bim.dao.UserDao;
import zq.bim.entity.Project;
import zq.bim.entity.User;
import zq.bim.entity.dto.ProjectDTO;
import zq.bim.entity.dto.UserDTO;
import zq.bim.entity.dto.UserLoginOKDefinition;
import zq.bim.entity.dto.parameter.ParameterPasswordDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.OperatingPasswordService;
import zq.bim.util.ApplicationUtil;
import zq.bim.util.EncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 功能：操作密码业务接口实现
 * 作者：刘梓江
 * 时间：2020/9/23  20:05
 */
@Service
public class OperatingPasswordServiceImpl implements OperatingPasswordService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 查看密码是否一致
     * @return
     */
    public ReturnView operatingPasswordConsistency(ParameterPasswordDefinition definition){
        if(StringUtils.isEmpty(definition.getPassword())){
            return ReturnView.error("密码不能为空");
        }
        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());
        if(userLoginInfo.getUserType().equals("1")){
            //代表当前的唯一id是项目id回去当前项目信息
            ProjectDTO one = projectDao.findOne(userLoginInfo.getUniqueId());
            if(!one.getProjectAccessPass().equals(EncryptionUtil.getEncryptString(definition.getPassword()))){
                return ReturnView.error("密码错误");
            }
        }else{
            //比较当前用户对应的原密码是否一致
            UserDTO one = userDao.findOne(userLoginInfo.getUniqueId());
            if(!one.getUserPass().equals(EncryptionUtil.getEncryptString(definition.getPassword()))){
                return ReturnView.error("密码错误");
            }
        }
        //获取一个修改令牌信息存储到缓存中 一分钟
        String updateToken= ApplicationUtil.getUUID();
        redisTemplate.opsForValue().set(updateToken, updateToken,60, TimeUnit.SECONDS);
        return ReturnView.success(updateToken);
    }

    /**
     * 查看显示密码
     * @param definition
     * @return
     */
    public ReturnView showPass(ParameterPasswordDefinition definition){
        //获取令牌信息
        String token = redisTemplate.opsForValue().get(definition.getToken());
        if(!StringUtils.isEmpty(token)){
            String password="";
            if(definition.getOperationType().equals("1")){
                //获取项目信息
                ProjectDTO one = projectDao.findOne(definition.getUniqueId());
                if(one!=null){
                    //对密码反解密码
                    password=EncryptionUtil.getDecryptString(one.getProjectAccessPass());
                }
            }else{
                //获取用户信息
                UserDTO one = userDao.findOne(definition.getUniqueId());
                if(one!=null){
                    //对密码反解密码
                    password=EncryptionUtil.getDecryptString(one.getUserPass());
                }
            }
            redisTemplate.delete(token);
            return ReturnView.success(password);
        }else{
            return ReturnView.error("令牌失效");
        }
    }

    /**
     * 更新密码
     * @param definition
     * @return
     */
    @Transactional(rollbackFor =Exception.class)
    public ReturnView updatePass(ParameterPasswordDefinition definition){
        //获取令牌信息
        String token = redisTemplate.opsForValue().get(definition.getToken());
        if(!StringUtils.isEmpty(token)){
            UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());

            //是否修改当前账户
            if(definition.getIsCurrentAccount().equals("1")){
                if(StringUtils.isEmpty(definition.getPassword()))return ReturnView.error("密码不能为空");

                //加密当前新密码
                String currentNewPass=EncryptionUtil.getEncryptString(definition.getPassword());

                //修改当前账户(可能是项目、可能是用户)
                ProjectDTO one = projectDao.findOne(userLoginInfo.getUniqueId());
                if(one!=null){
                    //代表当前账户是项目账户所以修改项目访问密码
                    Project project=new Project();
                    project.setProjectId(one.getProjectId());
                    project.setProjectAccessPass(currentNewPass);
                    project.setUpdateTime(new Date());
                    projectDao.updateByPrimaryKeySelective(project);
                }else{
                    UserDTO one1 = userDao.findOne(userLoginInfo.getUniqueId());
                    if(one1!=null){
                        //代表当前账户是用户账户所以修改用户访问密码
                        User user=new User();
                        user.setUserId(one1.getUserId());
                        user.setUserPass(currentNewPass);
                        user.setUpdateTime(new Date());
                        userDao.updateByPrimaryKeySelective(user);
                    }
                }
            }else{
                if(StringUtils.isEmpty(definition.getOperationType())||definition.getUniqueId()==null)return ReturnView.error("操作类型或唯一id不能为空");

                //判断当前用户是否是后台用户信息
                if(!userLoginInfo.getUserType().equals("1")){
                    String newPass=EncryptionUtil.getEncryptString(ApplicationUtil.getStringRandom(8));
                    //代表在后台中修改项目密码或账户密码
                    if(definition.getOperationType().equals("1"))   {
                        //代表修改的是项目密码
                        ProjectDTO one = projectDao.findOne(definition.getUniqueId());
                        Project project=new Project();
                        project.setProjectId(one.getProjectId());
                        project.setProjectAccessPass(newPass);
                        project.setUpdateTime(new Date());
                        projectDao.updateByPrimaryKeySelective(project);
                    }else{
                        //代表修改的是账户密码
                        UserDTO one1 = userDao.findOne(definition.getUniqueId());
                        if(one1!=null){
                            //代表当前账户是用户账户所以修改用户访问密码
                            User user=new User();
                            user.setUserId(one1.getUserId());
                            user.setUserPass(newPass);
                            user.setUpdateTime(new Date());
                            userDao.updateByPrimaryKeySelective(user);
                        }
                    }
                }
            }
            redisTemplate.delete(token);
            return ReturnView.success("密码修改成功");
        }else{
            return ReturnView.error("令牌失效");
        }
    }
}
    
    
    
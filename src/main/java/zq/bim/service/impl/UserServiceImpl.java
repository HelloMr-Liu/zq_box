package zq.bim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zq.bim.constant.GLOBAL_STATE;
import zq.bim.constant.REQUEST_MESS;
import zq.bim.constant.USER_TABLE_STATE;
import zq.bim.dao.ProjectDao;
import zq.bim.dao.UserDao;
import zq.bim.entity.Project;
import zq.bim.entity.User;
import zq.bim.entity.dto.*;
import zq.bim.entity.dto.delete.DeleteUserDefinition;
import zq.bim.entity.dto.parameter.ParameterAccountInfoDefinition;
import zq.bim.entity.dto.parameter.ParameterUserDefinition;
import zq.bim.entity.dto.query.QueryUserDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.UserService;
import zq.bim.util.ApplicationUtil;
import zq.bim.util.EncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 功能：描述当前类
 * 作者：刘梓江
 * 时间：2020/9/22  18:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterUserDefinition definition){
        UserDefinition userDefinition=new UserDefinition();
        UserDTO one = userDao.findOne(definition.getUserId());
        if(one!=null){
            userDefinition.setUserId(one.getUserId());                                          //用户id
            userDefinition.setUserName(one.getUserName());                                      //用户名称
            userDefinition.setUserAccount(one.getUserAccount());                                //用户账号
            //userDefinition.setUserPass(definition.getUserPass());                             //用户密码
            userDefinition.setUserType(USER_TABLE_STATE.getInstance(one.getUserType()).MESS);   //用户类型
        }
        return ReturnView.success(userDefinition);
    }

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryUserDefinition definition){
        //存储分页用户信息
        PageBeanDefinition<UserDefinition> pageBeans=new PageBeanDefinition<>();
        PageHelper.startPage(definition.getCurrentPage(),definition.getPageSize());
        List<UserDTO> users = userDao.findByQuery(definition);
        if(!CollectionUtils.isEmpty(users)){
            PageInfo<UserDTO> pageInfo=new PageInfo<>(users);
            //获取分页用户信息并二次封装
            for(UserDTO user:users){
                UserDefinition userDefinition=new UserDefinition();
                userDefinition.setUserId(user.getUserId());                                             //用户id
                userDefinition.setUserName(user.getUserName());                                         //用户名称
                userDefinition.setUserAccount(user.getUserAccount());                                   //用户账号
                //userDefinition.setUserPass(user.getUserPass());                                         //用户密码
                userDefinition.setUserType(USER_TABLE_STATE.getInstance(user.getUserType()).MESS);      //用户类型
                pageBeans.getList().add(userDefinition);
            }
            pageBeans.setCurrentPage(pageInfo.getPageNum());   //当前页
            pageBeans.setPageCount(pageInfo.getPages());       //总页数
            pageBeans.setTotalCount(pageInfo.getTotal());      //总记录数
            pageBeans.setPageSize(pageInfo.getPageSize());     //每页显示条数
        }
        return ReturnView.success(pageBeans);
    }

    /**
     * 添加
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView add(ParameterUserDefinition definition){
        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());

        //校验参数
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装新用户信息
        User user=new User();
        user.setUserId(ApplicationUtil.getID());                    //用户id
        user.setUserName(definition.getUserName());                 //用户名称
        user.setUserAccount(definition.getUserAccount());           //用户账号
        user.setUserPass(EncryptionUtil.getEncryptString(ApplicationUtil.getStringRandom(8)));//用户密码
        user.setCreateUserId(userLoginInfo.getUniqueId());          //创建人
        user.setCreateTime(new Date());                             //创建时间
        user.setUpdateTime(new Date());                             //修改时间
        user.setUserType(definition.getUserType());                 //用户类型
        user.setIsDeleted(GLOBAL_STATE.NO_DEL.STATE);               //删除标志

        userDao.insertSelective(user);
        return ReturnView.success("添加成功");
    }

    /**
     * 修改
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView update(ParameterUserDefinition definition){
        //校验参数
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装修改用户信息
        User user=new User();
        user.setUserId(definition.getUserId());                     //用户id
        user.setUserName(definition.getUserName());                 //用户名称
        user.setUserAccount(definition.getUserAccount());           //用户账号
        user.setUserType(definition.getUserType());                 //用户类型
        user.setUpdateTime(new Date());                             //修改时间

        userDao.updateByPrimaryKeySelective(user);
        return ReturnView.success("修改成功");
    }

    /**
     * 修改账户
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView updateAccount(ParameterAccountInfoDefinition definition){
        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());

        //判断是前台账户还是后台账户
        if(userLoginInfo.getUserType().equals("1")){
            Project updateProject=new Project();

            if(definition.getUpdateType().equals("1")){
                updateProject.setProjectLeader(definition.getUpdateContent()); //账户名
            }else if(definition.getUpdateType().equals("2")){
                //校验项目账户是否已经存在
                HashMap<String, Object> parameterMap=new HashMap<>();
                parameterMap.put("projectId",userLoginInfo.getUniqueId());
                parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL.STATE);
                parameterMap.put("projectName","");
                parameterMap.put("projectAccount",definition.getUpdateContent());
                ProjectDTO projectDTO = projectDao.checkContentIsExist(parameterMap);
                if(projectDTO!=null)return ReturnView.error("项目账号已存在");

                updateProject.setProjectAccount(definition.getUpdateContent());
            }

        }else{
            User user=new User();
            if(definition.getUpdateType().equals("1")){
                user.setUserName(definition.getUpdateContent()); //账户名

            }else if(definition.getUpdateType().equals("2")){
                //校验内容是否存在
                HashMap<String, Object> parameterMap=new HashMap<>();
                parameterMap.put("userId",userLoginInfo.getUniqueId());
                parameterMap.put("userAccount",definition.getUpdateContent());
                parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL);
                UserDTO userDTO = userDao.checkContentIsExist(parameterMap);
                if(userDTO!=null)return ReturnView.error("用户账号已存在");
                user.setUserAccount(definition.getUpdateContent());
            }
        }
        return ReturnView.success("修改成功");
    }


    /**
     * 删除
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView deleteByIds(DeleteUserDefinition definition){
        if(!CollectionUtils.isEmpty(definition.getDeleteIds())){
            //批量删除
            userDao.batchDelete(definition);
        }
        return ReturnView.success("删除成功");
    }

    /**
     * 校验参数
     * @param definition
     * @return
     */
    private ReturnView checkParameters(ParameterUserDefinition definition){
        //校验内容是否存在
        HashMap<String, Object> parameterMap=new HashMap<>();
        parameterMap.put("userId",definition.getUserId());
        parameterMap.put("userAccount",definition.getUserAccount());
        parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL);
        UserDTO userDTO = userDao.checkContentIsExist(parameterMap);
        if(userDTO!=null)return ReturnView.error("用户账号已存在");

        USER_TABLE_STATE instance = USER_TABLE_STATE.getInstance(definition.getUserType());
        if(instance==null){
            return ReturnView.error("用户类型异常");
        }else{
            definition.setUserType(instance.STATE);
        }
        return ReturnView.success("参数校验成功");
    }
}
    
    
    
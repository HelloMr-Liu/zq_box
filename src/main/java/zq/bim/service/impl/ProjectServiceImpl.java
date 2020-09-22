package zq.bim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zq.bim.constant.GLOBAL_STATE;
import zq.bim.dao.ProjectDao;
import zq.bim.entity.Project;
import zq.bim.entity.dto.PageBeanDefinition;
import zq.bim.entity.dto.ProjectDTO;
import zq.bim.entity.dto.ProjectDefinition;
import zq.bim.entity.dto.delete.DeleteProjectDefinition;
import zq.bim.entity.dto.parameter.ParameterProjectDefinition;
import zq.bim.entity.dto.query.QueryProjectDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.ProjectService;
import zq.bim.util.IdUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 功能：描述当前类
 * 作者：刘梓江
 * 时间：2020/9/21  16:11
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterProjectDefinition definition){
        //封装指定项目信息
        ProjectDefinition projectDefinition=new ProjectDefinition();
        ProjectDTO one = projectDao.findOne(definition.getProjectId());
        if(one!=null){
            //二次封装项目信息
            projectDefinition.setProjectId(one.getProjectId());     //项目id
            projectDefinition.setProjectName(one.getProjectName()); //项目名称
            projectDefinition.setCreateTime(one.getCreateTime());   //项目创建时间
        }
        return ReturnView.success(projectDefinition);
    }

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryProjectDefinition definition){
        //存储分页项目信息
        PageBeanDefinition<ProjectDefinition> pageBeans=new PageBeanDefinition<>();
        PageHelper.startPage(definition.getCurrentPage(),definition.getPageSize());
        List<ProjectDTO> projects = projectDao.findByQuery(definition);
        if(!CollectionUtils.isEmpty(projects)){
            PageInfo<ProjectDTO> pageInfo=new PageInfo<>(projects);
            //获取分页项目信息并二次封装
            for(ProjectDTO project:projects){
                ProjectDefinition projectDefinition=new ProjectDefinition();
                projectDefinition.setProjectId(project.getProjectId());     //项目id
                projectDefinition.setProjectName(project.getProjectName()); //项目名称
                projectDefinition.setCreateTime(project.getCreateTime());   //项目创建时间
                pageBeans.getList().add(projectDefinition);
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
    public ReturnView add(ParameterProjectDefinition definition){
        //校验信息
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装项目信息
        Project project=new Project();
        project.setProjectId(IdUtils.nextId());
        project.setProjectName(definition.getProjectName());
        project.setCreateTime(new Date());
        project.setIsDeleted(GLOBAL_STATE.NO_DEL.STATE);
        project.setUpdateTime(new Date());

        projectDao.insertSelective(project);
        return ReturnView.success("添加成功");
    }

    /**
     * 修改
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView update(ParameterProjectDefinition definition){
        //校验信息
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装项目信息
        Project project=new Project();
        project.setProjectId(definition.getProjectId());
        project.setProjectName(definition.getProjectName());
        project.setUpdateTime(new Date());
        projectDao.updateByPrimaryKeySelective(project);
        return ReturnView.success("修改成功");
    }

    /**
     * 删除
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView deleteByIds(DeleteProjectDefinition definition){
        //获取有效设备信息


        //批量移除项目信息
        projectDao.batchDelete(definition);
        return ReturnView.success("删除成功");
    }


    /**
     * 校验参数
     * @param definition
     * @return
     */
    private ReturnView checkParameters(ParameterProjectDefinition definition){
        //校验项目名称是否一致
        HashMap<String, Object> parameterMap=new HashMap<>();
        parameterMap.put("projectId",definition.getProjectId());
        parameterMap.put("projectName",definition.getProjectName());
        parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL);
        ProjectDTO projectDTO = projectDao.checkNameIsExist(parameterMap);
        if(projectDTO!=null){
            return ReturnView.error("项目名称已存在");
        }
        return ReturnView.success("参数校验成功");
    }
}
    
     
    
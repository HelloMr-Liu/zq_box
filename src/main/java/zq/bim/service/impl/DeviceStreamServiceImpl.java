package zq.bim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zq.bim.constant.*;
import zq.bim.dao.DeviceStreamDao;
import zq.bim.entity.DeviceStream;
import zq.bim.entity.dto.*;
import zq.bim.entity.dto.delete.DeleteDeviceStreamDefinition;
import zq.bim.entity.dto.delete.DeleteDeviceStreamDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceStreamDefinition;
import zq.bim.entity.dto.query.QueryDeviceStreamDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.DeviceStreamService;
import zq.bim.util.IdUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 功能：操作流流业务接口
 * 作者：刘梓江
 * 时间：2020/9/22  14:01
 */
@Service
public class DeviceStreamServiceImpl implements DeviceStreamService {

    @Autowired
    private DeviceStreamDao streamDao;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterDeviceStreamDefinition definition){
        DeviceStreamDefinition deviceStreamDefinition=new DeviceStreamDefinition();
        DeviceStreamDTO one = streamDao.findOne(definition.getStreamId());
        if(one!=null){
            deviceStreamDefinition.setStreamId(one.getStreamId());                                          //流id
            deviceStreamDefinition.setStreamName(one.getStreamName());                                      //流配置名称
            deviceStreamDefinition.setPullStreamAddress(one.getPullStreamAddress());                        //拉流地址
            deviceStreamDefinition.setPushStreamAddress(one.getPushStreamAddress());                        //推流地址
            deviceStreamDefinition.setPlayAddress(one.getPlayAddress());                                    //播放地址
            deviceStreamDefinition.setLiefStatus(STREAM_LIFE_STATE.getInstance(one.getLiefStatus()).MESS);  //配置状态
            if(one.getLiefStatus()){
                //在线
                deviceStreamDefinition.setLiefStatuCode("1");
            }else{
                //离线
                deviceStreamDefinition.setLiefStatuCode("0");
            }
        }
        return ReturnView.success(deviceStreamDefinition);
    }

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryDeviceStreamDefinition definition){
        //存储分页流信息
        PageBeanDefinition<DeviceStreamDefinition> pageBeans=new PageBeanDefinition<>();
        PageHelper.startPage(definition.getCurrentPage(),definition.getPageSize());
        List<DeviceStreamDTO> streams = streamDao.findByQuery(definition);
        if(!CollectionUtils.isEmpty(streams)){
            PageInfo<DeviceStreamDTO> pageInfo=new PageInfo<>(streams);
            //获取分页流信息并二次封装
            for(DeviceStreamDTO stream:streams){
                DeviceStreamDefinition streamDefinition=new DeviceStreamDefinition();
                streamDefinition.setStreamId(stream.getStreamId());                     //流id
                streamDefinition.setStreamName(stream.getStreamName());                 //流名称
                streamDefinition.setPullStreamAddress(stream.getPullStreamAddress());   //拉流地址
                streamDefinition.setPushStreamAddress(stream.getPushStreamAddress());   //推流地址
                streamDefinition.setPlayAddress(stream.getPlayAddress());               //播放地址
                streamDefinition.setLiefStatus(STREAM_LIFE_STATE.getInstance(stream.getLiefStatus()).MESS);  //配置状态
                if(stream.getLiefStatus()){
                    //在线
                    streamDefinition.setLiefStatuCode("1");
                }else{
                    //离线
                    streamDefinition.setLiefStatuCode("0");
                }
                pageBeans.getList().add(streamDefinition);
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
    public ReturnView add(ParameterDeviceStreamDefinition definition){
        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());
        //参数校验成功
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装信息设备流信息
        DeviceStream deviceStream=new DeviceStream();
        deviceStream.setStreamId(IdUtils.nextId());                             //流id
        deviceStream.setStreamName(definition.getStreamName());               //流名称
        deviceStream.setProjectId(definition.getProjectId());                   //项目id
        deviceStream.setDeviceId(definition.getDeviceId());                   //设备id
        deviceStream.setLiefStatus(STREAM_LIFE_STATE.ABNORMAL.STATE);           //流状态
        deviceStream.setPullStreamAddress(definition.getPullStreamAddress());   //拉流地址
        deviceStream.setPushStreamAddress(definition.getPushStreamAddress());   //推流地址
        deviceStream.setPlayAddress(definition.getPlayAddress());               //播放地址
        deviceStream.setCreateUserId(userLoginInfo.getUniqueId());              //创建人员id
        deviceStream.setCreateTime(new Date());                                 //创建时间
        deviceStream.setUpdateTime(new Date());                                 //修改时间
        deviceStream.setIsDeleted(GLOBAL_STATE.NO_DEL.STATE);                   //删除标志
        deviceStream.setOpStatus(STREAM_OP_STATE.OPERATION_INSERT.STATE);       //操作状态

        //持久化到数据库中
        streamDao.insertSelective(deviceStream);
        return ReturnView.success("新增成功");
    }

    /**
     * 修改
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView update(ParameterDeviceStreamDefinition definition){
        //参数校验成功
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;
        
        Long streamId=definition.getStreamId();

        DeviceStream stream=streamDao.selectByPrimaryKey(streamId);
        if(stream==null || stream.getIsDeleted()==true) {
        	return ReturnView.error("修改失败,数据不存在!");
        }
        
        //无状态
        Integer opStatus=STREAM_OP_STATE.OPERATION_NONE.STATE;
        //地址没改不需要发送到设备
        if(stream.getPullStreamAddress()!=definition.getPullStreamAddress()
         ||stream.getPushStreamAddress()!=definition.getPushStreamAddress()) {
        	//修改状态
        	opStatus=STREAM_OP_STATE.OPERATION_INSERT.STATE;
        }
        
        //封装信息设备流信息
        DeviceStream deviceStream=new DeviceStream();
        deviceStream.setStreamId(definition.getStreamId());                     //流id
        deviceStream.setStreamName(definition.getStreamName());                 //流名称
        deviceStream.setPullStreamAddress(definition.getPullStreamAddress());   //拉流地址
        deviceStream.setPushStreamAddress(definition.getPushStreamAddress());   //推流地址
        deviceStream.setPlayAddress(definition.getPlayAddress());               //播放地址
        deviceStream.setUpdateTime(new Date());                                 //修改时间
        deviceStream.setOpStatus(opStatus);       //操作状态

        //持久化到数据库中
        streamDao.updateByPrimaryKeySelective(deviceStream);
        return ReturnView.success("修改成功");
    }

    /**
     * 删除
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView deleteByIds(DeleteDeviceStreamDefinition definition){
        if(!CollectionUtils.isEmpty(definition.getDeleteIds())){
            //补充修改参数信息
            definition.setOpStatus(STREAM_OP_STATE.OPERATION_DELETE.STATE);
            //批量移除信息
            streamDao.batchDelete(definition);
        }
        return ReturnView.success("删除成功");
    }

    /**
     * 校验参数
     * @param definition
     * @return
     */
    private ReturnView checkParameters(ParameterDeviceStreamDefinition definition){
        //校验内容是否存在
        HashMap<String, Object> parameterMap=new HashMap<>();
        parameterMap.put("deviceId",definition.getDeviceId());                      //设备id
        parameterMap.put("streamId",definition.getStreamId());                      //流id
        parameterMap.put("streamName",definition.getStreamName());                  //流名称
        parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL);                         //删除标志
        parameterMap.put("pushStreamAddress", definition.getPushStreamAddress());   //推流地址
        DeviceStreamDTO deviceStreamDTO = streamDao.checkContentIsExist(parameterMap);
        if(deviceStreamDTO!=null){
            return ReturnView.error("推流地址或流名称已存在");
        }
        return ReturnView.success("参数校验成功");
    }
}
    
    
    
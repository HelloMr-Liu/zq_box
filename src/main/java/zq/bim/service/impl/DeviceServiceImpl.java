package zq.bim.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zq.bim.constant.DEVICE_ONLINE_STATE;
import zq.bim.constant.DEVICE_RELEVANCE_STATE;
import zq.bim.constant.GLOBAL_STATE;
import zq.bim.constant.REQUEST_MESS;
import zq.bim.dao.DeviceDao;
import zq.bim.dao.DeviceStreamDao;
import zq.bim.entity.Device;
import zq.bim.entity.dto.*;
import zq.bim.entity.dto.delete.DeleteDeviceDefinition;
import zq.bim.entity.dto.parameter.ParameterDeviceDefinition;
import zq.bim.entity.dto.parameter.ParameterProjectDefinition;
import zq.bim.entity.dto.query.QueryDeviceDefinition;
import zq.bim.result.ReturnView;
import zq.bim.service.DeviceService;
import zq.bim.util.IdUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：操作设备业务接口实现
 * 作者：刘梓江
 * 时间：2020/9/22  13:16
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DeviceStreamDao deviceStreamDao;


    /**
     * 获取指定信息
     * @param definition
     * @return
     */
    public ReturnView findOne(ParameterDeviceDefinition definition){
        //封装设备信息对象
        DeviceDefinition deviceDefinition=new DeviceDefinition();
        //获取当前指定设备信息
        DeviceDTO one = deviceDao.findOne(definition.getDeviceId());
        if(one!=null){
            deviceDefinition.setDeviceId(one.getDeviceId());            //设备id
            deviceDefinition.setDeviceName(one.getDeviceName());        //设备名称
            deviceDefinition.setDeviceNumber(one.getDeviceNumber());    //设备标识编号
            if(one.getRelevanceStatus()){
                deviceDefinition.setDeviceStatus(DEVICE_ONLINE_STATE.getInstance(one.getDeviceStatus()).MESS);  //设备离线或在线状态
                if(one.getDeviceStatus()){
                    //代表在线
                    deviceDefinition.setDeviceStatusCode("3");
                }else{
                    //代表离线
                    deviceDefinition.setDeviceStatusCode("2");
                }
            }else{
                deviceDefinition.setDeviceStatus(DEVICE_RELEVANCE_STATE.getInstance(one.getRelevanceStatus()).MESS);  //设备激活或未激活状态
                //代表未激活
                deviceDefinition.setDeviceStatusCode("1");
            }
        }
        return ReturnView.success(deviceDefinition);
    }

    /**
     * 条件查询对应的信息列表
     * @param definition
     * @return
     */
    public ReturnView findAllByQuery(QueryDeviceDefinition definition){
        //存储分页设备信息
        PageBeanDefinition<DeviceDefinition> pageBeans=new PageBeanDefinition<>();
        PageHelper.startPage(definition.getCurrentPage(),definition.getPageSize());
        List<DeviceDTO> devices = deviceDao.findByQuery(definition);
        if(!CollectionUtils.isEmpty(devices)){
            PageInfo<DeviceDTO> pageInfo=new PageInfo<>(devices);
            //获取分页设备信息并二次封装
            for(DeviceDTO device:devices){
                DeviceDefinition deviceDefinition=new DeviceDefinition();
                deviceDefinition.setDeviceId(device.getDeviceId());            //设备id
                deviceDefinition.setDeviceName(device.getDeviceName());        //设备名称
                deviceDefinition.setDeviceNumber(device.getDeviceNumber());    //设备标识编号
                if(device.getRelevanceStatus()){
                    deviceDefinition.setDeviceStatus(DEVICE_ONLINE_STATE.getInstance(device.getDeviceStatus()).MESS);  //设备离线或在线状态
                    if(device.getDeviceStatus()){
                        //代表在线
                        deviceDefinition.setDeviceStatusCode("3");
                    }else{
                        //代表离线
                        deviceDefinition.setDeviceStatusCode("2");
                    }
                }else{
                    deviceDefinition.setDeviceStatus(DEVICE_RELEVANCE_STATE.getInstance(device.getRelevanceStatus()).MESS);  //设备激活或未激活状态
                    //代表未激活
                    deviceDefinition.setDeviceStatusCode("1");
                }
                pageBeans.getList().add(deviceDefinition);
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
    public ReturnView add(ParameterDeviceDefinition definition){
        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());

        //校验参数
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装信息设备信息
        Device device=new Device();
        device.setDeviceId(IdUtils.nextId());                       //设备id
        device.setProjectId(definition.getProjectId());             //项目id
        device.setDeviceNumber("D-"+IdUtils.nextId());              //设备标识编号
        device.setDeviceName(definition.getDeviceName());           //设备名称
        device.setRelevanceStatus(DEVICE_RELEVANCE_STATE.NO.STATE); //设备未激活状态
        device.setDeviceStatus(DEVICE_ONLINE_STATE.NO.STATE);       //设备离线状态
        device.setCreateUserId(userLoginInfo.getUniqueId());        //用户id
        device.setCreateTime(new Date());                           //设备创建时间
        device.setUpdateTime(new Date());                           //设备修改时间
        device.setIsDeleted(GLOBAL_STATE.NO_DEL.STATE);             //设备删除标志
        //持久化到数据库中
        deviceDao.insertSelective(device);

        return ReturnView.success("添加成功");
    }

    /**
     * 修改
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView update(ParameterDeviceDefinition definition){
        //校验参数
        ReturnView returnView = checkParameters(definition);
        if(returnView.getCode()!=200)return returnView;

        //封装信息设备信息
        Device device=new Device();
        device.setDeviceId(definition.getDeviceId());           //设备id
        device.setDeviceName(definition.getDeviceName());       //设备名称
        device.setUpdateTime(new Date());                       //设备修改时间
        //持久化到数据库中
        deviceDao.updateByPrimaryKeySelective(device);

        return ReturnView.success("修改成功");
    }

    /**
     * 删除
     * @param definition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnView deleteByIds(DeleteDeviceDefinition definition){
        if(!CollectionUtils.isEmpty(definition.getDeleteIds())){
            //封装扩展信息
            definition.setRelevance(DEVICE_RELEVANCE_STATE.NO.STATE);

            //获取设备对应的有效流数量
            Map<String,Object> parametersMap=new HashMap<>();
            parametersMap.put("IS_DELETED",GLOBAL_STATE.NO_DEL.STATE);
            parametersMap.put("deviceIds",definition.getDeleteIds());
            int validDeviceStreamNumber = deviceStreamDao.findValidDeviceStreamNumber(parametersMap);
            if(validDeviceStreamNumber>0){
                return ReturnView.error("有效推流数量"+validDeviceStreamNumber+"刪除失败!");
            }
            //持久化到数据库中
            deviceDao.batchDelete(definition);
        }
        return ReturnView.success("删除成功");
    }

    /**
     * 校验参数
     * @param definition
     * @return
     */
    private ReturnView checkParameters(ParameterDeviceDefinition definition){
        //校验内容是否存在
        HashMap<String, Object> parameterMap=new HashMap<>();
        parameterMap.put("projectId",definition.getProjectId());            //项目id
        parameterMap.put("deviceId",definition.getDeviceId());              //设备id
        parameterMap.put("deviceName",definition.getDeviceName());          //设备名称
        parameterMap.put("isDeleted", GLOBAL_STATE.NO_DEL);                 //删除标志
        DeviceDTO deviceDTO = deviceDao.checkContentIsExist(parameterMap);
        if(deviceDTO!=null){
            return ReturnView.error("设备名称已存在");
        }
        return ReturnView.success("参数校验成功");
    }
}
    
    
    
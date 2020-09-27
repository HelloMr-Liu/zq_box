package zq.bim.listening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import zq.bim.constant.DEVICE_ONLINE_STATE;
import zq.bim.constant.DEVICE_RELEVANCE_STATE;
import zq.bim.constant.GLOBAL_STATE;
import zq.bim.dao.DeviceDao;
import zq.bim.entity.Device;
import zq.bim.entity.SystemOperationLogWithBLOBs;
import zq.bim.entity.dto.DeviceDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 功能：设备心跳管理器
 * 作者：刘梓江
 * 时间：2020/9/22  20:11
 */
@Component
public class DeviceHeartManager {

    Logger log= LoggerFactory.getLogger(DeviceHeartManager.class);

    @Autowired
    private DeviceDao deviceDao;
    /**
     * 每1分钟 监听设备健康状态
     */
    @Scheduled(cron="0 */1 * * * ?")
    public void timingManager() {
        Map<String,Object> parameters=new HashMap<>();
        parameters.put("isDeleted", GLOBAL_STATE.NO_DEL.STATE);
        parameters.put("isActivation", DEVICE_RELEVANCE_STATE.YES.STATE);
        List<DeviceDTO> allOnActivation = deviceDao.findAllOnActivation(parameters);
        if(!CollectionUtils.isEmpty(allOnActivation)){
            //创建一个集合默认批量存入400个队列信息
            List<Device> devices=new ArrayList<>();
            int count=0;
            for(DeviceDTO device:allOnActivation){
                count++; //获取一个队列信息
                //获取当前设备最近一次心跳时间
                Long currentTime=new Date().getTime()-device.getHeartbeatTime().getTime();
                if(currentTime>120000&&device.getDeviceStatus()){
                    //超过2分钟代表当前设备处于离线状态
                    Device cDevice=new Device();
                    cDevice.setDeviceId(device.getDeviceId());
                    cDevice.setDeviceStatus(DEVICE_ONLINE_STATE.NO.STATE);
                    cDevice.setUpdateTime(new Date());
                    devices.add(cDevice);
                }
                if(count==400){
                    //代表已经有300个消息队列 存储一次数据库中
                    deviceDao.batchUpdateDeviceState(devices);
                    count=0;
                    devices.clear();
                }
            }
            if(!CollectionUtils.isEmpty(devices)){
                //将剩余的设备更改信息
                deviceDao.batchUpdateDeviceState(devices);
                count=0;
                devices.clear();
            }
        }
        log.info("===============刷新设备健康状态信息 时间："+ LocalDateTime.now());
    }
}
    
    
    
package zq.bim.listening;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import zq.bim.aspect.AccessTokenAspect;
import zq.bim.dao.SystemOperationLogDao;
import zq.bim.entity.SystemOperationLogWithBLOBs;
import zq.bim.queue.SystemOperationLogMessageQueue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 功能：请求日志定时管理器
 * 作者：刘梓江
 * 时间：2020/8/27  16:52
 */
@Component
public class OperationLogManager {

    Logger log= LoggerFactory.getLogger(OperationLogManager.class);

    @Autowired
    private SystemOperationLogDao logDao;

    @Autowired
    private SystemOperationLogMessageQueue messageQueue;

    /**
     * 每5分钟 监听依次请求日志信息
     */
    @Scheduled(cron="0 */5 * * * ?")
    public void timingManager() {
        //判断队列是否有数据
        if(!CollectionUtils.isEmpty(messageQueue.getMessageQueue())){
            //创建一个集合默认批量存入400个队列信息
            List<SystemOperationLogWithBLOBs> logWithBLOBs=new ArrayList<>();

            int count=0;
            ConcurrentLinkedDeque<SystemOperationLogWithBLOBs> messageQueue = this.messageQueue.getMessageQueue();
            //遍历队列量信息
            while(messageQueue.peek()!=null){
                count++; //获取一个队列信息
                SystemOperationLogWithBLOBs poll = messageQueue.poll();
                poll.setCreateTime(new Date());
                logWithBLOBs.add(poll);
                if(count==300){
                    //代表已经有300个消息队列 存储一次数据库中
                    logDao.batchInsert(logWithBLOBs);
                    count=0;
                    logWithBLOBs.clear();
                }
            }

            if(!CollectionUtils.isEmpty(logWithBLOBs)){
                //将剩余的请求日志也添加到数据库中
                logDao.batchInsert(logWithBLOBs);
                count=0;
                logWithBLOBs.clear();
            }
        }
        log.info("===============刷新请求日志信息 时间："+ LocalDateTime.now());
    }
}
package zq.bim.queue;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zq.bim.entity.SystemOperationLogWithBLOBs;
import zq.bim.util.ApplicationUtil;
import zq.bim.util.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 功能：系统请求日志消息队列
 * 作者：刘梓江
 * 时间：2020/8/27  14:58
 */
@Component
public class SystemOperationLogMessageQueue {

    @Autowired
    private HttpServletRequest request;

    /**
     * 消息队列对象
     */
    private ConcurrentLinkedDeque<SystemOperationLogWithBLOBs> messageQueue=new ConcurrentLinkedDeque<>();

    /**
     * 操作类型
     * @param logType           操作类型：LOGIN,LOGOUT,FIND,ADD,UPDATE,DELETE
     * @param operationArgs     请求操作参数
     * @param operationPath     请求操作接口地址
     * @param operationContent  请求操作对应的内容位置  类+方法名称
     * @param userId            用户id
     */
    public void addOperationLogMessage(String logType,
                                       String operationArgs,
                                       String operationPath,
                                       String operationContent,
                                       Long userId)throws Exception{
        //异步创建一个请求日志对象
        SystemOperationLogWithBLOBs log=new SystemOperationLogWithBLOBs();
        log.setLogId(ApplicationUtil.getID());
        if(operationPath.contains("find")){
            //操作类型是查询
            log.setLogType("FIND");
        }else if(operationPath.contains("add")){
            //操作类型是添加
            log.setLogType("ADD");
        }else if(operationPath.contains("update")){
            //操作类型是修改
            log.setLogType("UPDATE");
        }else if(operationPath.contains("delete")){
            //操作类型是删除
            log.setLogType("DELETE");
        }else{
            log.setLogType(logType);
        }

        log.setOperationTime(new Date());                                   //当前操作时间
        log.setOperationContent(operationContent);                          //操作位置
        log.setOperationArgs(operationArgs);                                //操作参数
        log.setOperationPath(operationPath);                                //操作路径
        log.setOperationUserId(userId);                                     //操作人id
        log.setOperationAddress(NetworkUtil.getHostIpAddress(request));     //操作地址
        messageQueue.add(log);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public ConcurrentLinkedDeque<SystemOperationLogWithBLOBs> getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(ConcurrentLinkedDeque<SystemOperationLogWithBLOBs> messageQueue) {
        this.messageQueue = messageQueue;
    }
}
    
    
    
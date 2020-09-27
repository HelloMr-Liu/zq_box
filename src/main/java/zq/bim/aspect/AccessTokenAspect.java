package zq.bim.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zq.bim.constant.REQUEST_MESS;
import zq.bim.entity.dto.UserLoginOKDefinition;
import zq.bim.lock.OperatingLock;
import zq.bim.queue.SystemOperationLogMessageQueue;
import zq.bim.result.ReturnView;
import zq.bim.util.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 解决：用于访问权限校验,权限不够不能访问
 * 作者：刘梓江
 * 时间：2020/7/19  18:30
 */
@Aspect
@Component
public class AccessTokenAspect {

    Logger log= LoggerFactory.getLogger(AccessTokenAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private SystemOperationLogMessageQueue messageQueue;

    @Around("@annotation(zq.bim.annotations.AccessToken)")
    public Object accessToken(ProceedingJoinPoint joinPoint) throws Throwable {
        //接收参数1
        Enumeration<String> paraNames=request.getParameterNames();
        StringBuilder requestArgs=new StringBuilder();
        for(Enumeration<String> e = paraNames; e.hasMoreElements();){
            String thisName=e.nextElement().toString();
            String thisValue=request.getParameter(thisName);
            requestArgs.append(thisName + "=" + thisValue + "&");
        }

        //获取当前请求路径(获取接口地址(不包含项目名))
        String currentRequestPath=request.getServletPath();
        log.info("请求ip"+ NetworkUtil.getHostIpAddress(request));
        log.info("方法名"+joinPoint.getSignature().getName());
        log.info("方法接受参数"+ Arrays.toString(joinPoint.getArgs()));
        log.info("包名+组件名(类名):"+joinPoint.getSignature().getDeclaringTypeName());
        log.info("请求参数"+ requestArgs.toString());
        log.info("权限访问地址"+currentRequestPath);

        UserLoginOKDefinition userLoginInfo = (UserLoginOKDefinition)request.getAttribute(REQUEST_MESS.USER_INFO.name());
        if(userLoginInfo==null||currentRequestPath.contains("del")&&(!userLoginInfo.getUserType().equals("3"))){
            return ReturnView.error("没有权限操作").toJson();
        }
        //添加一条请求日志信息
        messageQueue.addOperationLogMessage("",requestArgs.toString(),currentRequestPath,joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(),userLoginInfo.getUniqueId());

        //获取原子锁操作
        ReentrantReadWriteLock lock = OperatingLock.getLock(currentRequestPath);
        try{
            System.out.println(lock!=null);
            //开启原子锁操作
            if(lock!=null)lock.writeLock().lock();

            //执行当前接口对应的方法
            Object proceed = joinPoint.proceed();
            return proceed;
        }catch (Exception e){
            e.printStackTrace();
            return ReturnView.error("系统原子锁操作异常").toJson();
        }finally {
            //关闭原子锁操作
            if(lock!=null&&lock.writeLock().getHoldCount()>0)lock.writeLock().unlock();
        }
    }
}
    
    
    
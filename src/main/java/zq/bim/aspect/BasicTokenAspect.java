package zq.bim.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zq.bim.queue.SystemOperationLogMessageQueue;
import zq.bim.util.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * 功能：简单权限校验
 * 作者：刘梓江
 * 时间：2020/7/19  18:30
 */
@Aspect
@Component
public class BasicTokenAspect {

    Logger log= LoggerFactory.getLogger(AccessTokenAspect.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private SystemOperationLogMessageQueue messageQueue;

    @Around("@annotation(zq.bim.annotations.BasicToken)")
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

        //添加一条请求日志信息
        messageQueue.addOperationLogMessage("",requestArgs.toString(),currentRequestPath,joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(),null);

        if(!currentRequestPath.contains("login")){

        }

        //执行当前接口对应的方法
        Object proceed = joinPoint.proceed();

        //对登录成功的记录
        if(currentRequestPath.contains("login")&&!currentRequestPath.contains("out")){

            //对登录成功标识未过期的退出成功的记录
        }else if(currentRequestPath.contains("login")&&currentRequestPath.contains("out")){

        }
        return proceed;
    }
}



package zq.bim.aspect;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import zq.bim.exception.BimErrorException;
import zq.bim.exception.BimNullException;
import zq.bim.result.ReturnView;
import zq.bim.util.UserUtil;


@Aspect
@Component
public class MyAop {
	
	private static final Logger log = LoggerFactory.getLogger(MyAop.class);
	
	
	@Around("execution( * zq.bim.controller.*.* (..))")
	public Object s(ProceedingJoinPoint pjp) {
		// 请求返回值
		Object proceed = null;
		ReturnView rv = null;
		long t1 = new Date().getTime();
		try {
			/*
			 * //提取请求头 String fetchHeader = fetchHeader(); if (fetchHeader != null) { return
			 * fetchHeader; }
			 */
			
			proceed = pjp.proceed();

		} catch (Throwable e) {
			e.printStackTrace();
			ReturnView returnView = analyzeThrowable(e);
			
			proceed =returnView.toJson(); 
				
		}finally {
			
			//清除缓存
			UserUtil.remove();
		}
		
		return proceed;
	}
	
	
	private ReturnView analyzeThrowable(Throwable e) {
		if(e instanceof BimErrorException) {
			return new ReturnView(20003, e.getMessage());
		}else if(e instanceof BimNullException) {
			return new ReturnView(20004, e.getMessage());
		}else if(e instanceof NullPointerException) {
			String readThrowableString = readThrowableString(e);
			
			//打印到文件
			log.error(readThrowableString);
			return new ReturnView(20005, readThrowableString);
		}else{
			String readThrowableString = readThrowableString(e);
			
			//打印到文件
			log.error(readThrowableString);
			return new ReturnView(500, readThrowableString);
		}
	}
	
	
	private String readThrowableString(Throwable e) {
		StringWriter sw = new StringWriter();  
		PrintWriter pw = new PrintWriter(sw);  
		e.printStackTrace(pw);  
		return sw.toString();
	}
	
	
}

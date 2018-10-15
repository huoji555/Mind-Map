package com.hwj.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hwj.entity.AdminErrorMsg;
import com.hwj.service.AdminErrorMsgService;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Aspect
@Component
public class HttpAspect {
	
	@Autowired
	private AdminErrorMsgService adminErrorMsgService;
	
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	//两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution( public * com.hwj.controller.*.*(..))")
    public  void log(){
    }

    @Before("log()")
    public void  doBefore(JoinPoint joinPoint){
       ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("请求地址:"+"url={}" , request.getRequestURL());

        //method
        logger.info("请求方法:"+"method={}" ,request.getMethod());

        //ip,也可以获取端口号
        logger.info("IP地址:"+"ip={}" , request.getRemoteAddr());
        
        //获取端口号
        logger.info("请求端口:"+"host={}"+request.getRemoteHost());

        //类方法
        logger.info("请求方法:"+"class_method={}" , joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());

        //方法的参数
        logger.info("方法参数:"+"args={}" , joinPoint.getArgs());

    }


    
    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturning(Object object){
    	logger.info("返回结果:"+"response={}" , object.toString());
    }
    
    
    //记录异常信息
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	if(attributes != null) {
    		HttpServletRequest request = attributes.getRequest();
    		if (request != null) {
    			AdminErrorMsg msg = new AdminErrorMsg();
    			msg.setAdminIp(request.getRemoteAddr());
    			msg.setUrl(request.getRequestURI().toString());
    			msg.setMethod_type(request.getMethod());
    			String s = joinPoint.getSignature().getDeclaringTypeName();
                String name = s.substring(s.lastIndexOf(".") + 1);
                msg.setController_name(name);
                msg.setMethod_name(joinPoint.getSignature().getName());
                msg.setParams(Arrays.toString(joinPoint.getArgs()));
                msg.setCreate_time(new Date());
                msg.setError_msg(e.toString());
                adminErrorMsgService.save(msg);
    		}
    	}
    	logger.warn("请求异常",e);
    }
    
    @Around("log()")
    public Object daRound(ProceedingJoinPoint pjp) throws Throwable{
	    long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        logger.info("耗时 : " + (System.currentTimeMillis() - startTime));
        return ob;
    }


}

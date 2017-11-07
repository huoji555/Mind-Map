package com.hwj.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
  * <p>Company: B505信息技术研究所 </p> 
  * @Description: 对web请求进行日志记录
  * @Create Date: 2017年5月11日下午9:45:00
  * @Version: V1.00 
  * @Author: 赵良臣
  */
@Aspect
@Component
public class HttpAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	//两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution( public * com.hwj.web.*.*(..))")
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
    
   @Around("log()")
   public Object daRound(ProceedingJoinPoint pjp) throws Throwable{
	   long startTime = System.currentTimeMillis();
       Object ob = pjp.proceed();// ob 为方法的返回值
       logger.info("耗时 : " + (System.currentTimeMillis() - startTime));
       return ob;
   }


}

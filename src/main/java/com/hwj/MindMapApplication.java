package com.hwj;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching                   //开启缓存
public class MindMapApplication extends SpringBootServletInitializer{

 
	public static void main(String[] args) {
		
		SpringApplication application = new SpringApplication(MindMapApplication.class);
		Map<String, Object> defaultMap = new HashMap<>();
		defaultMap.put("pwd", System.getenv("SPWD"));
		application.setDefaultProperties(defaultMap);
		
		if (System.getenv("SPWD") == null) {
			SpringApplication.run(MindMapApplication.class, args);    //加载本地数据库
		}
		application.run(args);                                        //加载线上数据库
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.5.8
	 * @param 设置文件上传大小
	 * @return
	 */
	@Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("50MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("50MB");  
        return factory.createMultipartConfig();  
    }  
}

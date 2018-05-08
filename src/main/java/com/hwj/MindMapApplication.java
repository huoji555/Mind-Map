package com.hwj;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement     //开启事物注解
public class MindMapApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(MindMapApplication.class);
    }
 
	public static void main(String[] args) {
		SpringApplication.run(MindMapApplication.class, args);
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

package com.hwj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement     //开启事物注解
public class MindMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindMapApplication.class, args);
	}
}

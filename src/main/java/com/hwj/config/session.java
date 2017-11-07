package com.hwj.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

/*@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass =RdbCommonRepositoryImpl.class)*/
public class session {

	/* public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }

	    @Bean
	    public HibernateJpaSessionFactoryBean sessionFactory() {
	        return new HibernateJpaSessionFactoryBean();
	    }*/
}

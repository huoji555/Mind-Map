package com.hwj.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;


/**
  * @Description: 数据连接池配置
  * @Create Date: 2017年8月13日下午11:59:49
  * @Version: V1.00 
  */
@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfig {
	
	private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	
	
	@Bean(name = "primaryDataSource")
	@Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.primary" )
    public DataSource primaryDataSource() {
		logger.info("数据库连接池创建中.......");
        return DataSourceBuilder.create().build();
    }

}

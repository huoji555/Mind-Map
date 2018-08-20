package com.hwj.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
//@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {
	private Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.password}")
	private String password;

	@Bean
	public JedisPool redisPoolFactory() {
		logger.info("JedisPool注入成功！！");
		logger.info("redis地址：" + host + ":" + port);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		/*if(StringUtils.isBlank(password)){
			return new JedisPool(jedisPoolConfig, host, port, timeout, null);
		}else{
			return new JedisPool(jedisPoolConfig, host, port, timeout, password);
		}*/
		return new JedisPool(jedisPoolConfig, host, port, timeout, null);
	}

}

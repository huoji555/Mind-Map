package com.hwj.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwj.service.JedisClient;

import groovy.util.logging.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author Ragty
 * @param Jedis模板
 * @serialDate 2018.8.20
 *
 */
@Service
@Slf4j
public class JedisClientSingleService implements JedisClient {

	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 根据key从redis中获取value
	 */
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String str = jedis.get(key);
		jedis.close();
		return str;
	}
	
	/**
	 * 保存 redis
	 */
	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String str = jedis.set(key, value);
		jedis.close();
		return str;
	}
	
	/**
	 * 保存redis并设置过期时间
	 */
	@Override
	public String setex(String key,int second, String value) {
		Jedis jedis = jedisPool.getResource();
		String str = jedis.setex(key,second, value);
		jedis.close();
		return str;
	}

	/**
	 *设置key在millisecondsTimestamp这个点之后失效
	 * @param key
	 * @param millisecondsTimestamp
	 * @return
	 */
	public Long pexpireAt(String key,long millisecondsTimestamp){
		Jedis jedis = jedisPool.getResource();
		Long aLong = jedis.pexpireAt(key, millisecondsTimestamp);
		jedis.close();
		return aLong;
	}

	/**
	 * 
	 */
	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String str = jedis.hget(hkey, key);
		jedis.close();
		return str;
	}

	@Override
	public String hmset(String key, Map<String, String> data) {
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hmset(key,data);
		jedis.close();
		return result;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}
	
	/**
	 * 设置key自动增长 从1开始
	 */
	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	/**
	 * 设置key的过期时间
	 */
	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}
	
	/**
	 * 查看key的剩余生存时间
	 */
	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}
	
	/**
	 * 删除key
	 */
	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}
	@Override
	public Jedis getRedis() {
		return jedisPool.getResource();
	}
	
	/**
	 * 判断key是否存在
	 */
	@Override
	public boolean exist(String key){
		Jedis jedis = jedisPool.getResource();
		boolean exists = jedis.exists(key);
		jedis.close();
		return exists;
	}
	
}

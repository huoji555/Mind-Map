package com.hwj.service;

import redis.clients.jedis.Jedis;

import java.util.Map;

public interface JedisClient {

	String get(String key);
	
    String set(String key, String value);
    
    String setex(String key, int second, String value);

	public Long pexpireAt(String key,long millisecondsTimestamp);

    String hget(String key, String field);

    String hmset(String key,Map<String,String> data);
    
    long hset(String hkey, String key, String value); 
    
    long incr(String key);  
    
    long expire(String key, int second);  
    
    long ttl(String key);  
    
    long del(String key);  
    
    long hdel(String hkey, String key); 
    
    Jedis getRedis();
    
    public boolean exist(String key);
}

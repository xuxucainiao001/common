package cn.toseektech.common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisUtil {
	
	private JedisUtil() {}
	
	public static final Jedis jedis() {
       return  SpringUtils.getApplicationContext()
    		   .getBean(JedisSentinelPool.class)
    		   .getResource();
	}

}

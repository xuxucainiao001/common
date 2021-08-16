package cn.toseektech.commom;

import com.google.common.collect.Sets;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class CommonTest {
	
	public static void main(String[] args) {
	
		JedisPoolConfig config =new JedisPoolConfig();
		config.setMinIdle(5);
		config.setMaxIdle(20);
		config.setMaxTotal(20);
		config.setMaxWaitMillis(1000);
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",Sets.newHashSet("121.196.28.91:26379"),config,"dongxun");	
		Jedis jedis = jedisSentinelPool.getResource();
		jedis.set("a", "b");
		System.out.println(jedis.get("a"));
		jedisSentinelPool.close();

	}

}

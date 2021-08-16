package cn.toseektech.common.utils;

import java.util.Objects;

import org.redisson.api.RedissonClient;

/**
 * RedissonClient工具类
 * @author xuxu
 *
 */
public class RedissonUtils {
	
	private static  RedissonClient redissonClient=null;
	
	private RedissonUtils() {}
	
	public static final RedissonClient getRedissonClient() {
		if(Objects.isNull(redissonClient)) {
			redissonClient = SpringUtils.getApplicationContext().getBean(RedissonClient.class);
		}
		return redissonClient;
	}

}

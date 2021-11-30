package cn.toseektech.common.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.redisson.api.RScript.Mode;
import org.redisson.api.RScript.ReturnType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Lists;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 分布式雪花id,服务启动时,通过配置指定workId,并通过redis自增生成 dataCenterId
 * 每个服务支持32个最大集群 (dataCenterId 范围 0-31)
 * @author xuxu
 * @Title: IdUtils
 * @Description:
 */
public class IdUtils {

	private static final String LUA = 
			"local currValue = redis.call('get', KEYS[1]); " 
	        + "if currValue == false then "
			   + "redis.call('set', KEYS[1], 0); " 
	           + "return '0'; "
			+ "elseif (tonumber(currValue) > 30) then " 
	           + "redis.call('set', KEYS[1], 0); "
			   + "return '0'; " 
	        + "else " 
			   + "redis.call('set', KEYS[1], tonumber(currValue) + 1); "
			   + "return redis.call('get', KEYS[1]); " 
			   + "end";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static Snowflake snowflake;

	private static final String SNOWFLAKE_SEED = "_snowflake_seed";

	@Value("${spring.application.name}")
	private String appName;

	@Value("${speakin.snowflake.work-id:1}")
	private Long workId;

	@Resource
	private RedissonClient redissonClient;

	@PostConstruct
	public void init() {
		Integer dataCenterId = dataCenterId();	
		IdUtils.snowflake = IdUtil.getSnowflake(this.workId, dataCenterId);
		logger.info("Snowflake workId:{} , datacenterId : {}", this.workId, dataCenterId);
	}

	public static final Long getId() {
		return snowflake.nextId();
	}
	

	private Integer dataCenterId() {
		return redissonClient.getScript().eval(Mode.READ_WRITE, LUA, ReturnType.INTEGER,
				Lists.newArrayList(appName + SNOWFLAKE_SEED), 0);

	}

}

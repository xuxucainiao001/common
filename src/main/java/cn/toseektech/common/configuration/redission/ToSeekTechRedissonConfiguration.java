package cn.toseektech.common.configuration.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redssion 配置
 * 
 * @author xuxu
 *
 */
@Configuration
public class ToSeekTechRedissonConfiguration {

	@Bean
	public RedssionConfigProperties redssionConfigProperties() {
		return new RedssionConfigProperties();
	}

	@Bean("redissonCofig")
	public Config redissonConfig(RedssionConfigProperties properties)
			throws InstantiationException, IllegalAccessException {
		// 基础配置
		Config config = new Config();
		config.setCodec(properties.getCodec().newInstance());
		config.setNettyThreads(properties.getNettyThreads());
		config.setThreads(properties.getThreads());
		// 哨兵配置
		if (properties.getSentinel() != null) {
			SentinelServersConfig sentinelConfig = config.useSentinelServers();
			sentinelConfig.addSentinelAddress(properties.getSentinel().getSentinelAddresses());
			sentinelConfig.setMasterName(properties.getSentinel().getMasterName());
			sentinelConfig.setPassword(properties.getSentinel().getPassword());
			sentinelConfig.setLoadBalancer(properties.getSentinel().getLoadBalancer().newInstance());
			sentinelConfig.setDatabase(properties.getSentinel().getDatabase());
			sentinelConfig.setIdleConnectionTimeout(properties.getSentinel().getIdleConnectionTimeout());
			sentinelConfig.setConnectTimeout(properties.getSentinel().getConnectTimeout());
			sentinelConfig
					.setMasterConnectionMinimumIdleSize(properties.getSentinel().getMasterConnectionMinimumIdleSize());
			sentinelConfig.setMasterConnectionPoolSize(properties.getSentinel().getMasterConnectionPoolSize());
			sentinelConfig.setReadMode(ReadMode.valueOf(properties.getSentinel().getReadMode()));
			sentinelConfig.setKeepAlive(properties.getSentinel().getkeepAlive());
			sentinelConfig.setCheckSentinelsList(properties.getSentinel().getCheckSentinelsList());
		}
		// 单机配置
		if (properties.getSingle() != null) {
			SingleServerConfig singleServerConfig = config.useSingleServer();
			singleServerConfig.setAddress(properties.getSingle().getAddress());
			singleServerConfig.setPassword(properties.getSingle().getPassword());
			singleServerConfig.setKeepAlive(properties.getSingle().getKeepAlive());
			singleServerConfig.setDatabase(properties.getSingle().getDatabase());
			singleServerConfig.setConnectionPoolSize(properties.getSingle().getConnectionPoolSize());
			singleServerConfig.setConnectionMinimumIdleSize(properties.getSingle().getConnectionMinimumIdleSize());
			singleServerConfig.setTimeout(properties.getSingle().getTimeout());
			singleServerConfig.setUsername(properties.getSingle().getUsername());
			singleServerConfig.setConnectTimeout(properties.getSingle().getConnectTimeout());
		}
		return config;

	}

	/**
	 * Spring统一创建 RedissonClient，通过本项目RedissonUtils获取
	 * 
	 * @param config
	 * @return RedissonClient
	 */
	@Bean("redissonClient")
	public RedissonClient redissonClient(@Qualifier("redissonCofig") Config config) {
		return Redisson.create(config);
	}
}

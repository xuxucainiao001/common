package cn.toseektech.common.configuration.jedis;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

@Configuration
@EnableConfigurationProperties(JedisSentinelProperties.class)
public class JedisConfiguration {

	@Bean
	public JedisSentinelPool jedisSentinelPool(JedisSentinelProperties jedisSentinelProperties) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(jedisSentinelProperties.getMinIdle());
		config.setMaxIdle(jedisSentinelProperties.getMaxIdle());
		config.setMaxTotal(jedisSentinelProperties.getMaxTotal());
		config.setMaxWaitMillis(jedisSentinelProperties.getMaxWaitMillis());
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(jedisSentinelProperties.getMasterName(),
				jedisSentinelProperties.getHosts(), config, jedisSentinelProperties.getPassword());
		return jedisSentinelPool;
	}

}

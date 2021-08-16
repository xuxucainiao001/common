package cn.toseektech.common.configuration.jedis;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = JedisSentinelProperties.PREFIX)
public class JedisSentinelProperties {
	
	public static final String PREFIX="jedis.sentinel.config";
	
	private String masterName;
	
	private Set<String> hosts;
	
	private String password;
	
	private int minIdle = 5;
	
	private int maxIdle = 5;
	
	private int maxTotal = 5;
	
	private long maxWaitMillis =-1L;

	public Set<String> getHosts() {
		return hosts;
	}

	public void setHosts(Set<String> hosts) {
		this.hosts = hosts;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public long getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(long maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}
	
}

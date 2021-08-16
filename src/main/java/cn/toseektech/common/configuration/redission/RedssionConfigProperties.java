package cn.toseektech.common.configuration.redission;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.connection.balancer.LoadBalancer;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = RedssionConfigProperties.PREFIX)
public class RedssionConfigProperties {

	public static final String PREFIX = "redisson.config";

	private int threads = 16;

	private int nettyThreads = 4;

	private Class<? extends Codec> codec = JsonJacksonCodec.class;

	private Sentinel sentinel;

	private Single single;

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getNettyThreads() {
		return nettyThreads;
	}

	public void setNettyThreads(int nettyThreads) {
		this.nettyThreads = nettyThreads;
	}

	public Class<? extends Codec> getCodec() {
		return codec;
	}

	public void setCodec(Class<? extends Codec> codec) {
		this.codec = codec;
	}

	public Sentinel getSentinel() {
		return sentinel;
	}

	public void setSentinel(Sentinel sentinel) {
		this.sentinel = sentinel;
	}

	public Single getSingle() {
		return single;
	}

	public void setSingle(Single single) {
		this.single = single;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

class Single {

	private String address;

	private String password;

	private boolean keepAlive = false;

	private int database = 0;

	private int connectionPoolSize = 64;
	
	private int connectionMinimumIdleSize =24;

	private int timeout = 3;

	private String username;

	private int connectTimeout;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}
	

	public int getConnectionMinimumIdleSize() {
		return connectionMinimumIdleSize;
	}

	public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
		this.connectionMinimumIdleSize = connectionMinimumIdleSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

class Sentinel {

	private String[] sentinelAddresses;

	private String password;

	private String masterName;

	private int database = 0;

	private int idleConnectionTimeout = 10000;

	private int connectTimeout = 10000;

	private int masterConnectionMinimumIdleSize = 24;

	private int masterConnectionPoolSize = 64;

	private boolean checkSentinelsList = true;

	private String readMode = "SLAVE_MASTER";

	private boolean keepAlive = false;

	private Class<? extends LoadBalancer> loadBalancer = RoundRobinLoadBalancer.class;

	public String[] getSentinelAddresses() {
		return sentinelAddresses;
	}

	public boolean getCheckSentinelsList() {
		return checkSentinelsList;
	}

	public void setCheckSentinelsList(boolean checkSentinelsList) {
		this.checkSentinelsList = checkSentinelsList;
	}

	public void setSentinelAddresses(String[] sentinelAddresses) {
		this.sentinelAddresses = sentinelAddresses;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getIdleConnectionTimeout() {
		return idleConnectionTimeout;
	}

	public void setIdleConnectionTimeout(int idleConnectionTimeout) {
		this.idleConnectionTimeout = idleConnectionTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getMasterConnectionMinimumIdleSize() {
		return masterConnectionMinimumIdleSize;
	}

	public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
		this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
	}

	public int getMasterConnectionPoolSize() {
		return masterConnectionPoolSize;
	}

	public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
		this.masterConnectionPoolSize = masterConnectionPoolSize;
	}

	public String getReadMode() {
		return readMode;
	}

	public void setReadMode(String readMode) {
		this.readMode = readMode;
	}

	public boolean getkeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public Class<? extends LoadBalancer> getLoadBalancer() {
		return loadBalancer;
	}

	public void setLoadBalancer(Class<? extends LoadBalancer> loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

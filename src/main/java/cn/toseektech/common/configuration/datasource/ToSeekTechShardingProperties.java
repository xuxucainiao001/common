package cn.toseektech.common.configuration.datasource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = ToSeekTechShardingProperties.PREFIX)
public class ToSeekTechShardingProperties {

	public static final String PREFIX = "spring.datasource";

	private String[] shardingTables;

	private String database;

	public String[] getShardingTables() {
		return shardingTables;
	}

	public void setShardingTables(String[] shardingTables) {
		this.shardingTables = shardingTables;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

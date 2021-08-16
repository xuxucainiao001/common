package cn.toseektech.common.configuration.datasource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ToSeekTechDataSourceConfiguration {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 表路由负载均衡策略
	 */
	private static final String LOAD_BALANCE = "round_robin";

	/**
	 * 主节点名称
	 */
	private static final String MASTER_DATABASE_NAME = "master";

	@Resource
	private Environment evn;

	@Bean(MASTER_DATABASE_NAME)
	@ConfigurationProperties(prefix = "spring.datasource.druid.master")
	@ConditionalOnProperty(name = "spring.datasource.druid.master.enable",havingValue = "true")
	public DruidDataSource masterDateSource() {
		return new DruidDataSourceBuilder().build();
	}

	@Bean("slave0")
	@ConfigurationProperties(prefix = "spring.datasource.druid.slave0")
	@ConditionalOnProperty(name = "spring.datasource.druid.slave0.enable",havingValue = "true")
	public DruidDataSource slave0DateSource() {
		return new DruidDataSourceBuilder().build();
	}
	
	@Bean("slave1")
	@ConfigurationProperties(prefix = "spring.datasource.druid.slave1")
	@ConditionalOnProperty(name = "spring.datasource.druid.slave1.enable",havingValue = "true")
	public DruidDataSource slave1DateSource() {
		return new DruidDataSourceBuilder().build();
	}
	
	@Bean("slave2")
	@ConfigurationProperties(prefix = "spring.datasource.druid.slave2")
	@ConditionalOnProperty(name = "spring.datasource.druid.slave2.enable",havingValue = "true")
	public DruidDataSource slave2DateSource() {
		return new DruidDataSourceBuilder().build();
	}
	
	@Bean("slave3")
	@ConfigurationProperties(prefix = "spring.datasource.druid.slave3")
	@ConditionalOnProperty(name = "spring.datasource.druid.slave3.enable",havingValue = "true")
	public DruidDataSource slave3DateSource() {
		return new DruidDataSourceBuilder().build();
	}

	@Bean
	public ToSeekTechShardingProperties shardingProperties() {
		return new ToSeekTechShardingProperties();
	}

	@Primary
	@Bean("dataSource")
	@ConditionalOnBean({DruidDataSource.class,ToSeekTechShardingProperties.class})
	public DataSource createShardingDataSource(Map<String, DruidDataSource> druidDataSourceMap,
			ToSeekTechShardingProperties shardingProperties) throws SQLException {

		// 配置分片规则
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		if (shardingProperties.getShardingTables() != null) {
			for (String tables : shardingProperties.getShardingTables()) {
				String shardingFeildAndCount = StringUtils.substringBetween(tables, "(", ")");
				String tableName = StringUtils.split(tables, "(")[0];
				String[] shardingFeildAndCounts = StrUtil.splitToArray(shardingFeildAndCount, " ");
				String shardingFeild = shardingFeildAndCounts[0];
				int shardingCount = Integer.parseInt(shardingFeildAndCounts[1]);
				shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration(shardingProperties.getDatabase(),
						tableName, shardingFeild, shardingCount));
			}
		}

		// 获取所有读库名称
		List<String> slaveNames = druidDataSourceMap.keySet().stream().filter(k -> !MASTER_DATABASE_NAME.equals(k))
				.collect(Collectors.toList());
		// 读写分离负载均衡
		LoadBalanceStrategyConfiguration loadBalanceStrategyConfiguration = new LoadBalanceStrategyConfiguration(
				LOAD_BALANCE);
		// 读写分离配置
		MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration(
				shardingProperties.getDatabase(), MASTER_DATABASE_NAME, slaveNames, loadBalanceStrategyConfiguration);
		shardingRuleConfig.setMasterSlaveRuleConfigs(Lists.newArrayList(masterSlaveRuleConfiguration));

		// 创建真正的sharding数据源

		// 类型转换 DruidDataSource -> DataSource
		Map<String, DataSource> dataSourceMap = Maps.newHashMap();
		druidDataSourceMap.entrySet().forEach(entry -> dataSourceMap.put(entry.getKey(), entry.getValue()));
		
		// 非生产环境打印sql
		Properties props = new Properties();
		String profile = evn.getActiveProfiles()[0];
		if (!"prod".equalsIgnoreCase(profile)) {
			props.put("sql.show", true);
		}
		// 创建 shardingDataSource
		return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, props);

	}

	/**
	 * 拼装所有需要分表的table节点字符串
	 * 
	 * @param logicTableName
	 * @return
	 */
	private String getTables(String databaseName, String logicTableName, Integer shardingCount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < shardingCount; i++) {
			sb.append(databaseName).append(".").append(logicTableName).append("_").append(i);
			if (i != shardingCount - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 表路由配置
	 * 
	 * @param logicTable
	 * @param shardingColumn
	 * @return
	 */
	private TableRuleConfiguration getTableRuleConfiguration(String database, String logicTable, String shardingColumn,
			int shardingCount) {
		TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(logicTable,
				getTables(database, logicTable, shardingCount));
		// 分表策略
		StandardShardingStrategyConfiguration csc = new StandardShardingStrategyConfiguration(shardingColumn,
				new FeildShardingAlgorithm(shardingCount));
		tableRuleConfiguration.setTableShardingStrategyConfig(csc);
		return tableRuleConfiguration;
	}

	/**
	 * 通过字段的值计算分表的位置
	 * 
	 * @author acer
	 *
	 */
	class FeildShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

		private int shardingCount;

		public FeildShardingAlgorithm(int shardingCount) {
			this.shardingCount = shardingCount;
		}

		@Override
		public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
			// 对feild进行hash防止雪花算法id奇偶不平衡现象
			int tableIndex = caculateIndex(shardingValue.getValue());
			for (String tableName : availableTargetNames) {
				String[] chars = StrUtil.splitToArray(tableName, "_");
				if (StrUtil.equals(chars[chars.length - 1], tableIndex + "")) {
					return tableName;
				}
			}
			logger.error("没有找到可路由的表，表节点：{}，路由字段：{}", availableTargetNames, shardingValue);
			throw new IllegalStateException("没有找到可路由的表！");
		}

		/**
		 * 雪花id的第17位加上第18位的和模以表数量
		 * 
		 * @param feildValue
		 * @return
		 */
		private int caculateIndex(Long feildValue) {
			String custId = Long.toString(feildValue);
			return (Integer.parseInt(custId.charAt(17) + "") + Integer.parseInt(custId.charAt(16) + ""))
					& (shardingCount - 1);
		}
	}

}

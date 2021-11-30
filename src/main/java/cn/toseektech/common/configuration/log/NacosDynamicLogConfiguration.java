package cn.toseektech.common.configuration.log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;

import cn.hutool.core.thread.ThreadUtil;

/**
 * Nacos动态修改日志级别
 * @author xuxu
 *
 *
 *  #本配置会自动监听 ${spring.application.name}.properties/  ${spring.application.name}.yml / ${spring.application.name}.yaml 下的配置
 *  #当配置发生变化时，会修改 logger.name 和 logger.level
 *  #注：为了避免ContextRefresher的影响，每次修改都延迟 6秒生效
 *  
 *  eg:
 *  # nacos 配置
 *  logger.name=ROOT
 *  logger.level=INFO
 *  
 *  当需要吧com.speakin包下的日志级别改为debug，则:
 *  logger.name=com.speakin
 *  logger.level=DEBUG
 *  
 *  如果需要把所有日志级别调整为DEBUG,则：
 *  logger.name=ROOT
 *  logger.level=DEBUG 
 *  
 *  如果需要把所有日志级别调整为INFO,则：
 *  logger.name=ROOT
 *  logger.level=INFO
 *  
 */
public class NacosDynamicLogConfiguration {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Value("${spring.application.name}")
	private String appName;

	private static final String LEVEL = "logger.level";
	
	private static final String NAME = "logger.name";

	private static final String YAML = ".yaml";

	private static final String YML = ".yml";

	private static final String PROPERTIES = ".properties";
		
	private ExecutorService scheduler = Executors.newSingleThreadExecutor();
	
	@Resource
	LoggingSystem loggingSystem;

	@Value("${" + LEVEL + ":INFO}")
	private String level;
	
	@Value("${" + NAME + ":ROOT}")
	private String name;

	@Resource
	private NacosConfigManager nacosConfigManager;

	@PostConstruct
	public void init() throws NacosException {	
		registerListener();
		changeLevel(this.name,this.level);
	}

	private void registerListener() throws NacosException {
		nacosConfigManager.getConfigService().addListener(this.appName + PROPERTIES,
				nacosConfigManager.getNacosConfigProperties().getGroup(), new AbstractListener() {

					@Override
					public void receiveConfigInfo(String configInfo) {
						Properties pro = new Properties();
						try {
							pro.load(new ByteArrayInputStream(configInfo.getBytes()));
						} catch (IOException e) {
							log.error("nacos读取配置失败：", e);
						}
						String nameValue = pro.getProperty(NAME);
						String levelValue = pro.getProperty(LEVEL);
						changeLevel(nameValue,levelValue);
					}
				});

		nacosConfigManager.getConfigService().addListener(this.appName + YAML, nacosConfigManager.getNacosConfigProperties().getGroup(),
				new AbstractListener() {

					@Override
					@SuppressWarnings("unchecked")
					public void receiveConfigInfo(String configInfo) {
						Yaml ymal = new Yaml();
						Object result = ymal.load(configInfo);
						String nameValue = ((Map<String,String>)result).get(NAME);
						String levelValue = ((Map<String,String>)result).get(LEVEL);
						changeLevel(nameValue,levelValue);
					}
				});
		
		nacosConfigManager.getConfigService().addListener(this.appName + YML, nacosConfigManager.getNacosConfigProperties().getGroup(),
				new AbstractListener() {

					@Override
					@SuppressWarnings("unchecked")
					public void receiveConfigInfo(String configInfo) {
						Yaml ymal = new Yaml();
						Object result = ymal.load(configInfo);
						String nameValue = ((Map<String,String>)result).get(NAME);
						String levelValue = ((Map<String,String>)result).get(LEVEL);
						changeLevel(nameValue,levelValue);
					}
				});
	}

	private void changeLevel(String nameValue,String levelValue) {
		//等待6秒，防止ContextRefresher 重置日志级别
		scheduler.execute(()->{
			ThreadUtil.safeSleep(6000);
			loggingSystem.setLogLevel(nameValue, LogLevel.valueOf(levelValue));
		});
	}

}

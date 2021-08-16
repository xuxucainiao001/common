package cn.toseektech.common.utils;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * spring静态工具类
 * 
 * @author xuxu
 *
 */
public class SpringUtils {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return SpringUtils.applicationContext;
	}
	

	public static AutowireCapableBeanFactory getBeanFactory() {
		return getApplicationContext().getAutowireCapableBeanFactory();
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

}

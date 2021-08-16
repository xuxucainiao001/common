package cn.toseektech.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.IdUtil;

public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    
	/**
	 * 普通对象复制
	 * @param <T>
	 * @param source
	 * @param clazz
	 * @return
	 */
	public static <T> T copy(Object source, Class<T> clazz) {
		try {
			Object target = clazz.newInstance();
			BeanUtil.copyProperties(source, target, false);
			return clazz.cast(target);
		} catch (Exception e) {
			logger.error("对象复制异常：{}", e);
			throw new UtilException("对象复制异常");
		}

	}
	
    
	/**
	 * 集合复制并转换为List
	 * @param <T>
	 * @param source
	 * @param targetClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T,R> List <R> listCopy(Collection<T> source, Class<R> targetClass){
		return collectionCopy(source,targetClass,ArrayList.class);
	}
	
	/**
    
	 * 通用集合复制
	 * @param <T>
	 * @param <E>
	 * @param <R>
	 * @param source
	 * @param targetClass
	 * @param containerClass
	 * @return
	 */
	public static <T,E,R extends Collection<E>> R collectionCopy(Collection<T> source, Class<E> targetClass,Class<R> containerClass) {
		 try {
			Collection<E> container  = containerClass.newInstance();
			source.stream().map(obj->copy(obj,targetClass)).forEach(container::add);
			return containerClass.cast(container);
		} catch (Exception e) {
			logger.error("集合复制异常：{}", e);
			throw new UtilException("集合复制异常");
		}
	}
	
	/**
	 * 生成id
	 * @return
	 */
	public static Long generateSnowflakeId() {
		return IdUtil.getSnowflake(1, 1).nextId();
	}
	
	/**
	 * 生成uuid
	 * @return
	 */
	public static String generateUUId() {
		return IdUtil.fastSimpleUUID();
	}
	
}

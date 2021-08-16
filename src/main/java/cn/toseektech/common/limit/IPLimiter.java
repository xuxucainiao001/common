package cn.toseektech.common.limit;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

/**
 * 简易的ip限流器，仅针对单个服务器
 * 
 * @author xuxu
 *
 */
public class IPLimiter {

	private static final Logger logger = LoggerFactory.getLogger(IPLimiter.class);

	// 根据IP分不同的令牌桶, 每天自动清理缓存
	private static LoadingCache<String, RateLimiter> ipLimitCache = CacheBuilder.newBuilder().maximumSize(1000)
			.weakKeys().weakValues().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, RateLimiter>() {
				@Override
				public RateLimiter load(String key) throws Exception {
					// 新的IP初始化 (限流每秒10个令牌响应)
					return RateLimiter.create(10);
				}
			});

	/**
	 * 本地ip方法限流
	 * 
	 * @param request
	 * @return
	 */
	public static final boolean dolimit(String ip) {
		try {
			RateLimiter limiter = ipLimitCache.get(ip);
			// 不等待，直接拦截返回
			return limiter.tryAcquire(0, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("ip限流发生异常：", e);
			return false;
		}
	}


}

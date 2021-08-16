package cn.toseektech.common.configuration.xxljob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;

public abstract class BaseJobHandler extends IJobHandler {

protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private String jobName = getJobName();
	 

	/**
	 * 执行逻辑封装
	 */
	@Override
	public ReturnT<String> execute(String param) {
		StopWatch sw = new StopWatch(jobName);
		try {
			logger.info("定时器任务{}执行开始", jobName);
			sw.start();
			execute0();
			sw.stop();
			logger.info("定时器任务{}执行结束, 执行时间: {}ms", jobName, sw.getTotalTimeMillis());
			return IJobHandler.SUCCESS;
		} catch (Throwable e) {
			sw.stop();
			logger.error("定时器任务{}执行发生异常: {}", jobName, e);
			return IJobHandler.FAIL;
		}

	}

	/**
	 * 子类覆盖定时器业务逻辑 @Title: execute0 @Description: @Date: 2021年5月24日
	 * 上午9:30:23 @throws Throwable
	 */
	protected abstract void execute0 () throws Throwable;

	/**
	 * 获取任务名称 @Title: getJobName @Description: @Date: 2021年5月24日
	 * 上午9:30:10 @return @throws
	 */
	public String getJobName() {
		return toLowerCaseFirstOne(getClass().getSimpleName());
	}

	/**
	 * 首字母转小写
	 * 
	 * @param simpleClassName
	 * @return
	 */
	private String toLowerCaseFirstOne(String simpleClassName) {
		if (Character.isLowerCase(simpleClassName.charAt(0))) {
			return simpleClassName;
		} else {
			return new StringBuilder().append(Character.toLowerCase(simpleClassName.charAt(0)))
					.append(simpleClassName.substring(1)).toString();
		}
	}

}

package cn.toseektech.common.configuration.xxljob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;

public abstract class BaseJobHandler extends IJobHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected String jobName = toLowerCaseFirstOne(getClass().getSimpleName());

	/**
	 * 执行逻辑封装
	 */
	@Override
	public void execute() throws Exception {
		String param = XxlJobHelper.getJobParam(); // 获取参数
		// 测试专用逻辑
		if ("test".equals(StringUtils.trimWhitespace(param))) {
			logger.info("{} 任务测试执行成功!", jobName);
			XxlJobHelper.handleSuccess();
			return;
		}
		try {
			XxlJobHelper.handleResult(200, execute0());
		} catch (Exception e) {
			logger.error("{} 任务执行失败：{}", jobName, e);
			XxlJobHelper.handleFail();
		}

	}

	/**
	 *  
	 *   @Description:子类覆盖定时器业务逻辑
	 *   @author: xuxu
	 *   @Title:execute0
	 *   @return
	 *   @throws Exception
	 */
	protected abstract String execute0() throws Exception;

	/**
	 * 
	 * @Description:获取任务名称
	 * @author: xuxu
	 * @Title:getJobName
	 * @return
	 */
	public String getJobName() {
		return this.jobName;
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

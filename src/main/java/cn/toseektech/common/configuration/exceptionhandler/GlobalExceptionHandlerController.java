package cn.toseektech.common.configuration.exceptionhandler;

import java.nio.charset.Charset;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.ContentCachingRequestWrapper;

import cn.toseektech.common.configuration.annotations.GlobalExceptionHandler;
import cn.toseektech.common.enums.ResponseEnum;
import cn.toseektech.common.exception.BussinessException;
import cn.toseektech.common.exception.ClientException;
import cn.toseektech.common.model.ResponseVO;

@ResponseBody
@ControllerAdvice(annotations = { GlobalExceptionHandler.class })
public class GlobalExceptionHandlerController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private HttpServletRequest request;

	/**
	 * 通用打印日志
	 */
	private void printRequestInfo() {
		String requestUri = request.getRequestURL().toString();
		String requestBody = "";
		if (request instanceof ContentCachingRequestWrapper) {
			ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
			requestBody = StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
					Charset.forName(wrapper.getCharacterEncoding()));

		}
		logger.error("异常请求地址：{}，异常请求参数：{}", requestUri, requestBody);
	}

	/**
	 * 普通业务异常 BussinessException
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(BussinessException.class)
	public ResponseVO<Object> bussinessExceptionHandler(BussinessException exception) {
		printRequestInfo();
		String errorCode = exception.getErrorCode();
		String errorMessage = exception.getErrorMessage();
		logger.info("业务处理异常：errorCode:{},errorMessage:{}", errorCode, errorMessage);
		ResponseVO<Object> responseVO = new ResponseVO<>();
		responseVO.setCode(errorCode);
		responseVO.setMessage(errorMessage);
		return responseVO;
	}

	/**
	 * 远程方法调用异常 ClientException
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(ClientException.class)
	public ResponseVO<Object> clientExceptionHandler(ClientException exception) {
		printRequestInfo();
		logger.error("远程方法调用异常：", exception);
		ResponseVO<Object>  responseVO = new ResponseVO<>();
		responseVO.setCode(ResponseEnum.SYSTEM_ERROR.getCode());
		responseVO.setMessage(ResponseEnum.SYSTEM_ERROR.getMessage());
		return responseVO;
	}

	/**
	 * 参数校验异常 MethodArgumentNotValidException
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseVO<Object> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
		printRequestInfo();
		BindingResult bindingResult = exception.getBindingResult();
		String message = null;
		if (bindingResult.hasErrors()) {
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
		}
		logger.error("参数校验异常：{}", message);
		ResponseVO<Object> responseDto = new ResponseVO<>();
		responseDto.setCode(ResponseEnum.VALIDATE_ERROR.getCode());
		responseDto.setMessage(message);
		return responseDto;
	}

	/**
	 * 系统异常 RuntimeException
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(RuntimeException.class)
	public ResponseVO<Object> runtimeExceptionHandler(RuntimeException exception) {
		printRequestInfo();
		logger.error("系统异常：", exception);
		ResponseVO<Object> responseDto = new ResponseVO<>();
		responseDto.setCode(ResponseEnum.SYSTEM_ERROR.getCode());
		responseDto.setMessage(ResponseEnum.SYSTEM_ERROR.getMessage());
		return responseDto;
	}

	/**
	 * 系统异常 Throwable
	 * 
	 * @param exception
	 * @return
	 */

	@ExceptionHandler(Throwable.class)
	public ResponseVO<Object> throwableHandler(Throwable t) {
		printRequestInfo();
		logger.error("系统异常：", t);
		ResponseVO<Object> responseDto = new ResponseVO<>();
		responseDto.setCode(ResponseEnum.SYSTEM_ERROR.getCode());
		responseDto.setMessage(ResponseEnum.SYSTEM_ERROR.getMessage());
		return responseDto;
	}

}

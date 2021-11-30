package cn.toseektech.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.StopWatch;

@Aspect
@Component
public class ExecuteTimeAspect {

    private final Logger logger = LoggerFactory.getLogger(ExecuteTimeAspect.class);

    @Pointcut("@annotation(printExecuteTime)")
    public void serviceExecutionTimeLog(PrintExecuteTime printExecuteTime) {
    }


    @Around(value = "serviceExecutionTimeLog(printExecuteTime)", argNames = "proceedingJoinPoint,printExecuteTime")
    public Object doAfter(ProceedingJoinPoint proceedingJoinPoint, PrintExecuteTime printExecuteTime) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();
        if(logger.isDebugEnabled()) {
        logger.debug("execute-time-name : [{}], execution-time : [{}ms], class-method : [{}]", printExecuteTime.name(),
                stopWatch.getTotalTimeMillis(),
                proceedingJoinPoint.getTarget().getClass().getName() + "." + proceedingJoinPoint.getSignature().getName());
        }
        return proceed;
    }
}
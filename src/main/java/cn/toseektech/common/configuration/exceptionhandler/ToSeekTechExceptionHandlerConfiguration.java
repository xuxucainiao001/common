package cn.toseektech.common.configuration.exceptionhandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToSeekTechExceptionHandlerConfiguration {
	   
	   @Bean
	   public GlobalExceptionHandlerController globalExceptionHandler() {
		   return new GlobalExceptionHandlerController();
	   }
	

}

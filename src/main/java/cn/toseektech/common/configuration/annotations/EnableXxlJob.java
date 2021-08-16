package cn.toseektech.common.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.toseektech.common.configuration.xxljob.XxlJobConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(XxlJobConfiguration.class)
public @interface EnableXxlJob {

}

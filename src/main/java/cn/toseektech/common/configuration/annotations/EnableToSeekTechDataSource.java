package cn.toseektech.common.configuration.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import cn.toseektech.common.configuration.datasource.ToSeekTechDataSourceConfiguration;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ToSeekTechDataSourceConfiguration.class)
public @interface EnableToSeekTechDataSource {

}

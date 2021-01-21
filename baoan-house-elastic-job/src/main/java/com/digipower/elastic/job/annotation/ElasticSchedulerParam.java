package com.digipower.elastic.job.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 分布式定时任务注解
 * 
 * @author zzg
 *
 */

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ElasticSchedulerParam {
	@AliasFor("value")
	String cron() default "";

	@AliasFor("cron")
	String value() default "";

	int shardingTotalCount() default 1;

	String shardingItemParameters() default "0=0";
}

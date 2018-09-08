package com.ebase.core.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注是否打印service的执行日志.<br />
 * 可标注在类或方法上, 标注在方法上的注解会覆盖标注在类上的注解.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface ServiceTraceable {

	public boolean enable() default true;

}

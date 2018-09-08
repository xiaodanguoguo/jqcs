package com.ebase.core.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ebase.core.validator.vali.api.FrameValidator;

/**
 * <p>
 * 校验框架<br>
 * 1,若是useAnnotation，则使用 java的validation校验，对注解存在的地方进行校验
 * 2,使用自己写的校验类进行校验
 * 
 * </p>
 *
 * @project core
 * @class Valid
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD})
public @interface Vali {
	
	/**
	 * 扩展校验类
	 * @return
	 */
	Class<? extends FrameValidator>[] value() default {};
	
	/**
	 * 是否是用java validator注解校验
	 * @return
	 */
	boolean useAnnotation() default false;
	
}

package com.ebase.core.validator.vali.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * 校验类
 * </p>
 *
 * @project core
 * @class Validator
 */
@SuppressWarnings("all")
public abstract class FrameValidator {

	private final static Logger logger = LoggerFactory.getLogger(FrameValidator.class);
	
	private List<Va<?>> list = new ArrayList<>();

	//初始化
	public void init() {
		initVla();
	}

	//触发修改
	public abstract void initVla();

	public void destory() {
		list = null;
	}

	public void addVla(String expression, Validator<?> vali) {
		list.add(new Va(expression, vali));
	};

	public void validate(ValidatorContext context) {
		try {
			doInitValidate(context);
			doValidate(context);
		} catch (Exception e) {
			throw new ValidationException("", e);
		}
	}

	private void doInitValidate(ValidatorContext context) {
		
		Method vaMethod = ReflectionUtils.findMethod(Validator.class, "isValid",Object.class,ValidatorContext.class);
		
		for (Va<?> va : list) {
			try {
				Validator<?> vali = va.getVali();
				String exp = va.getExpress();
				context.begin(exp);
				Object o = context.get(va.getExpress());
				logger.info("exp:{}, obj:{},val:{}",exp,o,vali);
				Object[] params = new Object[2];
				params[0] = o;
				params[1] = context;
				ReflectionUtils.invokeMethod(vaMethod, vali, params);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				context.clear();
			}
		}
	}

	protected void doValidate(ValidatorContext context) {

	}

	static class Va<T> {

		private String express;

		private Validator<T> vali;

		public Va(String express, Validator<T> vali) {
			super();
			this.express = express;
			this.vali = vali;
		}

		public String getExpress() {
			return express;
		}

		public void setExpress(String express) {
			this.express = express;
		}

		public Validator<T> getVali() {
			return vali;
		}

		public void setVali(Validator<T> vali) {
			this.vali = vali;
		}

	}

}

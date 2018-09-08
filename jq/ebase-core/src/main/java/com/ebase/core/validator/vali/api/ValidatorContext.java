package com.ebase.core.validator.vali.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ebase.core.validator.method.MethodParamNameContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * <p>
 * 校验的content
 * </p>
 *
 * @project core
 * @date 2017年9月4日下午5:10:47
 */
public class ValidatorContext {

	private Method method;

	private List<String> errorList = new ArrayList<>();

	private ExpressionParser parser = new SpelExpressionParser();

	private StandardEvaluationContext context = new StandardEvaluationContext();

	public ValidatorContext(Method method, Object[] arguments) {
		super();
		this.method = method;
		String[] params = MethodParamNameContext.getMethodParamNames(method);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				context.setVariable(params[i], arguments[i]);
			}
		}
	}

	private String currExpression;

	private Object currObject;

	public void begin(String expression) {
		currExpression = expression;
		currObject = get(expression);
	}

	public void clear() {
		currObject = null;
		currExpression = null;
	}

	public String getCurrExpression() {
		return currExpression;
	}

	public Object getCurrObject() {
		return currObject;
	}

	public Object getParam(String paramsName) {
		return context.lookupVariable(paramsName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T  getParam(String paramsName,Class<T> clazz) {
		return (T) getParam(paramsName);
	}

	public Object get(String express) {
		Expression expression = parser.parseExpression(express);
		return expression.getValue(context);
	}

	public <T> T get(String express, Class<T> clazz) {
		Expression expression = parser.parseExpression(express);
		return expression.getValue(context, clazz);
	}

	public void add(String errorMessage) {
		errorList.add(errorMessage);
	}

	public int size() {
		return errorList.size();
	}

	public Method getMethod() {
		return method;
	}

	public Iterator<String> iterator() {
		return errorList.iterator();
	}

}

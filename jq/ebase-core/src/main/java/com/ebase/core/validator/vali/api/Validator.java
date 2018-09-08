package com.ebase.core.validator.vali.api;

public interface Validator<T> {
	
	public void isValid(T t, ValidatorContext context);
	
}

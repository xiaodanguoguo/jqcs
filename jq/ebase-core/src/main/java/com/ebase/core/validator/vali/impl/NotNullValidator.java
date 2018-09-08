package com.ebase.core.validator.vali.impl;

import com.ebase.core.validator.vali.api.Validator;
import com.ebase.core.validator.vali.api.ValidatorContext;

public class NotNullValidator implements Validator<Object> {

	@Override
	public void isValid(Object t, ValidatorContext context) {
		if(t==null){
			context.add(context.getCurrExpression()+"不能为null");
		}
	}

}

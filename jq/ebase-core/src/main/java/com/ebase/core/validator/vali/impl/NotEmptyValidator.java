package com.ebase.core.validator.vali.impl;

import com.ebase.core.validator.vali.api.Validator;

import com.ebase.core.validator.vali.api.ValidatorContext;


public class NotEmptyValidator implements Validator<String> {

	@Override
	public void isValid(String t, ValidatorContext context) {
		if(t==null||t.trim().equals("")){
			context.add(context.getCurrExpression()+"不能为空");
		}
	}

	
}

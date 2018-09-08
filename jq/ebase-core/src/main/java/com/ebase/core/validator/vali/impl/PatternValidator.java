package com.ebase.core.validator.vali.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ebase.utils.StringUtil;
import com.ebase.core.validator.vali.api.Validator;
import com.ebase.core.validator.vali.api.ValidatorContext;

public class PatternValidator implements Validator<String> {
	
	/**邮箱*/
	public final static String PATTERN_EMAIL = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
	
	/**匹配汉字，一个或者多个*/
	public final static String PATTERN_HANZI = "^[\u0391-\uFFE5]+$";
	
	/**手机号码*/
	public final static String PATTERN_PHONE = "^1[3|4|5|8|7][0-9]\\d{8}$";
	
	/** 正数 */
	public final static String PATTERN_NUM = "^[1-9]\\d*$|0";
	
	/**
	 * 身份证，18位
	 */
	public final static String PATTERN_ID_CARD = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
	
	private java.util.regex.Pattern patternObject;
	
	private String pattern;
	
	public PatternValidator(String pattern,int flag) {
		this.pattern = pattern;
		patternObject = Pattern.compile(pattern);	
	}
	
	public PatternValidator(String pattern) {
		this.pattern = pattern;
		patternObject = Pattern.compile(pattern);	
	}

	@Override
	public void isValid(String t, ValidatorContext context) {
		if(StringUtil.isNotEmpty(t)){
			Matcher m = patternObject.matcher(t);
			if(!m.matches()){
				context.add(context.getCurrExpression()+"不符合正则：" + pattern);
			}
		}
	}

}

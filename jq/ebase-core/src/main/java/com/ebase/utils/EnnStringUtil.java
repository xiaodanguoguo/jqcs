package com.ebase.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnnStringUtil extends StringUtils {
	/**
	 * 是否为小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+");
		return pattern.matcher(str).matches();
	}
	
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher match = pattern.matcher(str);
		return match.matches();
	}
}

package com.ebase.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.alibaba.fastjson.JSONObject;
import com.ebase.core.web.json.JsonRequest;
import com.ebase.utils.json.FrameBeanSerializerModifier;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Json 工具类 jsonMapper的封装 附加一个 toPrettyJson
 * </p>
 *
 * @project core
 * @class JsonUtil
 * @date 2017年5月27日 下午1:44:43
 */
public class JsonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 默认jsonMapper处理对象
	 */
	private static ObjectMapper defaultMapper;

	/**
	 * NON_EMPTY 模式下的 jsonMapper对象
	 * spring boot 使用该mapper进行输出
	 */
	private static ObjectMapper noEmptyMapper;

	public static final org.codehaus.jackson.map.ObjectMapper OM = new org.codehaus.jackson.map.ObjectMapper();
	static{
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		OM.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
		OM.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	static {
		//获取当前时区
		Calendar calendar = new GregorianCalendar();
		TimeZone timeZone = calendar.getTimeZone();
		
		//dateformat
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		defaultMapper = new ObjectMapper();
		
		defaultMapper.setTimeZone(timeZone);
		// 设置输出时包含属性的风格
		defaultMapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		defaultMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		
		//是否美化格式输出
		defaultMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		defaultMapper.setDateFormat(dateFormat);
		
		//对array set list 为null 返回一个空 []
		defaultMapper.setSerializerFactory(defaultMapper.getSerializerFactory().withSerializerModifier(new FrameBeanSerializerModifier()));
		
		noEmptyMapper = new ObjectMapper();
		noEmptyMapper.setTimeZone(timeZone);
		// 设置输出时包含属性的风格
		noEmptyMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		noEmptyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		//是否美化格式输出
		noEmptyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		noEmptyMapper.setDateFormat(dateFormat);
	}

	/**
	 * 将object 转换成json toJson
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			return defaultMapper.writeValueAsString(obj);
		} catch (Exception ex) {
			throw new RuntimeException("toJson转换错误!", ex);
		}
	}


	public static <T> JsonRequest<T> stringToJsonResponse(String json, Class<T> clazz){
		JsonRequest resutlData = (JsonRequest) JSONObject.parseObject(json, JsonRequest.class);
		T resBody = JSONObject.toJavaObject((JSONObject) resutlData.getReqBody(), clazz);
		resutlData.setReqBody(resBody);
		return resutlData;
	}

	/**
	 * 将json转换成object parseObject
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		try {
			return defaultMapper.readValue(json, clazz);
		} catch (Exception ex) {
			throw new RuntimeException("parseObject转换错误!", ex);
		}
	}

	/**
	 * 输出jsonp null 字段也会展示 toJsonP
	 * 
	 * @param functionName
	 * @param object
	 * @return
	 */
	public static String toJsonP(String functionName, Object object) {
		try {
			JSONPObject jsonp = new JSONPObject(functionName, object);
			return noEmptyMapper.writeValueAsString(jsonp);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("toJsonP转换错误!", ex);
		}
	}
	
	/**
	 * tojson null 会展示出来 toJsonWithEmpty
	 * 
	 * @param o
	 * @return
	 */
	public static String toJsonNoEmpty(Object o) {
		try {
			return defaultMapper.writeValueAsString(o);
		} catch (Exception ex) {
			throw new RuntimeException("toJsonNoEmpty转换错误!", ex);
		}
	}

	/**
	 * 输出jsonp 忽略null字段 toJsonPWithEmpty
	 * 
	 * @param functionName
	 * @param object
	 * @return
	 */
	public static String toJsonPNoEmpty(String functionName, Object object) {
		try {
			JSONPObject jsonp = new JSONPObject(functionName, object);
			return defaultMapper.writeValueAsString(jsonp);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("toJsonPNoEmpty转换错误!", ex);
		}
	}
	
	/**
	 * 将object 转换成 格式化的json，利于查看 toPrettyJson
	 * 
	 * @param obj
	 * @return
	 */
	public static String toPrettyJson(Object obj) {
		try {
			return noEmptyMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException("toPrettyJson转换错误!", ex);
		}
	}

	/**
	 * 获取默认模式下的 ObjectMapper
	 */
	public static ObjectMapper getMapper() {
		return defaultMapper;
	}

	/**
	 * 获取NON_EMPTY 模式下的 ObjectMapper getnoEmptyMapper
	 * 
	 * @return
	 */
	public static ObjectMapper getNoEmptyMapper() {
		return noEmptyMapper;
	}

	/**
	 * json串转成 对象
	 * 
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz){
		try {
			return OM.readValue(json, clazz);
		} catch (JsonParseException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}

}

package com.ebase.core.web;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.google.common.collect.ImmutableList;

import com.ebase.utils.JsonUtil;

/**
 * <p>
 * 对返回的bean转json进行处理,为空对象的处理 平台的对象返回new Object() list set map 的基本处理
 * </p>
 *
 * @project core
 * @class FrameJsonHttpMessageConverter
 */
public class JacksonJsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

	public JacksonJsonHttpMessageConverter() {
		this.setSupportedMediaTypes(ImmutableList.of(MediaType.TEXT_HTML, MediaType.APPLICATION_JSON));
		this.setObjectMapper(JsonUtil.getMapper());
		this.setDefaultCharset(Charset.forName("UTF-8"));
	}
	
}

package com.ebase.core.web.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import com.ebase.core.web.JacksonJsonHttpMessageConverter;

/**
 * <p>
 * 自动加入spring json 序列化处理类
 * </p>
 * @ConditionalOnProperty(prefix="spring.json", value="enabled",havingValue="true") //spring.json.enabled=true 这个类的bean才会加载
 * @project core
 * @class JSONConverterAutoConfigure
 */
@Configuration
public class JSONConverterAutoConfigure {
	
	@Bean
	public HttpMessageConverter<Object> json() {
		JacksonJsonHttpMessageConverter converter = new JacksonJsonHttpMessageConverter();
		return converter;
	}
	
}

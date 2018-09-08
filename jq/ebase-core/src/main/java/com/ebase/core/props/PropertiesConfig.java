package com.ebase.core.props;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.ebase.utils.StringUtil;

/**
 * <p>
 * 配置获取类， <br>
 * 代码初始化启动中获取的配置，请不要用这个类， 建议用spring @Value 注解
 * </p>
 *
 * @project core
 * @class PropertyConfigurer
 */
@Order(0)
public class PropertiesConfig implements EnvironmentAware {
	static Logger logger = LoggerFactory.getLogger(PropertiesConfig.class);

	static final String CONSTANT_PROFILE = "spring.profiles.active";

	private static Properties properties;
	private static Environment environment;

	public static Integer getIntProperty(String name) {
		if (properties == null) {
			return Integer.parseInt(environment.getProperty(name, "0"));
		} else {
			return Integer.parseInt(properties.getProperty(name, "0"));
		}
	}

	public static String getStringProperty(String name) {
		if (properties == null) {
			return environment.getProperty(name, "");
		} else {
			return properties.getProperty(name, "");
		}
	}

	public static String getProperty(String name, String defaultValue) {
		if (properties == null) {
			return environment.getProperty(name, defaultValue);
		} else {
			return properties.getProperty(name, defaultValue);
		}
	}

	public static Object get(String key) {
		if (properties == null) {
			return environment.getProperty(key);
		} else {
			return properties.getProperty(key);
		}
	}

	
	public void setProperties(Properties properties) {
		logger.info("加载配置：" + properties);
		PropertiesConfig.properties = properties;
	}

	public void setEnvironment(Environment environment) {
		logger.info("加载配置：" + environment);
		if (properties != null) {
			String profile = environment.getProperty(CONSTANT_PROFILE);
			if(StringUtil.isNotEmpty(profile)){
				properties.setProperty(CONSTANT_PROFILE, profile);
			}
		}
		PropertiesConfig.environment = environment;
	}

}

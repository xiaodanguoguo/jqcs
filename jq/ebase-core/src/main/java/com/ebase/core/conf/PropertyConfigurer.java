package com.ebase.core.conf;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * @Description: 自定义属性配置类,在SPRING的XML中配置加载所有的属性文件
 */

public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
    private static Properties properties;

    public static Integer getIntProperty(String name) {
        return Integer.parseInt(properties.getProperty(name, "0"));
    }

    public static String getStringProperty(String name) {
        return properties.getProperty(name, "");
    }

    public static String getProperty(String name,String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public static Object get(String key){
        return properties.getProperty(key);
    }


    public static Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        PropertyConfigurer.properties = properties;
    }
    
    private static final String MULTI_ENV_SYSTEM_KEY = "hejin.profiles.active";
    
    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();
        String envKey = System.getenv(MULTI_ENV_SYSTEM_KEY);
        if(StringUtils.isBlank(envKey)) {
            envKey = System.getProperty(MULTI_ENV_SYSTEM_KEY);
        }
        logger.info("被激活的配置文件:{}", envKey);
        String propertiesName = "application-" + envKey + ".properties";
        ClassPathResource classPathResource = new ClassPathResource(propertiesName);
        if(!classPathResource.exists()) {
            logger.warn("配置文件: {}, 没有被找到, 请确认在classpath下存在此文件", propertiesName);
        } else {
            properties = PropertiesLoaderUtils.loadProperties(classPathResource);
        }
        return properties;
    }
}

package com.ebase.core.web.autoconfig;

import com.ebase.core.props.PropertiesConfig;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class WebConfigProps implements EnvironmentAware {

	@Override
	public void setEnvironment(Environment environment) {
		new PropertiesConfig().setEnvironment(environment);
	}
	
}

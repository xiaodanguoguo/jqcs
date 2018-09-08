package jq.steel.cs.registry.center;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

import com.ebase.core.EnvConstant;
import com.ebase.core.StringHelper;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class RegisterApplication {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String active = System.getenv(EnvConstant.ACTIVE_KEY);
		if (StringHelper.isBlank(active)) {
			active = System.getProperty(EnvConstant.ACTIVE_KEY);
		}
		if (StringHelper.isBlank(active)) {
			active = StringHelper.getProfilesByArgs(args, EnvConstant.ACTIVE_KEY);
		}
		if (StringHelper.isNotBlank(active)) {
			System.setProperty(EnvConstant.SERVER_TYPE_KEY, "register-center_" + active);
			System.out.println("server.type:" + "register-center_" + active);
		} else {
			System.out.println("active is null");
			//
		}

		PropertyPlaceholderConfigurer c = new PropertyPlaceholderConfigurer();

		ApplicationContext ctx = new SpringApplicationBuilder(RegisterApplication.class).web(true).run(args);
	}
}

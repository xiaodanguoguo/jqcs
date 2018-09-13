package jq.steel.cs.webapps.cs.main;

import com.ebase.core.EnvironmentUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@SpringCloudApplication
//断路器
@EnableCircuitBreaker
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@ComponentScan(basePackages = {"jq.steel.cs.webapps.cs","com.ebase.core.fastdfs"})
@EnableFeignClients(basePackages = {"jq.steel.cs.services.base.api", "jq.steel.cs.services.cust.api" })
@EnableDiscoveryClient
@EnableEurekaClient
@Configuration
public class WebappsApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebappsApplication.class);
	}

	public static void main(String[] args) {
		//长短链接
		System.setProperty("http.keepAlive", "false");
		System.setProperty("server.TYPE", "webapp_op");
		EnvironmentUtil.setSystemEnv(args);
		SpringApplication.run(WebappsApplication.class, args);
	}


	@LoadBalanced
	@Bean(name = "restTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * 文件上传大小限制
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("5MB"); //KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("50MB");
		return factory.createMultipartConfig();
	}
}

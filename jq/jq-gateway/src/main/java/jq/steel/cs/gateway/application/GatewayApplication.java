package jq.steel.cs.gateway.application;

import com.ebase.core.EnvironmentUtil;
import jq.steel.cs.gateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringCloudApplication
//断路器
@EnableCircuitBreaker
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
//mq和datasource路径配置
@ComponentScan(basePackages = { "jq.steel.cs.gateway"})
//@MapperScan({ "com.cpic.webapps.**.mapper", "com.cpic.webapps.**.dao", "com.cpic.**.mapper", "com.cpic.common.**.dao"})
//开始spring事务
@EnableTransactionManagement
//mybatis扫描
//@MapperScan({"com.cpic.webapps.**.dao", "com.cpic.**.mapper"})
@EnableFeignClients(basePackages = { "jq.steel.cs.gateway" })
@EnableDiscoveryClient
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableZuulProxy
public class GatewayApplication {

	public static void main(String[] args) {
		//长短链接
//		System.setProperty("http.keepAlive", "false");
		System.setProperty("server.TYPE", "gateway");
		EnvironmentUtil.setSystemEnv(args);
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}

	@LoadBalanced
	@Bean(name = "restTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	// @Bean
	// public ConnectionFactory connectionFactory() {
	// CachingConnectionFactory connectionFactory = new
	// CachingConnectionFactory();
	// connectionFactory.setAddresses("localhost:5672");
	// connectionFactory.setUsername("guest");
	// connectionFactory.setPassword("guest");	
	// connectionFactory.setVirtualHost("/");
	// connectionFactory.setPublisherConfirms(true); //必须要设置
	// return connectionFactory;
	// }

}

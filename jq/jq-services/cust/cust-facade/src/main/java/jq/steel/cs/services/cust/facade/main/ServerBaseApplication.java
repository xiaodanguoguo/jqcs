package jq.steel.cs.services.cust.facade.main;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: wangyu
 */
@SpringBootApplication(scanBasePackages = "jq.steel.cs.services.cust.facade")
//@ImportResource({ "classpath*:/META-INF/spring/*.xml" })
//@EnableEurekaClient
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
//事务
@EnableTransactionManagement
@MapperScan({"jq.steel.cs.services.cust.facade.dao", "jq.steel.cs.services.cust.facade**.mapper"})
@ComponentScan(basePackages = {"com.ebase.core.cache", "jq.steel.cs.services.cust.facade"})
//断路器
@EnableCircuitBreaker
public class ServerBaseApplication {


    private static Logger logger = LoggerFactory.getLogger(ServerBaseApplication.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServerBaseApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        logger.info("service-cust start success");
    }





}

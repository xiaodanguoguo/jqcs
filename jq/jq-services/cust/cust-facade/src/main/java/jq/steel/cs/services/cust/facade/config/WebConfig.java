package jq.steel.cs.services.cust.facade.config;


import com.raqsoft.report.view.ReportServlet;
import com.raqsoft.report.view.ServletMappings;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@Configuration
public class WebConfig {
    @Bean
    public ServletRegistrationBean servletRegistrationBean() throws IOException {

        ServletRegistrationBean registration = new ServletRegistrationBean(new ReportServlet());

        registration.setLoadOnStartup(1);

        System.err.println(ClassLoader.getSystemResource("raqsoftConfig.xml").getPath());
        registration.addInitParameter("configFile", ClassLoader.getSystemResource("raqsoftConfig.xml").getPath());


        registration.addInitParameter("headless", "none");

        registration.setName("reportServlet");

        registration.addUrlMappings("/reportServlet");

        ServletMappings.mappings.put( "com.raqsoft.report.view.ReportServlet", "/reportServlet");

        System.out.println("润乾servlet注册完成");

        return registration;

    }

    @Bean
    public ResourceLoader createResourceLoader() {

        return new DefaultResourceLoader();
    }

}

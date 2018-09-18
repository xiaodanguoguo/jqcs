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

//        Resource resource = new ClassPathResource("raqsoftConfig.xml");
//        File file = resource.getFile();
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//
//        String line = null;
//        String message = new String();
//        final StringBuffer buffer = new StringBuffer(2048);
//        while ((line = br.readLine()) != null) {
//            // buffer.append(line);
//            message += line;
//        }
//        System.out.println(message);

        //        ResourceLoader resourceLoader = this.createResourceLoader();
//        Resource resource = resourceLoader.getResource("classpath:resources/raqsoftConfig.xml");

        registration.addInitParameter("configFile", "/WEB-INF/raqsoftConfig.xml");

        registration.addInitParameter("headless", "none");
//        registration.addInitParameter("configFile", message);

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

package jq.steel.cs.webapps.op.controller.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description: 自定义静态资源映射目录，如有权限注意排除
 */
@Component
public class WebConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("file:///"+uploadConfig.getUploadPath());
    }
}

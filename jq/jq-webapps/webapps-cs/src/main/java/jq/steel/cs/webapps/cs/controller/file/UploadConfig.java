package jq.steel.cs.webapps.cs.controller.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Auther: <a mailto:xuyongming@ennew.cn>xuyongming</a>
 * description:
 */

@Component
@PropertySource("classpath:config/${env}/upload.properties")
@ConfigurationProperties(prefix="res")
public class UploadConfig {

    //上传路径
    private String uploadPath;

    private String domain;

    private String pathPattern;

    //润乾地址
    private String reportUrl;

    //质证书ip
    private String millsheet;

    //润乾模板地址
    private String modelUrl;

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getMillsheet() {
        return millsheet;
    }

    public void setMillsheet(String millsheet) {
        this.millsheet = millsheet;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }
}
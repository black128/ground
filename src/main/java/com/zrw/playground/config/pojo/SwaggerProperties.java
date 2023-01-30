package com.zrw.playground.config.pojo;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

//    @Value("swagger.enable")
    private Boolean enable;
//    @Value("swagger.application-name")
    private String applicationName;
//    @Value("swagger.application-version")
    private String applicationVersion;
//    @Value("swagger.application-description")
    private String applicationDescription;
//    @Value("swagger.try-host")
    private String tryHost;

}

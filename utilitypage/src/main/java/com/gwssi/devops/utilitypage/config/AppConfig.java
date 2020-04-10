package com.gwssi.devops.utilitypage.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class AppConfig {
    @Value("${spring.profiles.active}")
    private String runMode;//运行模式
}

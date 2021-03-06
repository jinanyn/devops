package com.gwssi.devops.utilitypage.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.mail")
@Slf4j
@Data
public class MailConfig {

    private String from;
    private String defaultReciver;
    private String reciverPph;
    private String reciverCaseAssign;
    private String reciverUtility;
    private String host;
    private String username;
    private String password;
    private String protocol;
    private String reciverWaring;
}

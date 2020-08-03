package com.gwssi.devops.utilitypage.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
@Data
public class AppConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Value("${spring.profiles.active}")
    private String runMode;//运行模式

    private String serverHost;
    private int serverPort;
    private String serverAddress;
    public String getServerAddress(){
        return "http://"+this.serverHost+":"+this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            this.serverHost = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.serverPort = event.getWebServer().getPort();
    }
}

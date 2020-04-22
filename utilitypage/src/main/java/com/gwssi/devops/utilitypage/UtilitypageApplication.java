package com.gwssi.devops.utilitypage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations= "classpath:dubbo-${spring.profiles.active}.xml")
@SpringBootApplication(scanBasePackages={"com.gwssi","cn.gwssi"})
public class UtilitypageApplication {
    public static void main(String[] args) {
        SpringApplication.run(UtilitypageApplication.class, args);
    }

}

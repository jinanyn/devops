package com.gwssi.devops.utilitypage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.gwssi","cn.gwssi"})
public class UtilitypageApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilitypageApplication.class, args);
    }

}

package com.gwssi.devops.utilitypage;

import cn.gwssi.TestUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UtilitypageApplication {

    public static void main(String[] args) {
        //String rtnStr = TestUtil.tstMyMethod();
        //System.out.println("rtnStr = "+rtnStr);
        SpringApplication.run(UtilitypageApplication.class, args);
    }

}

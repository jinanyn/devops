package com.gwssi.devops.utilitypage.schedule.task;

import org.springframework.stereotype.Component;

/**
 * @program:
 * @description:
 * @author:
 * @date:
 **/
@Component("demoTask")
public class DemoTask {

    public void taskWithParams(String param1, Integer param2) {

        System.out.println("这是有参示例任务：" + param1 + param2);
    }

    public void taskNoParams() {

        System.out.println("执行无参示例任务");
    }
}

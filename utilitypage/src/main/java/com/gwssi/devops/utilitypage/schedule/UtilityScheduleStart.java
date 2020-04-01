package com.gwssi.devops.utilitypage.schedule;

import com.gwssi.devops.utilitypage.schedule.application.SchedulingRunnable;
import com.gwssi.devops.utilitypage.schedule.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.springframework.boot.ApplicationRunner;

@Component
public class UtilityScheduleStart implements ApplicationRunner {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskNoParams", null);
        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");

        SchedulingRunnable task1 = new SchedulingRunnable("demoTask", "taskWithParams", "haha", 23);
        cronTaskRegistrar.addCronTask(task1, "0/10 * * * * ?");
    }
}

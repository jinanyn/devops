package com.gwssi.devops.utilitypage.schedule;

import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
@Slf4j
public class PPHMultithreadScheduleTask {
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private MailHelperBuilder mailHelperBuilder;

    @Async
    @Scheduled(cron = "0 45 23 * * ?")// 每天晚上23:45触发
    //@Scheduled(cron = "0 48 14 * * ?")
    public void pphSinkBottomCase() {//PPH沉底案件监控
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PPH_SINK_BOTTOM_CASE, "pphSinkBottomCase",mailHelperBuilder);
    }
}

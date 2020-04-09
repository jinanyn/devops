package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.http.HttpRequstUtil;
import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
@Slf4j
public class UtilityMultithreadScheduleTask {
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailHelperBuilder mailHelperBuilder;
    //@Async
    //@Scheduled(fixedDelay = 1000)  //间隔1秒
    //@Scheduled(cron = "0/15 * * * * ?")
    public void first() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
        Thread.sleep(1000 * 10);
    }

    //@Async
    //@Scheduled(fixedDelay = 2000)
    //@Scheduled(cron = "0/5 * * * * ?")
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }

    //@Async
    //@Scheduled(fixedDelay = 2000)
    //@Scheduled(cron = "0/30 * * * * ?")
    public void thrid() {
        mailHelperBuilder.sendSimpleMessage(javaMailSender, "idea测试邮件","收到内||容没？？？");
    }

    @Async
    @Scheduled(cron = "0 15 1 ? * *")// 每天上午1:15触发
    public void bizMidfileAssignLateMonitor() {//中间文件分配过晚导致回案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_MIDFILE_ASSIGN_LATE,"bizMidfileAssignLate");
    }

    @Async
    @Scheduled(cron = "0 30 1 ? * *")// 每天上午1:30触发
    public void bizWarrantyEventExceptionMonitor() {//授权通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_WARRANTY_EVENT_EXCEPTION,"bizWarrantyEventException");
    }
    @Async
    @Scheduled(cron = "0 45 1 ? * *")// 每天上午1:45触发
    public void bizDivisionEventExceptionMonitor() {//分案视未通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_DIVISION_EVENT_EXCEPTION,"bizDivisionEventException");
    }

    @Async
    @Scheduled(cron = "0 0 2 ? * *")// 每天上午2:00触发
    public void bizOverCaseDateBlackMonitor() {//已结案案件结案日期为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_OVER_CASE_DATE_BLACK,"bizOverCaseDateBlack");
    }

    @Async
    @Scheduled(cron = "0 0/15 * * * ?")// 15分钟触发一次
    public void bizNoticeSoftscanFailMonitor() {//通知书软扫失败或软扫回调失败
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_NOTICE_SOFTSCAN_FAIL,"bizNoticeSoftscanFail");
    }

    @Async
    @Scheduled(cron = "0 15 2 ? * *")//"0 15 2 ? * *" 每天上午2:15触发
    public void authCaseFivebookMissMonitor() {//授权案件五书缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS,"authCaseFivebookMiss");
    }

    @Async
    @Scheduled(cron = "0 30 2 ? * *")//"0 15 2 ? * *" 每天上午2:30触发
    public void caseStateExceptionMonitor() {//当前状态表和电子文件夹状态不对应
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig,BusinessConstant.BIZ_CASE_STATE_EXCEPTION,"caseStateException");
    }

}

package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.http.HttpRequstUtil;
import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.util.PathUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.model.RtnDataList;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.PathConfig;
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
    @Scheduled(fixedDelay = 2000)
    @Scheduled(cron = "0 15 2 ? * *")//"0 15 10 ? * *" 每天上午10:15触发
    public void authCaseFivebookMissMonitor() {
        Map<String, String> paramMap = new HashMap<>();
        String login_flowid = "" + UUID.randomUUID();
        paramMap.put("username", pathConfig.getMainAppLoginUsername());
        paramMap.put("password", pathConfig.getMainAppLoginPassword());
        paramMap.put("login_flowid", login_flowid);
        paramMap.put("success_keyword", "<a href=\"javascript:void(0)\">案件审查</a>");
        CloseableHttpClient httpClient;
        try {
            httpClient = HttpRequstUtil.loginHttpClient(pathConfig.getMainAppLoginUri(), paramMap);
        } catch (IOException e) {
            log.error("登陆系统异常:"+pathConfig.getMainAppLoginUri());
            e.printStackTrace();
            return ;
        }
        paramMap = new HashMap<String, String>();
        paramMap.put("select-key:monitor_key", BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS);
        String xmlRtnData;
        try {
            xmlRtnData = HttpRequstUtil.sessionRequest(httpClient,pathConfig.getMainAppMonitorUri(),paramMap);
            //log.info(xmlRtnData);
        } catch (IOException e) {
            log.error("获取监控数据异常:"+pathConfig.getMainAppMonitorUri()+";参数:"+paramMap.toString());
            e.printStackTrace();
            return ;
        }

        //XML转为JAVA对象
        RtnDataList rtnDataList = (RtnDataList) XmlHelerBuilder.convertXmlToObject(RtnDataList.class, xmlRtnData);
        if(rtnDataList == null){
            log.info("返回的xml解析完毕后结果为空");
            return ;
        }
        List<RtnData> bizDataList = rtnDataList.getBizDataList();
        if(bizDataList == null){
            log.info("返回的xml解析完毕后业务数据为空");
            return ;
        }
        //log.info(rtnDataList.toString());
        StringBuilder resultBui = new StringBuilder();
        for (RtnData rtnData : bizDataList){
            String bizXmlData = XmlHelerBuilder.convertObjectToXml(rtnData);
            int idx = bizXmlData.indexOf("<rtnData>");
            bizXmlData = bizXmlData.substring(idx);
            resultBui.append(bizXmlData);
        }
        String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String targetPath = pathConfig.getShareDisk()+File.separator+currDate+File.separator+"authCaseFivebookMiss";
        FileHelperUtil.appendContentToFile(targetPath+ File.separator+"authCaseFivebookMiss",resultBui.toString());
    }
}

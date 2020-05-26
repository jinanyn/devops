package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.xml.XmlHelerBuilder;
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

    @Async
    @Scheduled(cron = "0 40 23 * * ?")// 每天晚上23:40触发
    //@Scheduled(cron = "0 52 14 * * ?")// 每天晚上23:45触发
    public void pphSupplementDeadlineOverdue() {//补正期限逾期监控
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PPH_SUPPLEMENT_DEADLINE_OVERDUE, "pphSupplementDeadlineOverdue");
        if(rtnDataList != null && rtnDataList.size() >0){
            StringBuilder slhBui = new StringBuilder();
            StringBuilder pphidBui = new StringBuilder();
            boolean firstFlag = true;
            for(RtnData rtnData : rtnDataList){
                if(firstFlag){
                    firstFlag = false;
                }else{
                    slhBui.append(",");
                    pphidBui.append(",");
                }
                slhBui.append(rtnData.getQixianslh());
                pphidBui.append(rtnData.getPphid());
            }
            String jsonObj="{qixianslh:\""+slhBui.toString()+"\",pphid:\""+pphidBui.toString()+"\"}";
            List<RtnData> bizDataList = UtilityServiceInvoke.commonBizHandleProcess(pathConfig,BusinessConstant.BIZ_PPH_SUPPLEMENT_DEADLINE_OVERDUE,jsonObj,"jsonObj");
            bizDataList.parallelStream().forEach(rtnData->{
                String bizXmlData = XmlHelerBuilder.convertObjectToXml(rtnData);
                boolean flag = true;
                //log.info("****************bizXmlData="+bizXmlData);
                if (bizXmlData.indexOf("<rtnData/>") != -1) {//空白数据
                    flag = false;
                }
                int idx = bizXmlData.indexOf("<rtnData>");
                if (idx == -1) {
                    flag = false;
                }
                if(flag){
                    mailHelperBuilder.sendSimpleMessage("PPH补正期限逾期数据需要处理","待处理数据生成语句："+rtnData.getText());
                }
            });
        }else{
        }
    }
}

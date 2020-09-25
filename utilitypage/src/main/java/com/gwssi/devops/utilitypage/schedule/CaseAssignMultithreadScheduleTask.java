package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.util.FileHelperUtil;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveClusterStreamCommands;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.List;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
@Slf4j
public class CaseAssignMultithreadScheduleTask {
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private MailHelperBuilder mailHelperBuilder;
    @Async
    @Scheduled(cron = "12 42 20 ? * *")// 每天晚上20:42:12触发
    //@Scheduled(cron = "0 0/2 * * * ?")// 2分钟触发一次
    public void priorityAuditDataRepeatMointer() {//优先审查数据重复自动处理(如果有重复数据,会影响案源数据提取存储过程)
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_AUDIT_DATA_REPEAT, "priorityAuditDataRepeat");
        if(rtnDataList != null && rtnDataList.size() >0){
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for(RtnData rtnData : rtnDataList){
                if(firstFlag){
                    firstFlag = false;
                }else{
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getShenqingh());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig,BusinessConstant.BIZ_PRIORITY_AUDIT_DATA_REPEAT,sqhBui.toString(),"shenqingh");
            String bizDesc = "发明案源gl_yxsc_ajscb表数据重复处理";
            StringBuilder resultBui = new StringBuilder();
            resultBui.append("本次共处理"+sqhBui.toString()+",请核查是否正常!!!");
            mailHelperBuilder.sendSimpleMessage(bizDesc,resultBui.toString());

            String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "priorityAuditDataRepeatMointer";
            FileHelperUtil.appendContentToFile(targetPath + File.separator + "priorityAuditDataRepeatMointer", resultBui.toString(),bizDesc);
        }else{
            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次未发现需要处理的数据!!!");
        }
    }
}

package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.util.FileHelperUtil;
import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Date;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Service
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
@Slf4j
public class UtilityMultithreadScheduleTask {
    @Autowired
    private PathConfig pathConfig;
    @Autowired
    private MailHelperBuilder mailHelperBuilder;
    @Autowired
    private AppConfig appConfig;

    @Async
    //@Scheduled(cron = "0 5 1 ? * *")// 每天上午1:05触发
    @Scheduled(cron = "0 5 11 * * ?")// 每天上午1:05触发
    public void bizMidfileAssignLateMonitor() {//中间文件分配过晚导致回案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_MIDFILE_ASSIGN_LATE, "midfileAssignLate",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 10 1 ? * *")// 每天上午1:10触发
    @Scheduled(cron = "0 10 11 * * ?")// 每天上午1:05触发
    public void bizWarrantyEventExceptionMonitor() {//授权通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WARRANTY_EVENT_EXCEPTION, "warrantyEventException",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 15 1 ? * *")// 每天上午1:15触发
    @Scheduled(cron = "0 15 11 * * ?")// 每天上午1:05触发
    public void bizDivisionEventExceptionMonitor() {//分案视未通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DIVISION_EVENT_EXCEPTION, "divisionEventException",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 20 1 ? * *")// 每天上午1:20触发
    @Scheduled(cron = "0 20 11 * * ?")// 每天上午1:05触发
    public void bizOverCaseDateBlackMonitor() {//已结案案件结案日期为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DATE_BLACK, "overCaseDateBlack",mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 0/ * * * ?")// 15分钟触发一次
    public void bizNoticeSoftscanFailMonitor() {//通知书软扫失败或软扫回调失败
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SOFTSCAN_FAIL, "noticeSoftscanFail",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 25 1 ? * *")// 每天上午1:25触发
    @Scheduled(cron = "0 25 11 * * ?")// 每天上午1:05触发
    public void authCaseFivebookMissMonitor() {//授权案件五书缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS, "authCaseFivebookMiss",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 30 1 ? * *")// 每天上午1:30触发
    @Scheduled(cron = "0 30 11 * * ?")// 每天上午1:05触发
    public void reconfirmFillingDateInconformity() {//重新确定申请日通知书发出申请日未变更
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_RECONFIRM_FILLING_DATE_INCONFORMITY, "reconfirmFillingDateInconformity",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 35 1 ? * *")// 每天上午1:35触发
    @Scheduled(cron = "0 35 11 * * ?")// 每天上午1:05触发
    public void bulletinBagFileMiss() {//公报袋数据缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_BULLETIN_BAG_FILE_MISS, "bulletinBagFileMiss",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 40 1 ? * *")// 每天上午1:40触发
    @Scheduled(cron = "0 40 11 * * ?")// 每天上午1:05触发
    public void dismissEventException() {//驳回通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DISMISS_EVENT_EXCEPTION, "dismissEventException",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 45 1 ? * *")// 每天上午1:45触发
    @Scheduled(cron = "0 45 11 * * ?")// 每天上午1:05触发
    public void workflowExceptionRegisterUnsend() {//工作流异常办理登记书无法发出
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND, "workflowExceptionRegisterUnsend",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 50 1 ? * *")// 每天上午1:50触发
    @Scheduled(cron = "0 50 11 * * ?")// 每天上午1:05触发
    public void caseStateExceptionMonitor() {//当前状态表和电子文件夹状态不对应
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_CASE_STATE_EXCEPTION, "caseStateException",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 55 1 ? * *")// 每天上午1:55触发
    @Scheduled(cron = "0 55 11 * * ?")// 每天上午1:05触发
    public void overCaseDepartmentBlack() {//结案案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DEPARTMENT_BLACK, "overCaseDepartmentBlack",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 0 2 ? * *")// 每天上午2:00触发
    @Scheduled(cron = "0 0 12 * * ?")// 每天上午1:05触发
    public void reviewCaseDepartmentBlack() {//在审案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_REVIEW_CASE_DEPARTMENT_BLACK, "reviewCaseDepartmentBlack",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 5 2 ? * *")// 每天上午2:05触发
    @Scheduled(cron = "0 5 12 * * ?")// 每天上午1:05触发
    public void priorityFeeUnpay() {//优先权要求费无原始费用，但界面展示费足
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_FEE_UNPAY, "priorityFeeUnpay",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 10 2 ? * *")// 每天上午2:10触发
    @Scheduled(cron = "0 25 12 * * ?")// 每天上午1:05触发
    public void noticeSendNewState() {//发送通知书，但案件仍为新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SEND_NEW_STATE, "noticeSendNewState",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 15 2 ? * *")// 每天上午2:10触发
    @Scheduled(cron = "0 30 12 * * ?")// 每天上午1:05触发
    public void noticeUnsendReplyState() {//未发送通知书，但案件状态为初审待答复或者回案审查
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UNSEND_REPLY_STATE, "noticeUnsendReplyState",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    @Scheduled(cron = "0 35 12 * * ?")// 每天上午1:05触发
    public void noticeUploadNewState() {//通知书已上传，但案件仍为未处理新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UPLOAD_NEW_STATE, "noticeUploadNewState",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    @Scheduled(cron = "0 40 12 * * ?")// 每天上午1:05触发
    public void priorityApplyUnhangup() {//在先申请该挂起未挂起
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_UNHANGUP, "priorityApplyUnhangup",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    @Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void priorityApplyNationBestUnwithdraw() {//在先申请该国优视撤未国优视撤
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW, "priorityApplyNationBestUnwithdraw",mailHelperBuilder);
    }

    @Async
    @Scheduled(fixedDelay = 60000)
    public void serverShareDiskStateMonitor() {//服务器共享存储访问正常
        if(!"prod".equals(appConfig.getRunMode())){
            log.info("服务器共享存储检测生产环境才能使用");
            //throw new RuntimeException("生产环境才能使用");
            return ;
        }
        UtilityServiceInvoke.checkShareDiskState(pathConfig,mailHelperBuilder);
    }


}

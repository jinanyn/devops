package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.http.HttpRequestUtil;
import cn.gwssi.util.ExceptionUtil;
import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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
    @Scheduled(fixedDelay = 60000)
    public void serverShareDiskStateMonitor() {//服务器共享存储访问正常
        if(!"prod".equals(appConfig.getRunMode())){
            log.info("服务器共享存储检测生产环境才能使用");
            //throw new RuntimeException("生产环境才能使用");
            return ;
        }
        UtilityServiceInvoke.checkShareDiskState(pathConfig,BusinessConstant.BIZ_SERVER_SHARE_DISK_STATE, mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 25 1 * * ?")// 每天上午1:25触发
    //@Scheduled(cron = "0 25 11 * * ?")// 每天上午1:05触发
    public void bizAuthCaseFivebookMissMonitor() {//授权案件五书缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS, "authCaseFivebookMiss",mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    @Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    public void bizInnerFormDataWhitespace() {//内部表单存在空格数据
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_INNER_FORM_DATA_WHITESPACE, "innerFormDataWhitespace",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 5 1 * * ?")// 每天上午1:05触发
    //@Scheduled(cron = "0 56 13 * * ?")// 每天上午1:05触发
    public void bizMidfileAssignLateMonitor() {//中间文件分配过晚导致回案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_MIDFILE_ASSIGN_LATE, "midfileAssignLate",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 10 1 * * ?")// 每天上午1:10触发
    //@Scheduled(cron = "0 0 14 * * ?")// 每天上午1:05触发
    public void bizWarrantyEventExceptionMonitor() {//授权通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WARRANTY_EVENT_EXCEPTION, "warrantyEventException",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 15 1 * * ?")// 每天上午1:15触发
    //@Scheduled(cron = "0 22 14 * * ?")// 每天上午1:05触发
    public void bizDivisionEventExceptionMonitor() {//分案视未通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DIVISION_EVENT_EXCEPTION, "divisionEventException",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 20 1 * * ?")// 每天上午1:20触发
    //@Scheduled(cron = "0 25 14 * * ?")// 每天上午1:05触发
    public void bizOverCaseDateBlackMonitor() {//已结案案件结案日期为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DATE_BLACK, "overCaseDateBlack",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    //@Scheduled(cron = "0 28 14 * * ?")// 每天上午1:05触发
    public void reconfirmFillingDateInconformity() {//重新确定申请日通知书发出申请日未变更
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_RECONFIRM_FILLING_DATE_INCONFORMITY, "reconfirmFillingDateInconformity",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 35 1 * * ?")// 每天上午1:35触发
    //@Scheduled(cron = "0 31 14 * * ?")// 每天上午1:05触发
    public void bulletinBagFileMiss() {//公报袋数据缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_BULLETIN_BAG_FILE_MISS, "bulletinBagFileMiss",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 40 1 * * ?")// 每天上午1:40触发
    //@Scheduled(cron = "0 17 10 * * ?")// 每天上午1:05触发
    public void dismissEventException() {//驳回通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DISMISS_EVENT_EXCEPTION, "dismissEventException",mailHelperBuilder);
    }
    @Async
    @Scheduled(cron = "0 45 1 * * ?")// 每天上午1:45触发
    //@Scheduled(cron = "0 32 14 * * ?")// 每天上午1:05触发
    public void workflowExceptionRegisterUnsend() {//工作流异常办理登记书无法发出
        try(CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)){
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND, "workflowExceptionRegisterUnsend",httpClient);
            rtnDataList.parallelStream().forEach(rtnData -> {
                String uri =pathConfig.getMainAppMonitorUri().replaceAll("txnDevopsMonitor01", "txn09Sqgzltz");
                Map<String, String> reqParam = new ConcurrentHashMap<>();
                reqParam.put("shenqingh", rtnData.getShenqingh());
                reqParam.put("rid", rtnData.getRid());
                reqParam.put("actionId", "1101A10");
                reqParam.put("instanceId", rtnData.getEntryId());
                log.info(uri+reqParam.toString());
                try {
                    HttpRequestUtil.sessionRequest(httpClient,uri,reqParam);
                } catch (IOException e) {
                    log.error("执行工作流修正异常:" + uri);
                    log.error(ExceptionUtil.getMessage(e));
                }
            });
            mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND),rtnDataList.toString());
        }catch (IOException e){
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    @Async
    @Scheduled(cron = "0 50 1 * * ?")// 每天上午1:45触发
    //@Scheduled(cron = "0 20 10 * * ?")// 每天上午1:05触发
    public void caseStateExceptionMonitor() {//当前状态表和电子文件夹状态不对应
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_CASE_STATE_EXCEPTION, "caseStateException",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 55 1 * * ?")// 每天上午1:55触发
    //@Scheduled(cron = "0 34 10 * * ?")// 每天上午1:05触发
    public void overCaseDepartmentBlack() {//结案案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DEPARTMENT_BLACK, "overCaseDepartmentBlack",mailHelperBuilder);
    }
    @Async
    @Scheduled(cron = "0 0 2 * * ?")// 每天上午2:00触发
    //@Scheduled(cron = "0 37 10 * * ?")// 每天上午1:05触发
    public void reviewCaseDepartmentBlack() {//在审案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_REVIEW_CASE_DEPARTMENT_BLACK, "reviewCaseDepartmentBlack",mailHelperBuilder);
    }
    @Async
    @Scheduled(cron = "0 10 2 * * ?")// 每天上午2:10触发
    //@Scheduled(cron = "0 20 11 * * ?")// 每天上午1:05触发
    public void noticeSendNewState() {//发送通知书，但案件仍为新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SEND_NEW_STATE, "noticeSendNewState",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 15 2 * * ?")// 每天上午2:15触发
    //@Scheduled(cron = "0 30 12 * * ?")// 每天上午1:05触发
    public void noticeUnsendReplyState() {//未发送通知书，但案件状态为初审待答复或者回案审查
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UNSEND_REPLY_STATE, "noticeUnsendReplyState",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 35 12 * * ?")// 每天上午1:05触发
    public void noticeUploadNewState() {//通知书已上传，但案件仍为未处理新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UPLOAD_NEW_STATE, "noticeUploadNewState",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 40 12 * * ?")// 每天上午1:05触发
    public void priorityApplyUnhangup() {//在先申请该挂起未挂起
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_UNHANGUP, "priorityApplyUnhangup",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 25 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void priorityApplyNationBestUnwithdraw() {//在先申请该国优视撤未国优视撤
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW, "priorityApplyNationBestUnwithdraw",mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 0 7 * * ?")// 每天上午7:00触发
    public void viewPtajcxnewFresh() {//物化视图view_ptajcx_new刷新数据是否完成
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_VIEW_PTAJCX_NEW_FRESH, "viewPtajcxnewFresh",mailHelperBuilder);
    }

    //耗时太多,现只监控最近一年数据
    @Async
    @Scheduled(cron = "0 5 2 * * ?")// 每天上午2:05触发
    //@Scheduled(cron = "0 44 10 * * ?")// 每天上午1:05触发
    public void priorityFeeUnpay() {//优先权要求费无原始费用，但界面展示费足
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_FEE_UNPAY, "priorityFeeUnpay",mailHelperBuilder);
    }
















    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbztdtxa() {//新型审查表案件状态为待回，但主表案件状态为“等待提案”或者“新案审查”。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAZTDT, "ajdhZbajztyc1",mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbhawdf() {//新型审查表案件状态为待回，但主表案件状态为“回案审查”（无答复文件），需修改主表状态为初审待答复。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAHASC_WDF, "ajdhZbajztyc2",mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbhaydf() {//新型审查表案件状态为待回，但主表案件状态为“回案审查”（有答复文件），需修改主表状态为初审待答复。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAHASC_YDF, "ajdhZbajztyc3",mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztdt() {//案件审查表的案件状态为“回案审查”，主表状态为“等待提案”或者“新案审查”；
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZBZTBYZ, "scbhaZbztyc",mailHelperBuilder);
    }
    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_ftasfbzsy() {//新型审查表案件状态为待回，发出通知书非补正和审意，主表案件状态为其他情况
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_TZSCW, "ajdhZbajztyc1",mailHelperBuilder);
    }
    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztcsddfhxasc() {//案件审查表的案件状态为“回案审查”，主表状态为“初审待答复”或者“新案审查”；
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_HASC_ZBZTCSDFXSC, "scbhaZbztyc2",mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztwqt() {//案件审查表的案件状态为“回案审查”，主表状态为其他情况
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_HASC_ZBZTQT, "scbhaZbztyc",mailHelperBuilder);
    }


    //@Async
    //@Scheduled(cron = "0 0/ * * * ?")// 15分钟触发一次
    public void bizNoticeSoftscanFailMonitor() {//通知书软扫失败或软扫回调失败
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SOFTSCAN_FAIL, "noticeSoftscanFail",mailHelperBuilder);
    }
}

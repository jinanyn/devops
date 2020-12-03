package com.gwssi.devops.utilitypage.schedule;

import cn.gwssi.http.HttpRequestUtil;
import cn.gwssi.util.ExceptionUtil;
import cn.gwssi.util.FileHelperUtil;
import cn.gwssi.xml.XmlHelerBuilder;
import com.gwssi.devops.utilitypage.config.AppConfig;
import com.gwssi.devops.utilitypage.mail.MailHelperBuilder;
import com.gwssi.devops.utilitypage.model.RtnData;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import com.gwssi.devops.utilitypage.config.PathConfig;
import com.gwssi.devops.utilitypage.util.UtilityServiceInvoke;
import jdk.nashorn.internal.runtime.options.OptionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
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
        if (!"prod".equals(appConfig.getRunMode())) {
            log.info("服务器共享存储检测生产环境才能使用");
            //throw new RuntimeException("生产环境才能使用");
            return;
        }
        UtilityServiceInvoke.checkShareDiskState(pathConfig, BusinessConstant.BIZ_SERVER_SHARE_DISK_STATE, mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 25 1 * * ?")// 每天上午1:25触发
    //@Scheduled(cron = "0 25 11 * * ?")// 每天上午1:05触发
    public void bizAuthCaseFivebookMissMonitor() {//授权案件五书缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AUTH_CASE_FIVEBOOK_MISS, "authCaseFivebookMiss", mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    @Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    public void bizInnerFormDataWhitespace() {//内部表单存在空格数据
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_INNER_FORM_DATA_WHITESPACE, "innerFormDataWhitespace", mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 5 1 * * ?")// 每天上午1:05触发
    //@Scheduled(cron = "0 56 13 * * ?")// 每天上午1:05触发
    public void bizMidfileAssignLateMonitor() {//中间文件分配过晚导致回案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_MIDFILE_ASSIGN_LATE, "midfileAssignLate", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 10 1 * * ?")// 每天上午1:10触发
    //@Scheduled(cron = "0 0 14 * * ?")// 每天上午1:05触发
    public void bizWarrantyEventExceptionMonitor() {//授权通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WARRANTY_EVENT_EXCEPTION, "warrantyEventException", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 15 1 * * ?")// 每天上午1:15触发
    //@Scheduled(cron = "0 22 14 * * ?")// 每天上午1:05触发
    public void bizDivisionEventExceptionMonitor() {//分案视未通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DIVISION_EVENT_EXCEPTION, "divisionEventException", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 20 1 * * ?")// 每天上午1:20触发
    //@Scheduled(cron = "0 25 14 * * ?")// 每天上午1:05触发
    public void bizOverCaseDateBlackMonitor() {//已结案案件结案日期为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DATE_BLACK, "overCaseDateBlack", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 30 1 * * ?")// 每天上午1:30触发
    //@Scheduled(cron = "0 28 14 * * ?")// 每天上午1:05触发
    public void reconfirmFillingDateInconformity() {//重新确定申请日通知书发出申请日未变更
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_RECONFIRM_FILLING_DATE_INCONFORMITY, "reconfirmFillingDateInconformity", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 35 1 * * ?")// 每天上午1:35触发
    //@Scheduled(cron = "0 31 14 * * ?")// 每天上午1:05触发
    public void bulletinBagFileMiss() {//公报袋数据缺失
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_BULLETIN_BAG_FILE_MISS, "bulletinBagFileMiss", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 40 1 * * ?")// 每天上午1:40触发
    //@Scheduled(cron = "0 17 10 * * ?")// 每天上午1:05触发
    public void dismissEventException() {//驳回通知书发出事件记录异常
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_DISMISS_EVENT_EXCEPTION, "dismissEventException", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 45 1 * * ?")// 每天上午1:45触发
    //@Scheduled(cron = "0 45 10 * * ?")// 每天上午1:05触发
    public void workflowExceptionRegisterUnsend() {//工作流异常办理登记书无法发出
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND, "workflowExceptionRegisterUnsend", httpClient);
            StringBuffer strBui = new StringBuffer();
            rtnDataList.stream().forEach(rtnData -> {
                String uri = pathConfig.getMainAppMonitorUri().replaceAll("txnDevopsMonitor01", "txn09Sqgzltz");
                Map<String, String> reqParam = new ConcurrentHashMap<>();
                reqParam.put("shenqingh", rtnData.getShenqingh());
                reqParam.put("rid", rtnData.getRid());
                reqParam.put("actionId", "1101A10");
                reqParam.put("instanceId", rtnData.getEntryId());
                log.info(uri + reqParam.toString());
                try {
                    HttpRequestUtil.sessionRequest(httpClient, uri, reqParam);
                } catch (IOException e) {
                    log.error("执行工作流修正异常:" + uri);
                    log.error(ExceptionUtil.getMessage(e));
                }
                String bizXmlData = XmlHelerBuilder.convertObjectToXml(rtnData);
                int idx = bizXmlData.indexOf("<rtnData>");
                strBui.append(bizXmlData.substring(idx));
            });
            if (strBui.length() > 0) {//工作流修改完成后,会有14天授权后办登未发出监控来完成此项功能
                //mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND), strBui.toString());
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    @Async
    @Scheduled(cron = "0 50 1 * * ?")// 每天上午1:45触发
    //@Scheduled(cron = "0 39 16 * * ?")// 每天上午1:05触发
    public void caseStateExceptionMonitor() {//当前状态表和电子文件夹状态不对应
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_CASE_STATE_EXCEPTION, "caseStateException", httpClient);
            StringBuffer strBui = new StringBuffer();
            Map<String, String> ztMap = new HashMap<>();
            ztMap.put("1", "S050302");
            ztMap.put("2", "S050302");
            ztMap.put("3", "S050303");
            ztMap.put("4", "S050304");
            ztMap.put("5", "S050304");
            rtnDataList.stream().forEach(rtnData -> {
                //目前以xx_ajscb表状态为基准
                String shenqingh = rtnData.getShenqingh();
                String anjianzt = rtnData.getAnjianzt();
                String anjinaywzt = rtnData.getAnjianywzt();
                String dangqianztbh = rtnData.getDangqianztbh();
                String targetZt = ztMap.get(anjianzt);
                if (!anjinaywzt.equals(targetZt)) {
                    strBui.append("insert into backup_data_user.GG_ZLX_ZHU (SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, BUG_ID) select SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, sysdate||'案件状态不一致' from GG_ZLX_ZHU t where t.shenqingh = '");
                    strBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                    strBui.append("update gg_zlx_zhu t set t.anjianywzt = '").append(targetZt).append("' where t.shenqingh = '").append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                }
                if (!dangqianztbh.equals(ztMap.get(anjianzt))) {
                    strBui.append("insert into backup_data_user.GG_SCZT_DQZT (MODTIME, BUG_ID, GG_SCZT_DQZT_ID, SHENQINGH, WEINEIBH, DANGQIANZTBH, CHUANGJIANRDM, CHUANGJIANSJ, XIUGAIRDM, XIUGAISJ, REGNAME, REGTIME, MODNAME) select MODTIME, sysdate||'案件状态不一致', GG_SCZT_DQZT_ID, SHENQINGH, WEINEIBH, DANGQIANZTBH, CHUANGJIANRDM, CHUANGJIANSJ, XIUGAIRDM, XIUGAISJ, REGNAME, REGTIME, MODNAME from GG_SCZT_DQZT t where t.shenqingh = '");
                    strBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                    strBui.append("update gg_sczt_dqzt t set t.dangqianztbh = '").append(targetZt).append("' where t.shenqingh = '").append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                }
            });
            if (strBui.length() > 0) {
                mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.BIZ_CASE_STATE_EXCEPTION), strBui.toString());
                String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
                String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "caseStateExceptionMonitor";
                FileHelperUtil.appendContentToFile(targetPath + File.separator + "caseStateExceptionMonitor.txt", strBui.toString(), "当前状态表和电子文件夹状态不对应");
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    @Async
    @Scheduled(cron = "0 05 0 * * ?")// 每天上午1:55触发BIZ_NOTICE_SEND_DATE_IS_NULL
    //@Scheduled(cron = "0 34 10 * * ?")// 每天上午1:05触发
    public void overCaseDepartmentBlack() {//结案案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_OVER_CASE_DEPARTMENT_BLACK, "overCaseDepartmentBlack", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 0 2 * * ?")// 每天上午2:00触发
    //@Scheduled(cron = "0 37 10 * * ?")// 每天上午1:05触发
    public void reviewCaseDepartmentBlack() {//在审案件处室代码为空
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_REVIEW_CASE_DEPARTMENT_BLACK, "reviewCaseDepartmentBlack", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 10 2 * * ?")// 每天上午2:10触发
    //@Scheduled(cron = "0 20 11 * * ?")// 每天上午1:05触发
    public void noticeSendNewState() {//发送通知书，但案件仍为新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SEND_NEW_STATE, "noticeSendNewState", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 15 2 * * ?")// 每天上午2:15触发
    //@Scheduled(cron = "0 30 12 * * ?")// 每天上午12:30触发
    public void noticeUnsendReplyState() {//未发送通知书，但案件状态为初审待答复或者回案审查
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UNSEND_REPLY_STATE, "noticeUnsendReplyState");
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getShenqingh());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UNSEND_REPLY_STATE, sqhBui.toString(), "shenqingh");
        } else {
        }
    }

    @Async
    @Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 35 12 * * ?")// 每天上午1:05触发
    public void noticeUploadNewState() {//通知书已上传，但案件仍为未处理新案
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_UPLOAD_NEW_STATE, "noticeUploadNewState", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 03 15 * * ?")// 每天下午15.03触发
    public void priorityApplyUnhangup() {//在先申请该挂起未挂起
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_UNHANGUP, "priorityApplyUnhangup", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getZaixiansqh());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_UNHANGUP, sqhBui.toString(), "shenqingh");
        }
    }

    @Async
    @Scheduled(cron = "0 25 2 * * ?")// 每天上午2:25触发
    //@Scheduled(cron = "0 27 12 * * ?")// 每天上午1:05触发
    public void priorityApplyNationBestUnwithdraw() {//在先申请该国优视撤未国优视撤
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW, "priorityApplyNationBestUnwithdraw", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            StringBuilder ridBui = new StringBuilder();
            for (RtnData rtnData : rtnDataList) {
                sqhBui.append("," + rtnData.getZaixiansqh());
                ridBui.append("," + rtnData.getRid());
            }
            //UtilityServiceInvoke.commonBizHandleProcess(pathConfig,BusinessConstant.BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW,sqhBui.toString(),"shenqingh");
            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次共处理"+sqhBui.toString()+",请核查是否正常!!!");
            String jsonObj = "{is_exec:\"true\",shenqingh:\"" + sqhBui.toString().replaceFirst(",", "") + "\",rid:\"" + ridBui.toString().replaceFirst(",", "") + "\"}";
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW, jsonObj, "jsonObj");
        } else {
            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次未发现需要处理的数据!!!");
        }
    }

    @Async
    @Scheduled(cron = "0 30 2 * * ?")// 每天上午2:30触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void noticeRegistrationUnsendLongTime() {//授权发出后长时间未发办登(14天)
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_REGISTRATION_UNSEND_LONG_TIME, "noticeRegistrationUnsendLongTime", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 0 7 * * ?")// 每天上午7:00触发
    public void viewPtajcxnewFresh() {//物化视图view_ptajcx_new刷新数据是否完成
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_VIEW_PTAJCX_NEW_FRESH, "viewPtajcxnewFresh", mailHelperBuilder);
    }

    //耗时太多,现只监控最近一年数据
    @Async
    @Scheduled(cron = "0 5 2 * * ?")// 每天上午2:05触发
    //@Scheduled(cron = "0 44 10 * * ?")// 每天上午1:05触发
    public void priorityFeeUnpay() {//优先权要求费无原始费用，但界面展示费足
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_FEE_UNPAY, "priorityFeeUnpay", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getRid());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.BIZ_PRIORITY_FEE_UNPAY, sqhBui.toString(), "guidingfyxh");
            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次共处理"+sqhBui.toString()+",请核查是否正常!!!");
        } else {
            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次未发现需要处理的数据!!!");
        }
    }

    @Async
    @Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    //@Scheduled(cron = "0 31 16 * * ?")// 每天上午1:05触发
    public void historyDataHandle() {//历史数据处理
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_HISTORY_DATA_HANDLE, "historyDataHandle", mailHelperBuilder);
    }

    @Async
    @Scheduled(cron = "0 5 0 * * ?")// 每天上午0:05触发
    //@Scheduled(cron = "0 25 14 * * ?")// 每天上午1:05触发
    public void noticeSendDateIsNull() {//通知书发出后发送日为空
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SEND_DATE_IS_NULL, "noticeSendDateIsNull");
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getRid());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SEND_DATE_IS_NULL, sqhBui.toString(), "rid");
        } else {
        }
    }

    @Async
    //@Scheduled(cron = "0 35 01 24 * ?")// 每月25日上午1:15触发
    @Scheduled(cron = "0 35 01 24 * ?")// "0 15 10 15 * ?"
    public void sinkCaseMonthStatisticData() {//沉底案件每月统计数据
        log.info("开始执行!!!!沉底案件每月统计数据");
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_SINK_CASE_MONTH_STATISTIC_DATA, "sinkCaseMonthStatisticData");
        StringBuilder resultBui = new StringBuilder();
        resultBui.append("沉底案件每月统计数据已生成，请尽快进行下一步处理。!!!");
        mailHelperBuilder.sendSimpleMessage("沉底案件每月统计数据", resultBui.toString());
        String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "sinkCaseMonthStatisticData";
        FileHelperUtil.appendContentToFile(targetPath + File.separator + "sinkCaseMonthStatisticData.txt", resultBui.toString(), "沉底案件每月统计数据");
    }

    @Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    @Scheduled(cron = "2 53 0 * * ?")// 每天上午2:53触发
    public void preconditionReviewErrorDataDelete() {//前置复核结论错误数据删除
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.PRECONDITION_REVIEW_ERROR_DATA_DELETE, "preconditionReviewErrorDataDelete", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getRid());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.PRECONDITION_REVIEW_ERROR_DATA_DELETE, sqhBui.toString(), "rid");
        } else {
        }
    }

    @Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    @Scheduled(cron = "1 53 0 * * ?")// 每天上午1:53触发
    public void priorityWaitResumeTermError() {//视为未要求,优先权等恢复期限建立错误
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.PRIORITY_WAIT_RESUME_TERM_ERROR, "priorityWaitResumeTermError", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getQixianslh());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.PRIORITY_WAIT_RESUME_TERM_ERROR, sqhBui.toString(), "qixianslh");
        } else {
        }
    }
    /*
     * 功能描述
     * @author yuyang
     * @date 2020-11-28 12:25:36
     * @param ：[没有在先申请被挂起的案件    `]
     * @return void
     */
    @Async
@Scheduled(cron = "0 11 2 * * ?")// 每天上午2:11触发
//@Scheduled(cron = "1 53 0 * * ?")// 每天上午1:53触发
    public void meiYouZaiXianShenQingBeiGuaQi() {//未要求优先权被挂起的案件
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_MEI_YOU_ZAI_XIAN_SHEN_QING_HAO_BEI_GUA_QI, "meiYouZaiXianShenQingBeiGuaQiD", httpClient);
            StringBuilder myzxBui = new StringBuilder();
            rtnDataList.parallelStream().forEach(rtnData -> {
                String shenqingh = rtnData.getZaixiansqh();//申请号
                log.info(shenqingh + ";");
                if (StringUtils.isNotEmpty(shenqingh)){
                    myzxBui.append("insert into backup_data_user.GG_ZLX_ZHU (SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, BUG_ID) select SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, '没有在先申请被挂起 ' || sysdate from GG_ZLX_ZHU t where t.shenqingh = '");
                    myzxBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                    myzxBui.append("update gg_zlx_zhu z set z.guaqibj='0' where z.shenqingh='").append(shenqingh).append("';");
                    myzxBui.append(FileHelperUtil.LINE_SEPARATOR);
                }
            });
            if (myzxBui.length() > 0) {
                mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.BIZ_MEI_YOU_ZAI_XIAN_SHEN_QING_HAO_BEI_GUA_QI), myzxBui.toString());
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
    @Async
@Scheduled(cron = "0 21 3 * * ?")// 每天上午3.21触发
//@Scheduled(cron = "1 53 0 * * ?")// 每天上午1:53触发
    public void zaiXianShenQingGaiJieGuaWeiJieGua() {//在先申请该挂解挂未解挂案件
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.ZAI_XIAN_SHEN_QING_GAI_JIE_GUA_WEI_JIE_GUA, "zaiXianShenQingGaiJieGuaWeiJieGua", httpClient);
            StringBuilder gjgBui = new StringBuilder();
            rtnDataList.stream().forEach(rtnData -> {
                String shenqingh = rtnData.getZaixiansqh();//申请号
                log.info(shenqingh + ";");
                if (StringUtils.isNotEmpty(shenqingh)){
                    if(gjgBui.length() >0){
                        gjgBui.append(FileHelperUtil.LINE_SEPARATOR);
                    }
                    gjgBui.append("insert into backup_data_user.GG_ZLX_ZHU (SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, BUG_ID) select SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, '在先申请该解挂未解挂 ' || sysdate from GG_ZLX_ZHU t where t.shenqingh = '");
                    gjgBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                    gjgBui.append("update gg_zlx_zhu t set t.guaqibj='0' where t.shenqingh='").append(shenqingh).append("';");
                }
            });
            if (gjgBui.length() > 0) {
                mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.ZAI_XIAN_SHEN_QING_GAI_JIE_GUA_WEI_JIE_GUA), gjgBui.toString());
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
    /*
     * 功能描述
     * @author yuyang
     * @date 2020-11-23 17:35:26
     * @param ：[在先申请该解挂未解挂案件自动解决]
     * @return void
     */
//    @Async
////@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
//@Scheduled(cron = "0 00 15 * * ?")// 每天上午1:53触发
//    public void zaiXianShenQingGaiJieGuaWeiJieGua() {//在先申请该解挂未解挂案件
//        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
//            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.Zai_Xian_Shen_Qing_Gai_Jie_Gua_Wei_Jie_Gua, "zaiXianShenQingGaiJieGuaWeiJieGua", httpClient);
//            rtnDataList.parallelStream().forEach(rtnData -> {
//                String shenqingh = rtnData.getZaixiansqh();//申请号
//                log.info(shenqingh + ";");
//                UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.Zai_Xian_Shen_Qing_Gai_Jie_Gua_Wei_Jie_Gua, shenqingh, "shenqingh");
//            });
//        } catch (IOException e) {
//            log.error(ExceptionUtil.getMessage(e));
//        }
//    }
    /*
     * 功能描述
     * @author yuyang
     * @date 2020-11-22 16:42:56
     * @param ：[添加在先申请该挂起未挂起案件/重复了]
     * @return void
    @Async
//@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
//@Scheduled(cron = "1 53 0 * * ?")// 每天上午1:53触发
    public void zaiXianShenQingGaiGuaQiWeiGuaQi() {//在先申请该挂起未挂起案件
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.Zai_Xian_Shen_Qing_Gai_Gua_Qi_Wei_Gua_Qi, "zaiXianShenQingGaiGuaQiWeiGuaQi", httpClient);
            rtnDataList.parallelStream().forEach(rtnData -> {
                String shenqingh = rtnData.getZaixiansqh();//申请号
                log.info(shenqingh + ";");
                UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.Zai_Xian_Shen_Qing_Gai_Gua_Qi_Wei_Gua_Qi, shenqingh, "shenqingh");
            });
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
 */
    /*
     * 功能描述
     * @author yuyang
     * @date 2020-11-18 17:01:08
     * @param ：[添加申请文件表权项数,段数，图片个数与实际情况不符]
     * @return void
     */
    @Async
    @Scheduled(cron = "0 18 4 * * ?")// 每天上午4:12触发
  //@Scheduled(cron = "0 05 15 * * ?")// 每天下午15.05触发
    public void dmhWjQueShiXxWjSqwj() {//申请文件表权项数,段数，图片个数与实际情况不符
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            StringBuffer strBui = new StringBuffer();
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.DMH_WJ_QUE_SHI_XX_WJ_SQWJ, "dmhWjQueShiXxWjSqwj", httpClient);
            rtnDataList.parallelStream().forEach(rtnData -> {
                String shenqingh = rtnData.getShenqingh();
                String rid = rtnData.getRid();
                String fid = rtnData.getXh();//fid
                String wenjianlxdm = rtnData.getTongzhislx();//文件类型
                log.info(shenqingh + ";" + fid + ";" + wenjianlxdm);
                String paramVal = fid + "," + shenqingh + "," + wenjianlxdm;
                List<RtnData> otherRtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.DMH_WJ_QUE_SHI_XX_WJ_SQWJ + "_1", paramVal, "fid,shenqingh,wenjianlxdm", httpClient);
                otherRtnDataList.parallelStream().forEach(otherRtnData -> {
                    String text = otherRtnData.getText();
                    log.info("text=" + text);
                    String jsonObj = "{is_exec:\"true\",text:\"`" + text + "`\",rid:\"" + rid + "\"}";
                    UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.DMH_WJ_QUE_SHI_XX_WJ_SQWJ + "_1", jsonObj, "jsonObj");
                });
            });
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }
    @Async
    @Scheduled(cron = "0 42 3 * * ?")// 每天上午4:12触发
    //@Scheduled(cron = "0 06 15 * * ?")// 每天上午12:30触发
    public void noticeRejectWorkflowXxPhase() {//发出驳回通知书后工作流还处于新型初审阶段
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            StringBuffer strBui = new StringBuffer();
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.NOTICE_REJECT_WORKFLOW_XX_PHASE, "noticeRejectWorkflowXxPhase", httpClient);
//             案例修改，写死个数据直接让他往下走
//            List<RtnData> rtnDataList = new ArrayList<>();
//            RtnData rtnData1 = new RtnData();
//            rtnData1.setShenqingh("2018205498182");
//            rtnData1.setAnjianywzt("S050305");
//            rtnDataList.add(rtnData1);
            rtnDataList.parallelStream().forEach(rtnData -> {
                String shenqingh = rtnData.getShenqingh();
                String anjianywzt = rtnData.getAnjianywzt();
                List<RtnData> otherRtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.NOTICE_REJECT_WORKFLOW_XX_PHASE + "_1", shenqingh, "shenqingh", httpClient);
                if (otherRtnDataList != null && otherRtnDataList.size() > 0) {
                    StringBuilder sqlBui = new StringBuilder();
                    if (!"1891S01".equals(anjianywzt)) {
                        sqlBui.append("insert into backup_data_user.GG_ZLX_ZHU (SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, BUG_ID) select SHENQINGH, ZHUANLIMC, ZHUANLIYWMC, ZHUANLILX, PCTBJ, SHENQINFSBJ, DAIYIBJ, SHENQINGR, FENANTJR, GUOBIE, SHENQINGRSL, FAMINGRENSL, LIANXIRBJ, WAIGUANCPLB, FENLEIHBBH, ZHUFENLH, DAILIBJ, FENANBJ, WEISHENGWBCBJ, XULIEBBJ, TIQIANGKBJ, SHISHENQQBJ, YOUXIANQQTS, ZUIXIAOYXQR, BUSANGSXYXKXQSMBJ, BAOMIQQBJ, QIANZHANGHGBJ, TUPIANHGBJ, FEIYONGJHBJ, PCTFJBJ, MUANBJ, ZAIXIANSQBJ, QUANLIYQXS, BAOMITXBJ, GUAQIBJ, ZANTINGBJ, ZHONGZHIBJ, SUODINGBJ, JIAKUAIBJ, SHISHENQQHGR, SHISHENSXR, TIQIANGKR, FAMINGGBR, GONGKAIGGR, ZHUANLIH, ANJIANYWZT, CHONGFUSQHBZ, CHONGFUSQQBZ, YOUXIAOBJ, REGNAME, REGTIME, MODNAME, MODTIME, SHOULIZKR, TONGYDFMCZBJ, XIANGSSJBJ, CHENGTCPBJ, XIANGSSJXS, CHENGTCPXS, YICZYBJ, XIANGWSQBJ, XIANGWSQSPBJ, FEIZHENGCSQYSBJ, YXSCFS, ZJZDZSQSXR, SHENQINGRENFQZDXGQL, CAFBJ, YINANAJHSBJ, DIANZISQLX, DLJGNBBH, SHENGMINGWTSYZBJ, ZHAIYFTH, ZHAIYAOFTZD, DZZZJSQSXR, QIANZHANGNR, '发驳回后工作流仍在新型'||sysdate from GG_ZLX_ZHU t where t.shenqingh = '");
                        sqlBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                        sqlBui.append("update gg_zlx_zhu t set t.anjianywzt = '1891S01' where t.shenqingh = '").append(shenqingh).append("';");
                    }
                    boolean firstRecord = true;
                    String targetEntryId = "";
                    Map<String, String> dataMap = new HashMap<>();
                    for (RtnData otherRtnData : otherRtnDataList) {
                        if (firstRecord) {
                            String zhongzhibz = otherRtnData.getZhongzhibj();
                            String qixianslh = otherRtnData.getQixianslh();
                            if (StringUtils.isEmpty(zhongzhibz)) {
                                //写入数据
                                String fawenr = otherRtnData.getFawenr();
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                String xh = otherRtnData.getXh();
                                String shenchaydm = otherRtnData.getShenchaydm();
                                sqlBui.append("insert into zpt_qxjs_qxsl (QIXIANSLH, QIXIANDM, YEWUZTBH, YEWUZTLX, ZIXITBH, QIXIANMC, QIXIANCD, QISHIRLX, QIXIANDW, CANSHUDY, YUQICLL, ZHONGZHICLL, QIXIANYCQBZ, GUOQIBZ, CANSHUZ, SHEJIWJBZ, SHEJIFYBZ, QISHIRQ, YUQIRQ, ZHONGZHIRQ, YANCHANGCS, YUQBZ, ZHONGZHIBZ, YUQICLBZ, ZHONGZHICLBZ, QIXIANJLJLDM, QIXIANJLJLMC, JIELUNYJBH, JIELUNYJLX, CHUANGJIANRQ, CHUANGJIANR, QIXIANZT, CAOZUOR, CAOZUOSJ, REGNAME, REGTIME, MODNAME, MODTIME, YIZHIXH, YIZHIBZ, YSYQBJ)values (SE_ZPT_QXJS_QXSL_XX.nextval, '18T0101', '" + shenqingh + "', '1', '09', '（驳回申请）等复审请求', 3, '6', '2', null, null, 'cn.gwssi.cpees.n_fswx.lalc.qxgl.terminate.TerminateWaitFSRequest', '1', '1', null, '1', '0', '" + fawenr + "', to_char(add_months(to_date('" + fawenr + "','yyyyMMdd'),3)+15,'yyyyMMdd'), to_char(add_months(to_date('" + fawenr + "','yyyyMMdd'),4)+15,'yyyyMMdd'), 0, '1', '0', null, null, '18C0101', '（驳回申请）等复审请求', " + xh + ", '1', to_char(to_date('" + fawenr + "','yyyyMMdd')-3,'yyyyMMdd'), '" + shenchaydm + "', '1', null, null, 'zptQxjsJdbcJinsTM', to_char(sysdate,'yyyyMMddHH24mmss'), '补数据_xx', null, null, null, null);");
                            } else if (!"0".equals(zhongzhibz)) {
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                //备份
                                sqlBui.append("insert into backup_data_user.ZPT_QXJS_QXSL(QIXIANSLH,QIXIANDM,YEWUZTBH,YEWUZTLX,ZIXITBH,QIXIANMC,QIXIANCD,QISHIRLX,QIXIANDW,CANSHUDY,YUQICLL,ZHONGZHICLL,QIXIANYCQBZ,GUOQIBZ,CANSHUZ,SHEJIWJBZ,SHEJIFYBZ,QISHIRQ,YUQIRQ,ZHONGZHIRQ,YANCHANGCS,YUQBZ,ZHONGZHIBZ,YUQICLBZ,ZHONGZHICLBZ,QIXIANJLJLDM,QIXIANJLJLMC,JIELUNYJBH,JIELUNYJLX,CHUANGJIANRQ,CHUANGJIANR,QIXIANZT,CAOZUOR,CAOZUOSJ,REGNAME,REGTIME,MODNAME,MODTIME,YIZHIXH,YIZHIBZ,YSYQBJ,BUG_ID) select QIXIANSLH,QIXIANDM,YEWUZTBH,YEWUZTLX,ZIXITBH,QIXIANMC,QIXIANCD,QISHIRLX,QIXIANDW,CANSHUDY,YUQICLL,ZHONGZHICLL,QIXIANYCQBZ,GUOQIBZ,CANSHUZ,SHEJIWJBZ,SHEJIFYBZ,QISHIRQ,YUQIRQ,ZHONGZHIRQ,YANCHANGCS,YUQBZ,ZHONGZHIBZ,YUQICLBZ,ZHONGZHICLBZ,QIXIANJLJLDM,QIXIANJLJLMC,JIELUNYJBH,JIELUNYJLX,CHUANGJIANRQ,CHUANGJIANR,QIXIANZT,CAOZUOR,CAOZUOSJ,REGNAME,REGTIME,MODNAME,MODTIME,YIZHIXH,YIZHIBZ,YSYQBJ,'发驳回后工作流仍在新型'||sysdate BUG_ID from ZPT_QXJS_QXSL t where t.qixianslh = " + qixianslh + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //将字段修改为0
                                sqlBui.append("update zpt_qxjs_qxsl t set t.zhongzhibz = '0' where t.qixianslh = " + qixianslh + ";");
                            }
                            String anjiansccxd = otherRtnData.getAnjiansccxd();
                            if (!"18".equals(anjiansccxd)) {
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                //备份
                                sqlBui.append("insert into backup_data_user.ZPT_ANJIANKZ(ZHUANLISQH,ZHUANLILX,ZHULIUCSLH,ANJIANSCCXD,CHENGXUDLCSLH,PCTSQBJ,GUONEIFMBJ,TUXINGWDBJ,TUXINGKZBJ,DAIMAWDBJ,DAIMAKZBJ,FENLEIWCBJ,SHISHENQQBJ,GONGBUWCBJ,WUXIAOXGBJ,SHENCHAYYDM,FUSHENYYDM,SHIXIAOYYDM,LIUCHENGKZDM,MODNAME,MODTIME,REGNAME,REGTIME,BUG_ID) select ZHUANLISQH,ZHUANLILX,ZHULIUCSLH,ANJIANSCCXD,CHENGXUDLCSLH,PCTSQBJ,GUONEIFMBJ,TUXINGWDBJ,TUXINGKZBJ,DAIMAWDBJ,DAIMAKZBJ,FENLEIWCBJ,SHISHENQQBJ,GONGBUWCBJ,WUXIAOXGBJ,SHENCHAYYDM,FUSHENYYDM,SHIXIAOYYDM,LIUCHENGKZDM,MODNAME,MODTIME,REGNAME,REGTIME,'发驳回后工作流仍在新型'||sysdate BUG_ID from ZPT_ANJIANKZ t where t.zhuanlisqh='" + shenqingh + "';");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //将字段修改为18
                                sqlBui.append("update zpt_anjiankz t set t.anjiansccxd = '18' where t.zhuanlisqh ='" + shenqingh + "';");
                            }
                            firstRecord = false;
                        }
                        String rid = otherRtnData.getRid();
                        if (StringUtils.isNotBlank(rid)) {
                            targetEntryId = rid;
                            String liuchengzt = otherRtnData.getLiuchengzt();
                            String status = otherRtnData.getStatus();
                            if (!"1891".equals(liuchengzt) || !"1891S01".equals(status)) {
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                //备份
                                sqlBui.append("insert into backup_data_user.WF_CURRENTSTEP(ID,ENTRY_ID,STEP_ID,ACTION_ID,OWNER,START_DATE,FINISH_DATE,DUE_DATE,STATUS,CALLER,PREVIOUS_ID,BUG_ID) select ID,ENTRY_ID,STEP_ID,ACTION_ID,OWNER,START_DATE,FINISH_DATE,DUE_DATE,STATUS,CALLER,PREVIOUS_ID,'发驳回后工作流仍在新型'||sysdate BUG_ID from WF_CURRENTSTEP t where t.id = " + rid + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //修改
                                sqlBui.append("update wf_currentstep t set t.step_id = '1891',t.status = '1891S01' where t.id = " + rid + ";");
                            }
                        }
                        String name = otherRtnData.getName();
                        String state = otherRtnData.getState();
                        String entryId = otherRtnData.getEntryId();
                        dataMap.put(name + "_" + state, entryId);
                    }
                    if (dataMap.keySet().contains("18_1")) {//不需要处理
                    } else {
                        if (StringUtils.isBlank(targetEntryId)) {//说明WF_CURRENTSTEP表不存在数据
                            String key = dataMap.get("18_4");
                            if (StringUtils.isBlank(key)) {
                                key = dataMap.get("11_4");
                            }
                            if (sqlBui.length() > 0) {
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                            }
                            if (StringUtils.isBlank(key)) {
                                sqlBui.append("wf_currentstep shenqingh = " + shenqingh + "没有数据;");
                            } else {//向wf_currentstep写入一条数据
                                //2015208499107,2015207874196情况特殊，wf_currentstep表存储的entry_id是wf_wfentry表name = '00'数据,需要删除后再执行插入
                                sqlBui.append("insert into backup_data_user.WF_CURRENTSTEP (ID, ENTRY_ID, STEP_ID, ACTION_ID, OWNER, START_DATE, FINISH_DATE, DUE_DATE, STATUS, CALLER, PREVIOUS_ID, BUG_ID) select ID, ENTRY_ID, STEP_ID, ACTION_ID, OWNER, START_DATE, FINISH_DATE, DUE_DATE, STATUS, CALLER, PREVIOUS_ID, '发驳回后工作流仍在新型' || sysdate BUG_ID from WF_CURRENTSTEP t where t.entry_id in (select tt.id from wf_wfentry tt where tt.main_id ='" + shenqingh + "');");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                sqlBui.append("delete from wf_currentstep s where s.entry_id in (select t.id from wf_wfentry t where t.main_id = '" + shenqingh + "');");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);

                                sqlBui.append("insert into wf_currentstep (ID, ENTRY_ID, STEP_ID, ACTION_ID, OWNER, START_DATE, FINISH_DATE, DUE_DATE, STATUS, CALLER, PREVIOUS_ID)values (SEQ_WF_CURRENTSTEPS.nextval, " + key + ", '1891', null, '自动', '14-11月-20 10.37.15.664000 上午', null, null, '1891S01', null, null);");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //备份
                                sqlBui.append("insert into backup_data_user.WF_WFENTRY(ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,BUG_ID) select ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,'发驳回后工作流仍在新型'||sysdate BUG_ID from WF_WFENTRY t where t.id =" + key + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //修改
                                sqlBui.append("update wf_wfentry t set t.name = '18',t.state = '1',t.sub_workflow_id = '00SF18' where t.id = " + key + ";");
                            }
                            sqlBui.append(FileHelperUtil.LINE_SEPARATOR);

                        } else {
                            if (dataMap.keySet().contains("11_1")) {//目前存活的是11段
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                String key = dataMap.get("11_1");
                                //备份
                                sqlBui.append("insert into backup_data_user.WF_WFENTRY(ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,BUG_ID) select ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,'发驳回后工作流仍在新型'||sysdate BUG_ID from WF_WFENTRY t where t.id =" + key + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //修改
                                sqlBui.append("update wf_wfentry t set t.name = '18',t.sub_workflow_id = '00SF18' where t.id = " + key + ";");
                            } else {//11段和18段都没有存活,用wf_currentstep表中entry_id记录修改到18段
                                if (sqlBui.length() > 0) {
                                    sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                }
                                //备份
                                sqlBui.append("insert into backup_data_user.WF_WFENTRY(ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,BUG_ID) select ID,NAME,STATE,PARENT_ID,SUB_WORKFLOW_ID,INSTANCE_ID,MAIN_ID,'发驳回后工作流仍在新型'||sysdate BUG_ID from WF_WFENTRY t where t.id =" + targetEntryId + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                                //修改
                                sqlBui.append("update wf_wfentry t set t.name = '18',t.state = '1',t.sub_workflow_id = '00SF18' where t.id = " + targetEntryId + ";");
                                sqlBui.append(FileHelperUtil.LINE_SEPARATOR);
                            }
                        }
                    }
                    if (strBui.length() > 0) {
                        strBui.append(FileHelperUtil.LINE_SEPARATOR);
                    }
                    strBui.append(sqlBui.toString());
                }
            });
            if (strBui.length() > 0) {
                strBui.append(FileHelperUtil.LINE_SEPARATOR);
                mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.NOTICE_REJECT_WORKFLOW_XX_PHASE), strBui.toString());
                String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
                String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "noticeRejectWorkflowXxPhase";
                FileHelperUtil.appendContentToFile(targetPath + File.separator + "noticeRejectWorkflowXxPhase.txt", strBui.toString());
                //log.info("正常结束**************************");
            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    @Async
    @Scheduled(cron = "0 12 4 * * ?")// 每天上午4:12触发
    //@Scheduled(cron = "0 30 12 * * ?")// 每天上午12:30触发
    public void linkmanInfoAllIsnull() {//联系人信息为空
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.LINKMAN_INFO_ALL_ISNULL, "linkmanInfoAllIsnull", mailHelperBuilder);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getRid());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.LINKMAN_INFO_ALL_ISNULL, sqhBui.toString(), "rid");
        } else {
        }
    }

    @Async
   @Scheduled(cron = "0 34 5 * * ?")// 每天上午5:34触发
    //@Scheduled(cron = "0 02 15 * * ?")// 每天上午1:05触发
    public void versionUnmatchClassification() {//版本号与分类号长度不符
        //UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.VERSION_UNMATCH_CLASSIFICATION, "versionUnmatchClassification", mailHelperBuilder);
        try (CloseableHttpClient httpClient = UtilityServiceInvoke.loginUtilityApplication(pathConfig)) {
            StringBuffer mailBui = new StringBuffer();
            List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.VERSION_UNMATCH_CLASSIFICATION, "versionUnmatchClassification", httpClient);
            rtnDataList.parallelStream().forEach(rtnData -> {
                StringBuffer strBui = new StringBuffer();
                String shenqingh = rtnData.getShenqingh();
                String fenleihbbh = rtnData.getState();
                String fujiaxxflh = rtnData.getText();
                if (StringUtils.isNotEmpty(fujiaxxflh)) {
                    //备份
                    mailBui.append("insert into backup_data_user.gg_zlx_flh (UUID, SHENQINGH, FENLEIHBBH, FENLEIH, FJFENLH, FUJIAXXFLH, FENLHCSRQ, YOUXIAOBJ, SHENCHAYDM, BUMENDM, REGNAME, REGTIME, MODNAME, MODTIME, FUFENLH1, FUFENLH2, FUFENLH3, FENLEIBZ, LAIYUANZXTDM, bug_id) select UUID, SHENQINGH, FENLEIHBBH, FENLEIH, FJFENLH, FUJIAXXFLH, FENLHCSRQ, YOUXIAOBJ, SHENCHAYDM, BUMENDM, REGNAME, REGTIME, MODNAME, MODTIME, FUFENLH1, FUFENLH2, FUFENLH3, FENLEIBZ, LAIYUANZXTDM, '版本号不符'||sysdate BUG_ID from gg_zlx_flh t where t.shenqingh ='");
                    mailBui.append(shenqingh).append("';").append(FileHelperUtil.LINE_SEPARATOR);
                    //修改
                    mailBui.append("update gg_zlx_flh t set t.fenleihbbh = '" + fenleihbbh + " 'where t.shenqingh ='" + shenqingh + "';");
                    mailBui.append(FileHelperUtil.LINE_SEPARATOR);
                } else {
                    strBui.append(shenqingh + "," + fenleihbbh);
                    //UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.VERSION_UNMATCH_CLASSIFICATION, strBui.toString(), "shenqingh,fenleihbbh");
                    String jsonObj = "{is_exec:\"true\",shenqingh:\"" + shenqingh + "\",fenleihbbh:\"`" + fenleihbbh + "`\"}";
                    UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.VERSION_UNMATCH_CLASSIFICATION, jsonObj, "jsonObj");
                }

            });
            if (mailBui.length() > 0) {
                mailHelperBuilder.sendSimpleMessage(BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.VERSION_UNMATCH_CLASSIFICATION), mailBui.toString());
           /*     mailHelperBuilder.sendSimpleMessage(desc, "版本号与分类号长度不符");
            String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "versionUnmatchClassification";
            FileHelperUtil.appendContentToFile(targetPath + File.separator + "versionUnmatchClassification.txt", sqhBui.toString(), desc);*/

            }
        } catch (IOException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

    @Async
    @Scheduled(cron = "0 38 3 * * ?")// 每天上午3:38触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void noticeWithdrawMsbStateUnconsistent() {//通知书撤件后描述表状态不一致
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.NOTICE_WITHDRAW_MSB_STATE_UNCONSISTENT, "noticeWithdrawMsbStateUnconsistent", mailHelperBuilder);
    }

    @Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    @Scheduled(cron = "3 53 0 * * ?")// 每天上午3:53触发
    public void noticeDeleteTermExists() {//通知书删除等答复期限未删除
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.NOTICE_DELETE_TERM_EXISTS, "noticeDeleteTermExists");
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append("Qixianslh=" + rtnData.getQixianslh() + ";shenqingh=" + rtnData.getShenqingh() + ";tzsrid=" + rtnData.getRid() + ";starttime=" + rtnData.getStartTime() + "\n");
            }
            String desc = BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.NOTICE_DELETE_TERM_EXISTS);
            mailHelperBuilder.sendSimpleMessage(desc, sqhBui.toString());

            String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "noticeDeleteTermExists";
            FileHelperUtil.appendContentToFile(targetPath + File.separator + "noticeDeleteTermExists.txt", sqhBui.toString(), desc);

        } else {
        }
    }

    //@Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    //@Scheduled(cron = "0 53 0 * * ?")// 每天上午0:53触发
    //@Scheduled(initialDelay=5000L, fixedDelay = 5 * 60000)
    public void examinerWorkloadCountMonitor() {//审查员工作量分配表数据量监控
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.EXAMINER_WORKLOAD_COUNT_MONITOR, "examinerWorkloadCountMonitor");
        String expectValue = BusinessConstant.SYSTEM_VARIABLE_MAP.get(BusinessConstant.EXAMINER_WORKLOAD_COUNT_MONITOR);
        if (StringUtils.isBlank(expectValue)) {
            expectValue = BusinessConstant.SYSTEM_VARIABLE_MAP.put(BusinessConstant.EXAMINER_WORKLOAD_COUNT_MONITOR, "8970");
            expectValue = "8970";
        }
        log.info("expectValue=" + expectValue);
        if (rtnDataList != null && rtnDataList.size() > 0) {
            String cnt = "0";
            for (RtnData rtnData : rtnDataList) {
                cnt = rtnData.getCnt();
            }
            if (!cnt.equals(expectValue)) {
                String desc = BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.EXAMINER_WORKLOAD_COUNT_MONITOR);
                mailHelperBuilder.sendSimpleMessagesWaring(desc, "审查员工作量分配表数据量出现变动,请马上处理!");
            }
        } else {
        }
    }

    @Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    //@Scheduled(cron = "0 53 0 * * ?")// 每天上午0:53触发
    @Scheduled(fixedDelay = 5 * 60000)
    public void noticeSoftscanFinishStateDraft() {//通知书发出软扫结束通知书状态仍为草稿
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.NOTICE_SOFTSCAN_FINISH_STATE_DRAFT, "noticeSoftscanFinishStateDraft");
        if (rtnDataList != null && rtnDataList.size() > 0) {
            StringBuilder sqhBui = new StringBuilder();
            boolean firstFlag = true;
            for (RtnData rtnData : rtnDataList) {
                if (firstFlag) {
                    firstFlag = false;
                } else {
                    sqhBui.append(",");
                }
                sqhBui.append(rtnData.getRid());
            }
            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.NOTICE_SOFTSCAN_FINISH_STATE_DRAFT, sqhBui.toString(), "rid");
            //String desc = BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.NOTICE_SOFTSCAN_FINISH_STATE_DRAFT);
            //mailHelperBuilder.sendSimpleMessage(desc,"修改通知书状态到05的通知书描述表主键："+sqhBui.toString());
        } else {
        }
    }

    @Async
    @Scheduled(cron = "0 55 0 * * ?")// 每天上午0:55触发
    //@Scheduled(cron = "0 48 11 * * ?")// 每天上午1:05触发
    public void comparareNoticeYesterdayCount() {//管理查询库和电子审批库昨天发出通知书数据量比对
        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_COMPARARE_NOTICE_YESTERDAY_COUNT + "_1", "comparareNoticeYesterdayCount");
        String dzsqkCnt = rtnDataList.get(0).getCnt();
        rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_COMPARARE_NOTICE_YESTERDAY_COUNT + "_2", "comparareNoticeYesterdayCount");
        String glcxkCnt = rtnDataList.get(0).getCnt();
        if (!dzsqkCnt.equals(glcxkCnt)) {
            String desc = BusinessConstant.MONITOR_BIZ_DESC_MAP.get(BusinessConstant.BIZ_COMPARARE_NOTICE_YESTERDAY_COUNT);
            StringBuilder resultBui = new StringBuilder();
            resultBui.append("昨日新型通知书发出数量：管理查询库=" + glcxkCnt + ";电子审批库=" + dzsqkCnt);
            mailHelperBuilder.sendSimpleMessage(desc, resultBui.toString());
            String currDate = DateFormatUtils.format(new Date(), "yyyyMMdd");
            String targetPath = pathConfig.getShareDisk() + File.separator + currDate + File.separator + "comparareNoticeYesterdayCount";
            FileHelperUtil.appendContentToFile(targetPath + File.separator + "comparareNoticeYesterdayCount.txt", resultBui.toString(), "管理查询库和电子审批库昨天发出通知书数据量比对");

        }
    }


//    @Async
//    @Scheduled(cron = "0 15 06 * * ?")// 每天上午6:15触发
//    //@Scheduled(cron = "0 25 14 * * ?")// 每天上午1:05触发
//    public void unhangupCaseAuthInvalidPriority() {//在后授权在先优先权不成立未解挂
//        List<RtnData> rtnDataList = UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.UNHANGUP_CASE_AUTH_INVALID_PRIORITY, "unhangupCaseAuthInvalidPriority");
//        if (rtnDataList != null && rtnDataList.size() > 0) {
//            StringBuilder sqhBui = new StringBuilder();
//            boolean firstFlag = true;
//            for (RtnData rtnData : rtnDataList) {
//                if (firstFlag) {
//                    firstFlag = false;
//                } else {
//                    sqhBui.append(",");
//                }
//                sqhBui.append(rtnData.getZaixiansqh());
//            }
//            UtilityServiceInvoke.commonBizHandleProcess(pathConfig, BusinessConstant.UNHANGUP_CASE_AUTH_INVALID_PRIORITY, sqhBui.toString(), "shenqingh");
//            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次共处理"+sqhBui.toString()+",请核查是否正常!!!");
//        } else {
//            //mailHelperBuilder.sendSimpleMessage("发明案源gl_yxsc_ajscb表数据重复处理","本次未发现需要处理的数据!!!");
//        }
//    }


    //@Async
    //@Scheduled(cron = "0 15 0 * * ?")// 每天上午0:15触发
    //@Scheduled(cron = "0 31 16 * * ?")// 每天上午1:05触发
    public void exceptionNoticeOfWithdrawal() {//异常视撤通知书
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_EXCEPTION_NOTICE_OF_WITHDRAWAL, "exceptionNoticeOfWithdrawal", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbztdtxa() {//新型审查表案件状态为待回，但主表案件状态为“等待提案”或者“新案审查”。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAZTDT, "ajdhZbajztyc1", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbhawdf() {//新型审查表案件状态为待回，但主表案件状态为“回案审查”（无答复文件），需修改主表状态为初审待答复。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAHASC_WDF, "ajdhZbajztyc2", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_zbhaydf() {//新型审查表案件状态为待回，但主表案件状态为“回案审查”（有答复文件），需修改主表状态为初审待答复。
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZAHASC_YDF, "ajdhZbajztyc3", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztdt() {//案件审查表的案件状态为“回案审查”，主表状态为“等待提案”或者“新案审查”；
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_ZBZTBYZ, "scbhaZbztyc", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbdh_ftasfbzsy() {//新型审查表案件状态为待回，发出通知书非补正和审意，主表案件状态为其他情况
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_AJDH_TZSCW, "ajdhZbajztyc1", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztcsddfhxasc() {//案件审查表的案件状态为“回案审查”，主表状态为“初审待答复”或者“新案审查”；
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_HASC_ZBZTCSDFXSC, "scbhaZbztyc2", mailHelperBuilder);
    }

    //@Async
    //@Scheduled(cron = "0 20 2 * * ?")// 每天上午2:20触发
    //@Scheduled(cron = "0 45 12 * * ?")// 每天上午1:05触发
    public void scbha_zbztwqt() {//案件审查表的案件状态为“回案审查”，主表状态为其他情况
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_HASC_ZBZTQT, "scbhaZbztyc", mailHelperBuilder);
    }


    //@Async
    //@Scheduled(cron = "0 0/ * * * ?")// 15分钟触发一次
    public void bizNoticeSoftscanFailMonitor() {//通知书软扫失败或软扫回调失败
        UtilityServiceInvoke.commonBizMonitorProcess(pathConfig, BusinessConstant.BIZ_NOTICE_SOFTSCAN_FAIL, "noticeSoftscanFail", mailHelperBuilder);
    }
}

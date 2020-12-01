package com.gwssi.devops.utilitypage.controller;

import cn.gwssi.util.TreeNode;
import com.gwssi.devops.utilitypage.schedule.PPHMultithreadScheduleTask;
import com.gwssi.devops.utilitypage.schedule.UtilityMultithreadScheduleTask;
import com.gwssi.devops.utilitypage.util.BusinessConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("utility/")
public class Utilitycontroller {

    @Autowired
    private PPHMultithreadScheduleTask pphTask;
    @Autowired
    private UtilityMultithreadScheduleTask utilityTask;

    @ResponseBody
    @RequestMapping(value = "menuTreeData", method = RequestMethod.GET)
    public List<TreeNode> menuTreeData(@RequestParam("parentId") String parentId) {

        //System.out.println("parentId="+parentId);
        List<TreeNode> rtnList = new ArrayList<>();

        TreeNode topOne = new TreeNode();
        topOne.setId("-1");
        topOne.setParentId("");
        topOne.setName("新型审查");
        List<TreeNode> topOneChildren = new ArrayList<>();

        TreeNode levelOne1 = new TreeNode();
        List<TreeNode> levelOne1Children = new ArrayList<>();
        levelOne1.setId("01");
        levelOne1.setParentId("-1");
        levelOne1.setName("程序处理");


        TreeNode levelOne11 = new TreeNode<String>();
        levelOne11.setId("0101");
        levelOne11.setParentId("01");
        levelOne11.setName("图片cmyk转rgb");
        levelOne11.setData("cmyk-to-rgb");
        levelOne1Children.add(levelOne11);

        levelOne1.setChildren(levelOne1Children);

        topOneChildren.add(levelOne1);

        TreeNode levelOne2 = new TreeNode();
        List<TreeNode> levelOne2Children = new ArrayList<>();
        levelOne2.setId("02");
        levelOne2.setParentId("-1");
        levelOne2.setName("数据监控");

        TreeNode levelOne21 = new TreeNode();
        levelOne21.setId("0201");
        levelOne21.setParentId("02");
        levelOne21.setName("案件状态异常");
        levelOne21.setData("utility-common");
        levelOne2Children.add(levelOne21);

        TreeNode levelOne22 = new TreeNode();
        levelOne22.setId("0202");
        levelOne22.setParentId("02");
        levelOne22.setName("授权案件五书缺失");
        levelOne22.setData("auth-case-fivebook-miss");
        levelOne2Children.add(levelOne22);

        TreeNode levelOne23 = new TreeNode();
        levelOne23.setId("0203");
        levelOne23.setParentId("02");
        levelOne23.setName("中间文件分配过晚导致回案");
        levelOne23.setData("utility-common");
        levelOne2Children.add(levelOne23);

        TreeNode levelOne24 = new TreeNode();
        levelOne24.setId("0204");
        levelOne24.setParentId("02");
        levelOne24.setName("通知书软扫或回调失败");
        levelOne24.setData("notice_softscan_fail");
        levelOne2Children.add(levelOne24);

        TreeNode levelOne25 = new TreeNode();
        levelOne25.setId("0205");
        levelOne25.setParentId("02");
        levelOne25.setName("授权通知书发出事件记录异常");
        levelOne25.setData("utility-common");
        levelOne2Children.add(levelOne25);

        TreeNode levelOne26 = new TreeNode();
        levelOne26.setId("0206");
        levelOne26.setParentId("02");
        levelOne26.setName("分案视未通知书发出事件记录异常");
        levelOne26.setData("utility-common");
        levelOne2Children.add(levelOne26);

        TreeNode levelOne27 = new TreeNode();
        levelOne27.setId("0207");
        levelOne27.setParentId("02");
        levelOne27.setName("已结案件缺失结案日期");
        levelOne27.setData("utility-common");
        levelOne2Children.add(levelOne27);

        List<TreeNode> levelOne2_1Children = new ArrayList<>();
        TreeNode levelOne28 = new TreeNode();
        levelOne28.setId("0208");
        levelOne28.setParentId("02");
        levelOne28.setName("新型审查表案件状态为待回，但主表状态非初审待答复");
        levelOne28.setData("aj-dhzt-yqqk1");
        levelOne2Children.add(levelOne28);

//        TreeNode levelOne28_1 = new TreeNode();
//        levelOne28_1.setId("020801");
//        levelOne28_1.setParentId("0208");
//        levelOne28_1.setName("表案件状态为等待提案或者新案审查");
//        levelOne28_1.setData("aj-dhzt-yqqk1");
//        levelOne2_1Children.add(levelOne28_1);
        //levelOne28.setChildren(levelOne2_1Children);

//        TreeNode levelOne28_2 = new TreeNode();
//        levelOne28_2.setId("020802");
//        levelOne28_2.setParentId("0208");
//        levelOne28_2.setName("主表案件状态为“回案审查”（无答复文件）,需修改主表状态为初审待答复");
//        levelOne28_2.setData("aj-dhzt-yqqk2");
        //       levelOne2_1Children.add(levelOne28_2);
        // levelOne28.setChildren(levelOne2_1Children);

//        TreeNode levelOne28_3 = new TreeNode();
//        levelOne28_3.setId("020803");
//        levelOne28_3.setParentId("0208");
//        levelOne28_3.setName("主表案件状态为“回案审查”（有答复文件）,需修改新型审查表案件状态为未处理回案");
//        levelOne28_3.setData("aj-dhzt-yqqk3");
//        levelOne2_1Children.add(levelOne28_3);
        //  levelOne28.setChildren(levelOne2_1Children);

        TreeNode levelOne28_4 = new TreeNode();
        levelOne28_4.setId("020804");
        levelOne28_4.setParentId("0208");
        levelOne28_4.setName("发出通知书非补正和审意,主表案件状态为其他情况");
        levelOne28_4.setData("aj-dhzt-yqqk4");
        levelOne2_1Children.add(levelOne28_4);
        levelOne28.setChildren(levelOne2_1Children);


        List<TreeNode> levelOne2_2Children = new ArrayList<>();
        TreeNode levelOne29 = new TreeNode();
        levelOne29.setId("0209");
        levelOne29.setParentId("02");
        levelOne29.setName("新型审查表案件状态为回案，主表状态与新型状态不一致");
        levelOne29.setData("utility-common");
        levelOne2Children.add(levelOne29);

//        TreeNode levelOne29_1 = new TreeNode();
//        levelOne29_1.setId("020901");
//        levelOne29_1.setParentId("0209");
//        levelOne29_1.setName("主表状态为“等待提案”或者“新案审查”");
//        levelOne29_1.setData("aj-dhzt-yqqk1");
//        levelOne2_2Children.add(levelOne29_1);
//        levelOne29.setChildren(levelOne2_2Children);
//
//        TreeNode levelOne29_2 = new TreeNode();
//        levelOne29_2.setId("020902");
//        levelOne29_2.setParentId("0209");
//        levelOne29_2.setName("主表状态为“初审待答复”或者“新案审查”");
//        levelOne29_2.setData("aj-dhzt-yqqk2");
//        levelOne2_2Children.add(levelOne29_2);
//        levelOne29.setChildren(levelOne2_2Children);

        TreeNode levelOne29_3 = new TreeNode();
        levelOne29_3.setId("020903");
        levelOne29_3.setParentId("0209");
        levelOne29_3.setName("主表状态为其他情况");
        levelOne29_3.setData("aj-dhzt-yqqk5");
        levelOne2_2Children.add(levelOne29_3);
        levelOne29.setChildren(levelOne2_2Children);

        levelOne2.setChildren(levelOne2Children);

        topOneChildren.add(levelOne2);

        TreeNode levelOne3 = new TreeNode();
        List<TreeNode> levelOne3Children = new ArrayList<>();
        levelOne3.setId("03");
        levelOne3.setParentId("-1");
        levelOne3.setName("服务器相关");

        TreeNode levelOne31 = new TreeNode();
        levelOne31.setId("0301");
        levelOne31.setParentId("03");
        levelOne31.setName("weblogic健康状态");
        levelOne31.setData("weblogic-health-state");
        levelOne3Children.add(levelOne31);

        TreeNode levelOne32 = new TreeNode();
        levelOne32.setId("0302");
        levelOne32.setParentId("03");
        levelOne32.setName("共享存储检测");
        levelOne32.setData("check-share-disk-state");
        levelOne3Children.add(levelOne32);

        TreeNode levelOne33 = new TreeNode();
        levelOne33.setId("0303");
        levelOne33.setParentId("03");
        levelOne33.setName("软扫日志搜索");
        levelOne33.setData("softscan-log-search");
        levelOne3Children.add(levelOne33);

        levelOne3.setChildren(levelOne3Children);

        topOneChildren.add(levelOne3);

        TreeNode levelOne4 = new TreeNode();
        List<TreeNode> levelOne4Children = new ArrayList<>();
        levelOne4.setId("04");
        levelOne4.setParentId("");
        levelOne4.setName("快捷操作");

        TreeNode levelOne41 = new TreeNode();
        levelOne41.setId("0401");
        levelOne41.setParentId("04");
        levelOne41.setName("文件操作");
        levelOne41.setData("utility-file-operate");
        levelOne4Children.add(levelOne41);

        TreeNode levelOne42 = new TreeNode();
        levelOne42.setId("0402");
        levelOne42.setParentId("04");
        levelOne42.setName("redis操作");
        levelOne42.setData("utility-redis-operate");
        levelOne4Children.add(levelOne42);

        levelOne4.setChildren(levelOne4Children);

        topOneChildren.add(levelOne4);

        topOne.setChildren(topOneChildren);


        TreeNode topTwo = new TreeNode();
        topTwo.setId("-2");
        topTwo.setParentId("");
        topTwo.setName("案源配送");
        List<TreeNode> topTwoChildren = new ArrayList<>();

        TreeNode levelOne5 = new TreeNode();
        List<TreeNode> levelOne5Children = new ArrayList<>();
        levelOne5.setId("05");
        levelOne5.setParentId("");
        levelOne5.setName("数据处理");

        TreeNode levelOne51 = new TreeNode();
        levelOne51.setId("0501");
        levelOne51.setParentId("05");
        levelOne51.setName("优先审查数据重复处理");
        levelOne51.setData("priority_audit_data_repeat");
        levelOne5Children.add(levelOne51);


        TreeNode levelOne52 = new TreeNode();
        levelOne52.setId("0502");
        levelOne52.setParentId("05");
        levelOne52.setName("案源用户管理");
        levelOne52.setData("ay-yonghu-state");
        levelOne5Children.add(levelOne52);

        TreeNode levelOne53 = new TreeNode();
        levelOne53.setId("0503");
        levelOne53.setParentId("05");
        levelOne53.setName("案件审序查询");
        levelOne53.setData("ay-ajshenxu-cx");
        levelOne5Children.add(levelOne53);

        TreeNode levelOne54 = new TreeNode();
        levelOne54.setId("0504");
        levelOne54.setParentId("05");
        levelOne54.setName("用户提案量上限修改");
        levelOne54.setData("ay-yonghu-talsx");
        levelOne5Children.add(levelOne54);

        levelOne5.setChildren(levelOne5Children);

        topTwoChildren.add(levelOne5);

        topTwo.setChildren(topTwoChildren);

        TreeNode topThree = new TreeNode();
        topThree.setId("-3");
        topThree.setParentId("");
        topThree.setName("在线业务指导");
        List<TreeNode> topThreeChildren = new ArrayList<>();

        TreeNode levelOne6 = new TreeNode();
        List<TreeNode> levelOne6Children = new ArrayList<>();
        levelOne6.setId("06");
        levelOne6.setParentId("-3");
        levelOne6.setName("服务监控");

        TreeNode levelOne61 = new TreeNode();
        levelOne61.setId("0601");
        levelOne61.setParentId("06");
        levelOne61.setName("通知书下载测试");
        levelOne61.setData("instruction_notice_download_test");
        levelOne6Children.add(levelOne61);

        levelOne6.setChildren(levelOne6Children);

        topThreeChildren.add(levelOne6);

        topThree.setChildren(topThreeChildren);

        rtnList.add(topOne);
        rtnList.add(topTwo);
        rtnList.add(topThree);
        return rtnList;
    }

    @ResponseBody
    @RequestMapping(value = "taskDesc", method = RequestMethod.GET)
    public String taskDesc() {
        String rtnStr = BusinessConstant.MONITOR_BIZ_DESC_MAP.toString().replaceAll(", ", "<br/>");
        return rtnStr.substring(1, rtnStr.length() - 1);
    }

    @ResponseBody
    @RequestMapping(value = "variable/{paramName}/{paramValue}", method = RequestMethod.GET)
    public void variableModify(@PathVariable("paramName") String paramName, @PathVariable("paramValue") String paramValue) {
        BusinessConstant.SYSTEM_VARIABLE_MAP.put(paramName, paramValue);
    }

    @ResponseBody
    @RequestMapping(value = "taskExecute/{taskId}", method = RequestMethod.GET)
    public String taskExecute(@PathVariable("taskId") String taskId) {
        switch (taskId) {
            case "300001":
                pphTask.pphSinkBottomCase();
                break;
            case "300002":
                pphTask.pphSupplementDeadlineOverdue();
                break;
            case "100099":
                utilityTask.serverShareDiskStateMonitor();
                break;
            case "100000":
                utilityTask.bizAuthCaseFivebookMissMonitor();
                break;
            case "100096":
                utilityTask.bizInnerFormDataWhitespace();
                break;
            case "100002":
                utilityTask.bizMidfileAssignLateMonitor();
                break;
            case "100004":
                utilityTask.bizWarrantyEventExceptionMonitor();
                break;
            case "100005":
                utilityTask.bizDivisionEventExceptionMonitor();
                break;
            case "100006":
                utilityTask.bizOverCaseDateBlackMonitor();
                break;
            case "100007":
                utilityTask.reconfirmFillingDateInconformity();
                break;
            case "100008":
                utilityTask.bulletinBagFileMiss();
                break;
            case "100009":
                utilityTask.dismissEventException();
                break;
            case "100010":
                utilityTask.workflowExceptionRegisterUnsend();
                break;
            case "100011":
                utilityTask.caseStateExceptionMonitor();
                break;
            case "100012":
                utilityTask.overCaseDepartmentBlack();
                break;
            case "100013":
                utilityTask.reviewCaseDepartmentBlack();
                break;
            case "100015":
                utilityTask.noticeSendNewState();
                break;
            case "100016":
                utilityTask.noticeUnsendReplyState();
                break;
            case "100017":
                utilityTask.noticeUploadNewState();
                break;
            case "100021":
                utilityTask.priorityApplyUnhangup();
                break;
            case "100022":
                utilityTask.priorityApplyNationBestUnwithdraw();
                break;
            case "100077":
            utilityTask.meiYouZaiXianShenQingBeiGuaQi();
            break;
            case "100078":
               utilityTask.zaiXianShenQingGaiJieGuaWeiJieGua();
                break;
            case "100079":
               utilityTask.dmhWjQueShiXxWjSqwj();
                break;
            case "100080":
                utilityTask.noticeRejectWorkflowXxPhase();
                break;
            case "100081":
                utilityTask.linkmanInfoAllIsnull();
                break;
            case "100082":
                utilityTask.versionUnmatchClassification();
                break;
            case "100083":
                utilityTask.noticeWithdrawMsbStateUnconsistent();
                break;
            case "100084":
                utilityTask.noticeDeleteTermExists();
                break;
            case "100085":
                utilityTask.examinerWorkloadCountMonitor();
                break;
            case "100086":
                utilityTask.preconditionReviewErrorDataDelete();
                break;
            case "100087":
                utilityTask.priorityWaitResumeTermError();
                break;
            case "100088":
                utilityTask.noticeSoftscanFinishStateDraft();
                break;
            case "100095":
                utilityTask.noticeRegistrationUnsendLongTime();
                break;
            case "100097":
                utilityTask.viewPtajcxnewFresh();
                break;
            case "100014":
                utilityTask.priorityFeeUnpay();
                break;
            case "100094":
                utilityTask.historyDataHandle();
                break;
            case "100093":
                utilityTask.noticeSendDateIsNull();
                break;
            case "100092":
                utilityTask.sinkCaseMonthStatisticData();
                break;
            case "100090":
                utilityTask.comparareNoticeYesterdayCount();
                break;
//            case "100089":
//                utilityTask.unhangupCaseAuthInvalidPriority();
//                break;
            default:
                break;
        }
        String taskDesc = BusinessConstant.MONITOR_BIZ_DESC_MAP.get(taskId);
        if (StringUtils.isBlank(taskDesc)) {
            taskDesc = "未找到匹配的任务";
        }
        return taskDesc;
    }
}

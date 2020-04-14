package com.gwssi.devops.utilitypage.util;

import java.util.HashMap;
import java.util.Map;

public class BusinessConstant {
    public static final String BIZ_AUTH_CASE_FIVEBOOK_MISS="100000";//授权案件五书缺失

    public static final String BIZ_MIDFILE_ASSIGN_LATE="100002";//中间文件分配过晚导致回案

    public static final String BIZ_NOTICE_SOFTSCAN_FAIL="100003";//通知书软扫失败或软扫回调失败

    public static final String BIZ_WARRANTY_EVENT_EXCEPTION="100004";//授权通知书发出事件记录异常

    public static final String BIZ_DIVISION_EVENT_EXCEPTION="100005";//分案视未通知书发出事件记录异常

    public static final String BIZ_OVER_CASE_DATE_BLACK="100006";//已结案案件结案日期为空

    public static final String BIZ_RECONFIRM_FILLING_DATE_INCONFORMITY="100007";//重新确定申请日通知书发出申请日未变更

    public static final String BIZ_BULLETIN_BAG_FILE_MISS="100008";//公报袋数据缺失

    public static final String BIZ_DISMISS_EVENT_EXCEPTION="100009";//驳回通知书发出事件记录异常

    public static final String  BIZ_WORKFLOW_EXCEPTION_REGISTER_UNSEND="100010";//工作流异常办理登记书无法发出

    public static final String BIZ_CASE_STATE_EXCEPTION="100011";//当前状态表和电子文件夹状态不对应

    public static final String BIZ_UTILITY_SECTION_NOTICE="100098";//查询新型相关通知书

    public static final String BIZ_SERVER_SHARE_DISK_STATE="100099";//服务器共享存储状态

    public static final String PRIORITY_AUDIT_DATA_REPEAT="200001";//发明案源配送中优先审查数据重复处理
    public static final String PRIORITY_UPDATE_YONGHU_CA="200002   ";
    public static final Map<String,String> MENU_BIZ_MAP = new HashMap<>();
    public static final Map<String,String> MONITOR_BIZ_DESC_MAP = new HashMap<>();

    static {
        MENU_BIZ_MAP.put("0201", "caseStateException");
        MENU_BIZ_MAP.put("0203", "midfileAssignLate");
        MENU_BIZ_MAP.put("0205", "warrantyEventException");
        MENU_BIZ_MAP.put("0206", "divisionEventException");
        MENU_BIZ_MAP.put("0207", "overCaseDateBlack");

        MONITOR_BIZ_DESC_MAP.put("100000","授权案件五书缺失");
        MONITOR_BIZ_DESC_MAP.put("100002","中间文件分配过晚导致回案");
        MONITOR_BIZ_DESC_MAP.put("100003","通知书软扫失败或软扫回调失败");
        MONITOR_BIZ_DESC_MAP.put("100004","授权通知书发出事件记录异常");
        MONITOR_BIZ_DESC_MAP.put("100005","分案视未通知书发出事件记录异常");
        MONITOR_BIZ_DESC_MAP.put("100006","已结案案件结案日期为空");
        MONITOR_BIZ_DESC_MAP.put("100007","重新确定申请日通知书发出申请日未变更");
        MONITOR_BIZ_DESC_MAP.put("100008","公报袋数据缺失");
        MONITOR_BIZ_DESC_MAP.put("100009","驳回通知书发出事件记录异常");
        MONITOR_BIZ_DESC_MAP.put("100010","工作流异常办理登记书无法发出");
        MONITOR_BIZ_DESC_MAP.put("100011","当前状态表和电子文件夹状态不对应");
        MONITOR_BIZ_DESC_MAP.put("100099","服务器共享存储状态");
        MONITOR_BIZ_DESC_MAP.put("200001","发明案源配送中优先审查数据重复处理");

        System.out.println("aaaaa");
    }


}

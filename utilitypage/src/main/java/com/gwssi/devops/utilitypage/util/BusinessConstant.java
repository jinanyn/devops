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

    public static final String BIZ_OVER_CASE_DEPARTMENT_BLACK="100012";//结案案件处室代码为空

    public static final String BIZ_REVIEW_CASE_DEPARTMENT_BLACK="100013";//在审案件处室代码为空

    public static final String BIZ_PRIORITY_FEE_UNPAY="100014";//优先权要求费无原始费用，但界面展示费足

    public static final String BIZ_NOTICE_SEND_NEW_STATE="100015";//发送通知书，但案件仍为新案

    public static final String BIZ_NOTICE_UNSEND_REPLY_STATE="100016";//未发送通知书，但案件状态为初审待答复或者回案审查

    public static final String BIZ_NOTICE_UPLOAD_NEW_STATE="100017";//通知书已上传，但案件仍为未处理新案

    public static final String BIZ_PRIORITY_APPLY_UNHANGUP="100021";//在先申请该挂起未挂起

    public static final String BIZ_PRIORITY_APPLY_NATION_BEST_UNWITHDRAW="100022";//在先申请该国优视撤未国优视撤

    public static final String BIZ_INNER_FORM_DATA_WHITESPACE="100096";//内部表单存在空格数据

    public static final String BIZ_VIEW_PTAJCX_NEW_FRESH="100097";//物化视图view_ptajcx_new刷新数据

    public static final String BIZ_UTILITY_SECTION_NOTICE="100098";//查询新型相关通知书

    public static final String BIZ_SERVER_SHARE_DISK_STATE="100099";//服务器共享存储状态

    public static final String BIZ_PRIORITY_AUDIT_DATA_REPEAT="200001";//发明案源配送中优先审查数据重复处理
    public static final String BIZ_PRIORITY_UPDATE_YONGHU_CA="200002";

    public static final String BIZ_PPH_SINK_BOTTOM_CASE="300001";//PPH沉底案件监控

    public static final String BIZ_AJDH_ZAZTDT="100023";  //新型审查表案件状态为待回，但主表案件状态为“等待提案”或者“新案审查”
    public static final String BIZ_AJDH_ZAHASC_WDF="100024";  //案件状态为待回，但主表案件状态为“回案审查”（无答复文件），需修改主表状态为初审待答复
    public static final String BIZ_AJDH_ZAHASC_YDF="100025";  //案件状态为待回，但主表案件状态为“回案审查”（有答复文件），需修改新型审查表案件状态为未处理回案。
    public static final String BIZ_AJDH_TZSCW="100026";  //审查表案件状态为待回，发出通知书非补正和审意，主表案件状态为其他情况

    public static final String BIZ_AJDH_ZBZTBYZ="100027";  //新型审查表案件状态为回案，主表状态与新型状态不一致
    public static final String BIZ_HASC_ZBZTCSDFXSC="100028";  //案件审查表的案件状态为“回案审查”，主表状态为“初审待答复”或者“新案审查”
    public static final String BIZ_HASC_ZBZTQT="100029";  //件审查表的案件状态为“回案审查”，主表状态为其他情况

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
        MONITOR_BIZ_DESC_MAP.put("100012","结案案件处室代码为空");
        MONITOR_BIZ_DESC_MAP.put("100013","在审案件处室代码为空");
        MONITOR_BIZ_DESC_MAP.put("100014","优先权要求费无原始费用，但界面展示费足");
        MONITOR_BIZ_DESC_MAP.put("100015","发送通知书，但案件仍为新案");
        MONITOR_BIZ_DESC_MAP.put("100016","未发送通知书，但案件状态为初审待答复或者回案审查");
        MONITOR_BIZ_DESC_MAP.put("100017","通知书已上传，但案件仍为未处理新案");
        MONITOR_BIZ_DESC_MAP.put("100021","在先申请该挂起未挂起");


        MONITOR_BIZ_DESC_MAP.put("100023","型审查表案件状态为待回，但主表案件状态为等待提案或者新案审查");
        MONITOR_BIZ_DESC_MAP.put("100024","案件状态为待回，但主表案件状态为“回案审查”（无答复文件）");
        MONITOR_BIZ_DESC_MAP.put("100025","案件状态为待回，但主表案件状态为“回案审查”（有答复文件）");
        MONITOR_BIZ_DESC_MAP.put("100026","审查表案件状态为待回，发出通知书非补正和审意，主表案件状态为其他情况");

        MONITOR_BIZ_DESC_MAP.put("100027","审查表案件状态为回案，主表状态与新型状态不一致");
        MONITOR_BIZ_DESC_MAP.put("100028","案件审查表的案件状态为“回案审查”，主表状态为“初审待答复”或者“新案审查”");
        MONITOR_BIZ_DESC_MAP.put("100029","件审查表的案件状态为“回案审查”，主表状态为其他情况");

        MONITOR_BIZ_DESC_MAP.put("100096","内部表单存在空格数据");
        MONITOR_BIZ_DESC_MAP.put("100097","物化视图view_ptajcx_new刷新数据");
        MONITOR_BIZ_DESC_MAP.put("100098","查询新型相关通知书");
        MONITOR_BIZ_DESC_MAP.put("100099","服务器共享存储状态");


        MONITOR_BIZ_DESC_MAP.put("200001","发明案源配送中优先审查数据重复处理");
        MONITOR_BIZ_DESC_MAP.put("300001","PPH沉底案件监控");
        System.out.println("aaaaa");
    }


}

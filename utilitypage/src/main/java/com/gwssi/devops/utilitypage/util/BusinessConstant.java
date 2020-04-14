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

    public static final String SERVER_SHARE_DISK_STATE="100007";//服务器共享存储状态

    public static final String BIZ_AUTH_CASE_FIVEBOOK_MISS7="100000";//授权案件五书缺失

    public static final String BIZ_AUTH_CASE_FIVEBOOK_MISS8="100000";//授权案件五书缺失

    public static final String BIZ_AUTH_CASE_FIVEBOOK_MISS9="100000";//授权案件五书缺失

    public static final String BIZ_CASE_STATE_EXCEPTION="100011";//当前状态表和电子文件夹状态不对应



    public static final String PRIORITY_AUDIT_DATA_REPEAT="200001";//发明案源配送中优先审查数据重复处理
    public static final String PRIORITY_UPDATE_YONGHU_CA="200002   ";
    public static final Map<String,String> MENU_BIZ_MAP = new HashMap<>();

    static {
        MENU_BIZ_MAP.put("0201", "caseStateException");
    }
}

package cn.gwssi.xx;

import cn.gwssi.util.JdbcUtil;
import cn.gwssi.util.NumPercentUtil;

import java.util.List;
import java.util.Map;

/**
 * 执行结果参数
 * D:/develop/jdk/jdk8/bin/java -jar C:\Users\yangning\Desktop\xxWeblogicCheck\dataStats-1.0-SNAPSHOT-jar-with-dependencies.jar 开始日期 结束日期 结束日期上周登录人次 本周日均登录人数 上周日均登录人数 上周发文量 本周报修问题数量 本周报修问题数量
 */
public class WeekDataStat {
    public static void main(String[] args) {
//        System.out.println("aaaaaaaaaaaaaaaaaaaaa");
//        Connection conn = JdbcUtil.getConnection();
//        System.out.println(conn);
        String startDate = args[0];//开始日期
        String endDate = args[1];//结束日期

        String beforeWeekLoginCount = args[2];//结束日期上周登录人次

        String avgLoginCnt = args[3];//本周日均登录人数
        String beforeWeekAvgLoginCnt = args[4];//上周日均登录人数

        String beforeWeekNoticeCount = args[5];//上周发文量

        String bugCount = args[6];//本周报修问题数量
        String beforeWeekBugCount = args[7];//本周报修问题数量

       // System.out.println(startDate +";"+endDate);
        String template = "本周登录人次{loginCount}次（较上周{loginCountPercent}），日均登录人数{avgLoginCnt}（较上周{avgLoginCntPercent}），发文量{noticeCount}件（较上周{noticeCountPercent}）；本周共报修{bugCount}个问题（较上周{bugCountPercent}）；新型相关智能审查业务优化、以及数据运维等工作按计划进行。系统运行情况。";

        String sql = "select count(1) cnt from XX_RZGL_DLRZ@dzspscp_gwssi_user where denglusj>=? and denglusj<= ? ";
        Object[] paramObj = new Object[]{startDate+"000000",endDate+"000000"};
        List<Map<String,String>> resList = JdbcUtil.query(sql,paramObj);
        String loginCount = resList.get(0).get("CNT");
        template = template.replaceFirst("\\{loginCount}",loginCount);
        Integer loginCountNum = Integer.parseInt(loginCount);
        Integer beforeWeekLoginCountNum =Integer.parseInt(beforeWeekLoginCount);
        if(loginCountNum > beforeWeekLoginCountNum){
            template =template.replaceFirst("\\{loginCountPercent}","增加"+ NumPercentUtil.toNumStr(loginCountNum,beforeWeekLoginCountNum,beforeWeekLoginCountNum)+"%");
        }else if(loginCountNum < beforeWeekLoginCountNum){
            template =template.replaceFirst("\\{loginCountPercent}","降低"+ NumPercentUtil.toNumStr(beforeWeekLoginCountNum,loginCountNum,beforeWeekLoginCountNum)+"%");
        }else{
            template =template.replaceFirst("\\{loginCountPercent}","持平");
        }

        template = template.replaceFirst("\\{avgLoginCnt}",avgLoginCnt);
        Integer avgLoginCntNum = Integer.parseInt(avgLoginCnt);
        Integer beforeWeekAvgLoginCntNum =Integer.parseInt(beforeWeekAvgLoginCnt);
        if(avgLoginCntNum > beforeWeekAvgLoginCntNum){
            template =template.replaceFirst("\\{avgLoginCntPercent}","增加"+ NumPercentUtil.toNumStr(avgLoginCntNum,beforeWeekAvgLoginCntNum,beforeWeekAvgLoginCntNum)+"%");
        }else if(avgLoginCntNum < beforeWeekAvgLoginCntNum){
            template =template.replaceFirst("\\{avgLoginCntPercent}","降低"+ NumPercentUtil.toNumStr(beforeWeekAvgLoginCntNum,avgLoginCntNum,beforeWeekAvgLoginCntNum)+"%");
        }else{
            template =template.replaceFirst("\\{avgLoginCntPercent}","持平");
        }


        sql = "select count(1) cnt from gg_wj_tzsmsb@dzspscp_gwssi_user t where t.zixtdm = '09' and t.tongzhiszt = '05' and t.fawenxlh >= ? and t.fawenxlh<= ? ";
        paramObj = new Object[]{startDate+"000000000",endDate+"000000000"};
        resList = JdbcUtil.query(sql,paramObj);
        String noticeCount = resList.get(0).get("CNT");
        template = template.replaceFirst("\\{noticeCount}",noticeCount);
        Integer noticeCountNum = Integer.parseInt(noticeCount);
        Integer beforeWeekNoticeCountNum =Integer.parseInt(beforeWeekNoticeCount);
        if(noticeCountNum > beforeWeekNoticeCountNum){
            template =template.replaceFirst("\\{noticeCountPercent}","增加"+ NumPercentUtil.toNumStr(noticeCountNum,beforeWeekNoticeCountNum,beforeWeekNoticeCountNum)+"%");
        }else if(noticeCountNum < beforeWeekNoticeCountNum){
            template =template.replaceFirst("\\{noticeCountPercent}","降低"+ NumPercentUtil.toNumStr(beforeWeekNoticeCountNum,noticeCountNum,beforeWeekNoticeCountNum)+"%");
        }else{
            template =template.replaceFirst("\\{noticeCountPercent}","持平");
        }

        template = template.replaceFirst("\\{bugCount}",bugCount);
        Integer bugCountNum = Integer.parseInt(bugCount);
        Integer beforeWeekBugCountNum =Integer.parseInt(beforeWeekBugCount);
        if(bugCountNum > beforeWeekBugCountNum){
            template =template.replaceFirst("\\{bugCountPercent}","增加"+ NumPercentUtil.toNumStr(bugCountNum,beforeWeekBugCountNum,beforeWeekBugCountNum)+"%");
        }else if(bugCountNum < beforeWeekBugCountNum){
            template =template.replaceFirst("\\{bugCountPercent}","降低"+ NumPercentUtil.toNumStr(beforeWeekBugCountNum,bugCountNum,beforeWeekBugCountNum)+"%");
        }else{
            template =template.replaceFirst("\\{bugCountPercent}","持平");
        }

        System.out.println(template);
    }
}

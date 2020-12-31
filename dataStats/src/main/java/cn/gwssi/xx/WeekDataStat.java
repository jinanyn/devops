package cn.gwssi.xx;

import cn.gwssi.util.JdbcUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class WeekDataStat {
    public static void main(String[] args) {
//        System.out.println("aaaaaaaaaaaaaaaaaaaaa");
//        Connection conn = JdbcUtil.getConnection();
//        System.out.println(conn);
        String startDate = args[0];
        String endDate = args[1];

        System.out.println(startDate +";"+endDate);
        String template = "平均登陆人数{avg_login_count}";

        String sql = "select t.shenqingh,t.shenchaydm,t.shenchabj from xx_ajscb@dzspscp_gwssi_user t where t.jieanrq >= ? and  t.jieanrq <= ? ";
        Object[] paramObj = new Object[]{startDate,endDate};
        List<Map<String,String>> resList = JdbcUtil.query(sql,paramObj);
        String bbb = resList.get(0).get("SHENQINGH");
        String aa = template.replaceFirst("\\{avg_login_count}",bbb);
        System.out.println(aa);
    }
}

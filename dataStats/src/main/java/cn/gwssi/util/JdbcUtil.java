package cn.gwssi.util;

import java.sql.*;
import java.util.*;

/**
 * jdbc工具类，负责：
 * 1. 加载/注册数据库驱动程序
 * 2. 获取数据库连接
 * 3. 释放数据库资源（Connection, Statement, ResultSet）
 */
public class JdbcUtil {
    private static final String DRIVER = getValue("jdbc.driver");
    private static final String URL = getValue("jdbc.url");
    private static final String USERNAME = getValue("jdbc.username");
    private static final String PASSWORD = getValue("jdbc.password");

    static{
        try {
            //1. 注册数据库驱动程序
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("注册数据库驱动程序失败。" + e.getMessage());
        }
    }
    /**
     * 2. 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return  conn;
        } catch (SQLException e) {
            System.err.println("a获得数据连接失败。" + e.getMessage());
        }
        return null;
    }

    /**
     * @param sql sql语句
     * @param obj 参数
     * @return 数据集合
     */
    public static List<Map<String,String>> query(String sql,Object...obj) {
        List<Map<String,String>> resList = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement sta = null;
        try {
            sta = conn.prepareStatement(sql);
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    sta.setObject(i + 1, obj[i]);
                }
            }
            rs = sta.executeQuery();
            ResultSetMetaData metaData= rs.getMetaData();
            int cloumnNum = metaData.getColumnCount();
            Set<String> columnSet = new HashSet<String>();
            for (int i = 1; i <= cloumnNum; i++) {
                String columnName = metaData.getColumnName(i);
                columnSet.add(columnName);
            }
            while (rs.next()) {
                Map<String,String> dataMap = new HashMap<>();
                for (String columnName : columnSet) {
                    String columnValue = rs.getString(columnName);
                    dataMap.put(columnName,columnValue);
                }
                resList.add(dataMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn,sta,null,rs);
        }
        return resList;
    }

        /**
         * @param conn
         * @param stmt
         * @param rs
         */
    public static void close(Connection conn, PreparedStatement psmt, Statement stmt, ResultSet rs) {
        try {
            //关闭数据库的资源的顺序最好与使用的顺序相反
            if(rs != null){
                rs.close();
            }
            if(psmt != null){
                psmt.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 读取属性文件中的信息
     *
     * @param key
     * @return
     */
    private static String getValue(String key) {
        // 资源包绑定
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        return bundle.getString(key);
    }

    /**
     * 对数据库连接进行测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(getValue("jdbc.driver"));
        System.out.println(getConnection());
    }
}

package com.ebase.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 //存储过程调用数据库的配置文件。本地启动需注意（操作触发点在拆分质证书提交按钮上，配置出问题可能导致拆分测试数据库/正式数据库数据混乱，质证书生成不出来）
public class JDBCUtils {
    private static String driver="oracle.jdbc.OracleDriver";
    //生产
    //private static String url="jdbc:oracle:thin:@10.1.213.125:1521:jgcrmdbp";
    //测试
    private static String url="jdbc:oracle:thin:@10.1.213.127:1521:jgcrmdbt";
    private static String user="WARRANTY";
    private static String password="WARRANTY";

    static{
        try {
            Class.forName(driver); //加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void release(Connection conn,Statement st,ResultSet rs){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                conn=null;//垃圾回收
            }
        }
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                st=null;//垃圾回收
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                rs=null;//垃圾回收
            }
        }
    }
}

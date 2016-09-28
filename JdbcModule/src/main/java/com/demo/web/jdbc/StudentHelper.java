package com.demo.web.jdbc;

import java.sql.*;

/**
 * 2016-09-28.
 */
public class StudentHelper {

    public static void main(String[] args) {

        StudentHelper helper = new StudentHelper();
        helper.test();

    }

    private void test() {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // 建立连接
            conn = JdbcHelper.getInstance().getConnection();

            // 创建语句
            st = conn.createStatement();

            // 执行语句
            rs = st.executeQuery("select * from student");

            // 处理结果
            while (rs.next()) {
                System.out.println("student row :" +
                        " Id=" + rs.getString(1) +
                        ", Name=" + rs.getString(2) +
                        ", Age=" + rs.getString(3) +
                        ", Phone=" + rs.getString(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(rs, st, conn);
        }

    }

}

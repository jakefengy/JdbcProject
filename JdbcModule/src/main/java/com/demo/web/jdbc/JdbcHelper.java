package com.demo.web.jdbc;

import java.sql.*;

/**
 */
final class JdbcHelper {

    // http://blog.csdn.net/kl28978113/article/details/52585799

    private final static String url = "jdbc:mysql://localhost:3306/hello_jdbc?serverTimezone=UTC&characterEncoding=utf-8";
    private final static String driver = "com.mysql.cj.jdbc.Driver";
    private final static String user = "root";
    private final static String password = "123456";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static class SingletonHolder {
        private static final JdbcHelper INSTANCE = new JdbcHelper();
    }

    private JdbcHelper() {
    }

    public static JdbcHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    void free(ResultSet rs, Statement st, Connection conn) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}

package com.demo.web.jdbc;

import com.demo.web.entity.Student;
import com.demo.web.utils.PrintUtils;

import java.sql.*;
import java.util.List;

/**
 * 2016-09-28.
 */
public class StudentHelper {

    public static void main(String[] args) {

        StudentHelper helper = new StudentHelper();

        Student student = new Student();
        student.setId(3);
        student.setName("傻逼轮");
        student.setAge(10);
        student.setPhone("13859385469");

//        helper.add(student);

//        helper.update();

//        helper.search();

//        helper.delete();

//        helper.sumAB();

//        helper.transfer();

        helper.test();
    }

    // 批处理
    private void addAll() {
        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = JdbcHelper.getInstance().getConnection();
            statement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)");

            for (int index = 1; index < 4; index++) {
                statement.setInt(1, 2 + index);
                statement.setString(2, "傻逼轮");
                statement.setString(3, String.valueOf(index));
                statement.setString(4, "15487854127");
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(null, statement, connection);
        }


    }

    private void search() {

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
                PrintUtils.println("student row :" +
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

    private void add(Student student) {
        Connection conn = null;
        Statement st = null;

        try {
            // 建立连接
            conn = JdbcHelper.getInstance().getConnection();

            // 创建语句
            st = conn.createStatement();

            // 执行语句
            String sql = "insert into student (id, name, age, phone) value " +
                    "(" + student.getId() + ", '" + student.getName() + "', " + student.getAge() + ", '" + student.getPhone() + "')";

            // 处理结果
            if (st.executeUpdate(sql) > 0) {
                PrintUtils.println("数据已添加");
            } else {
                PrintUtils.println("数据添加失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(null, st, conn);
        }
    }

    private void update() {
        Connection conn = null;
        Statement st = null;

        try {
            // 建立连接
            conn = JdbcHelper.getInstance().getConnection();

            // 创建语句
            st = conn.createStatement();

            // 执行语句
            String sql = "UPDATE student SET NAME = '" + "修改之後的名字" + "' WHERE id = 3";

            // 处理结果
            if (st.executeUpdate(sql) > 0) {
                PrintUtils.println("数据已修改");
            } else {
                PrintUtils.println("数据修改失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(null, st, conn);
        }
    }

    private void delete() {
        Connection conn = null;
        Statement st = null;

        try {
            conn = JdbcHelper.getInstance().getConnection();
            st = conn.createStatement();

            String sql = "DELETE FROM student WHERE id = 3";

            if (st.executeUpdate(sql) > 0) {
                PrintUtils.println("数据已删除");
            } else {
                PrintUtils.println("数据删除失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(null, st, conn);
        }

    }

    // 添加事物处理
    private void transfer() {
        Connection connection = null;

        PreparedStatement statement = null;

        try {
            connection = JdbcHelper.getInstance().getConnection();

            connection.setAutoCommit(false);

            statement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)");

            for (int index = 1; index < 3; index++) {
                statement.setInt(1, 5 + index);
                statement.setString(2, "事物处理");
                statement.setString(3, String.valueOf(index));
                statement.setString(4, "扒拉");
                statement.addBatch();
            }

            statement.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            JdbcHelper.getInstance().free(null, statement, connection);
        }
    }

    //執行存儲過程
    private void sumAB() {

        Connection conn = null;
        CallableStatement cst = null;
        try {
            conn = JdbcHelper.getInstance().getConnection();
            cst = conn.prepareCall("call sum_ab(?,?,?)");

            cst.registerOutParameter(3, Types.INTEGER);
            cst.setInt(1, 2);
            cst.setInt(2, 3);

            cst.execute();

            PrintUtils.println("執行存儲过程：sum_ab(2,3) ==> " + cst.getInt(3));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcHelper.getInstance().free(null, cst, conn);
        }


    }

    private void test() {
        int n = 25;

        System.out.println("Input num = " + n);

        int radius = (int) (Math.sqrt(n));

        int count = 0;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (Math.pow(x, 2) + Math.pow(y, 2) == n) {
                    System.out.println("coordinate = (" + x + " , " + y + ")");
                    count++;
                }
            }
        }

        System.out.println("input " + n + ", count " + count);
    }

}

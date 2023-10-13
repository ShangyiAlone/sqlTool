package test;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class MySQLExample {

    // JDBC连接URL，注意：此处的"mydatabase"为你的数据库名称
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sqltool";

    // 数据库的用户名与密码，需要替换为你自己的用户名和密码
    private static final String USER = "root";
    private static final String PASSWORD = "shangyi";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 打开链接
            System.out.println("连接数据库...");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            // 执行数据库操作，此处可以添加你的业务逻辑

            System.out.println("数据库连接成功！");
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}


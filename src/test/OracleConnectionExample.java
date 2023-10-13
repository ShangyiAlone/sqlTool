package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnectionExample {

    // JDBC连接URL，注意：此处的"oracledb"为你的数据库名称
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:";

    // 数据库的用户名与密码，需要替换为你自己的用户名和密码
    private static final String USER = "C##SHANGYI1";
    private static final String PASSWORD = "1";

    public static void main(String[] args) {
        Connection connection = null;

        try {
            // 注册 JDBC 驱动
            Class.forName("oracle.jdbc.driver.OracleDriver");

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


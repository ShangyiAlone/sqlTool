import java.sql.Connection;

import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;


public class MysqlConnection {
    private static Connection connection; // 声明Connection对象
    private MysqlConnection(String url, String username, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = getConnection(url, username, password);
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException { // 主方法，测试连接
        String dbAddress = "localhost:3306";
        String dbname = "sqltool";
        String user = "root";
        String password = "shangyi";
        String type = "mysql";

        String url = "jdbc:mysql://localhost:3306/sqltool";

        MysqlConnection m = new MysqlConnection(url,user,password);
        java.sql.Connection con = m.connection;

    }

}

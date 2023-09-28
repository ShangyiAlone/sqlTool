import java.sql.*;

public class MysqlConnection {
    private static String username;
    private static  String password;
    private static String url;
    private volatile static MysqlConnection mysqlConnection;  // volatile禁止指令重排
    private static Connection connection; // 声明Connection对象
    private MysqlConnection(String url, String username, String password) throws SQLException {
        System.out.println(Thread.currentThread().getName()+" 构造方法");

        this.url = url;
        this.username = username;
        this.password = password;

        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static MysqlConnection getSqlConnect(String url, String username, String password) throws SQLException {
        if (mysqlConnection ==null) {
            synchronized (MysqlConnection.class){
                if(mysqlConnection ==null){
                    mysqlConnection = new MysqlConnection(url,username,password);
                }
            }
        }

        return mysqlConnection;
    }

    public static MysqlConnection getSqlConnect(){
        return mysqlConnection;
    }

    public Connection getConnection(){
        return connection;
    }

    public void setDatabaseParams(String url, String username, String password) throws SQLException {
        // 在这里设置新的数据库连接参数，并重新初始化连接
        this.url = url;
        this.username = username;
        this.password = password;

        connection = DriverManager.getConnection(url, username, password);
    }

    public static boolean isInstanceCreated() {
        return mysqlConnection != null;
    }

    public void setConnection(Connection con){
        connection = con;
    }

    public static void main(String[] args) throws SQLException { // 主方法，测试连接
    }

}

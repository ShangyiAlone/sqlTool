import java.sql.*;

public class OracleConnection {
    private static String username;
    private static  String password;
    private static String url;
    private volatile static OracleConnection oracleConnection;  // volatile禁止指令重排
    public static Connection connection; // 声明Connection对象
    public OracleConnection(String url, String username, String password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.url = url;
        this.username = username;
        this.password = password;

        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static Connection getSqlConnect(String url, String username, String password) throws SQLException {
        if (oracleConnection ==null) {
            synchronized (MysqlConnection.class){
                if(oracleConnection ==null){
                    oracleConnection = new OracleConnection(url,username,password);
                }
            }
        }

        return oracleConnection.connection;
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
        return oracleConnection != null;
    }

    public static void setConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);

    }

    public static void main(String[] args) throws SQLException { // 主方法，测试连接
        Connection con = getSqlConnect("jdbc:oracle:thin:@//10.16.53.25:1521/ORCLPDB1","NCC_IFRS9_0605","1");
        System.out.println(con.getMetaData());

        Statement stmt = con.createStatement();

        // SQL 插入语句
//        String sql = "INSERT INTO a_test (id, name) vALUES (3, 'shangyi');";

        String sql = "select * from a_test ";

        // 执行插入操作
        stmt.execute(sql);



    }
}

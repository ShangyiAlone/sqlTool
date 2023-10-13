package DataBaseConnection;

import java.sql.*;

public class OracleConnection {

    private static OracleConnection oracleConnection;
    public static Connection connection; // 声明Connection对象
    public OracleConnection(String url, String username, String password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static Connection getSqlConnect(String url, String username, String password) throws SQLException {
        if (oracleConnection ==null) {
            oracleConnection = new OracleConnection(url,username,password);
        }

        return oracleConnection.connection;
    }


    public static void main(String[] args) throws SQLException { // 主方法，测试连接
        Connection con = getSqlConnect("jdbc:oracle:thin:@localhost:1521:XE","C##SHANGYI1","1");
        System.out.println(con.getMetaData());

        Statement stmt = con.createStatement();
        ResultSet rs = null;


        try {
            stmt = con.createStatement();
            String sql = "SELECT s.* FROM ( SELECT t.*, ROWNUM AS rnum FROM test t where ROWNUM  BETWEEN 1 AND 6 ) s ";

            boolean hasResults = stmt.execute(sql);

            if (hasResults) {
                rs = stmt.getResultSet();

                // 处理结果集
                while (rs.next()) {
                    // 获取数据
                    int id = rs.getInt("id");
                    String name = rs.getString("name");

                    // 处理数据，你可以根据具体的表结构获取其他列的数据
                    System.out.println("ID: " + id + ", Name: " + name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭 ResultSet、Statement 和 Connection
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }





    }
}

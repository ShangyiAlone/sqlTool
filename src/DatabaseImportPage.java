import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImportPage extends JFrame{
    private JTextField dbAddressField;
    private JPasswordField passwordField;
    private JTextField userField;
    private JTextField folderField;

    private JTextField dbNameField;

    public DatabaseImportPage() {
        setTitle("数据库导入工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new GridLayout(6, 2));

        JLabel dbAddressLabel = new JLabel("服务器地址:");
        dbAddressField = new JTextField();
        JLabel dbName = new JLabel("数据库名称:");
        dbNameField = new JTextField();
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField();
        JLabel userLabel = new JLabel("用户:");
        userField = new JTextField();
        JLabel folderLabel = new JLabel("文件夹位置:");
        folderField = new JTextField();

        JButton importButton = new JButton("开始导入");
        JButton testConnectionButton = new JButton("测试连接");

        add(dbAddressLabel);
        add(dbAddressField);
        add(dbName);
        add(dbNameField);
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(folderLabel);
        add(folderField);
        add(importButton);
        add(testConnectionButton);

        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取文本框中的值
                String dbAddress = dbAddressField.getText();
                String dbName = dbNameField.getText();
                String password = new String(passwordField.getPassword());
                String user = userField.getText();
                String folder = folderField.getText();

                // 执行导入操作，你可以在这里调用相应的方法或函数


                try {
                    importData(dbAddress,dbName ,user, password, folder);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        testConnectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取文本框中的值
                String dbAddress = dbAddressField.getText();
                String dbName = dbNameField.getText();
                String password = new String(passwordField.getPassword());
                String user = userField.getText();


                // 执行测试连接的操作，你可以在这里调用相应的方法或函数
                try {
                    testDatabaseConnection(dbAddress,dbName ,user, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // 添加导入数据的逻辑方法
    private void importData(String dbAddress,String dbname, String user, String password, String folderPath) throws SQLException {
        dbAddress = "123";
        dbname = "123";
        user = "123";
        password = "123";
        folderPath = "C:\\Users\\Administrator\\Desktop\\test";


        String url = "jdbc:mysql://"+dbAddress+"/"+dbname;
        boolean SqlConnectCreated =  SqlConnect.isInstanceCreated();

        try {
            if(SqlConnectCreated){
                SqlConnect.getSqlConnect().setDatabaseParams(url,user,password);
            }else{
                SqlConnect.getSqlConnect(url,dbname,password);
            }

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
            SqlConnect.getSqlConnect().setConnection(null);
            // 捕获数据库连接异常
            e.printStackTrace();

        }

        //测试代码
        SqlConnect connection = SqlConnect.getSqlConnect();

        Connection con =  connection.getConnection(); // 调用连接数据库的方法
//        con.setAutoCommit(false); //禁止自动提交
        Statement statement = null; // 创建声明对象
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        File folder = new File(folderPath);

        java.util.List<String> sqlFiles = FileHandle.findSqlFiles(folder);

        for (String sqlFilePath : sqlFiles) {
            String result = new String();
            try {
                result =  FileHandle.readFileContent(sqlFilePath);
//                System.out.println(result);
                String[] lines = result.split("\n");
                for( String line : lines){
                    statement.addBatch(line);
                }
            } catch (FileNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "执行失败：" + e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
                throw new RuntimeException(e);
            }

        }

        int[] updateCounts = new int[0];
        try {
            updateCounts = statement.executeBatch();
            for (int count : updateCounts) {
                System.out.println("受影响的行数：" + count);
            }
//            con.commit();
            JOptionPane.showMessageDialog(null, "执行成功" , "提示", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "执行失败：" + e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
            throw new RuntimeException(e);
        }

    }

    // 添加测试数据库连接的逻辑方法
    private void testDatabaseConnection(String dbAddress, String dbname,String user, String password) throws SQLException {
        String url = "jdbc:mysql://"+dbAddress+"/"+dbname;
//        SqlConnect c = SqlConnect.getSqlConnect("jdbc:mysql://123/123","123","123");

        boolean SqlConnectCreated =  SqlConnect.isInstanceCreated();

        try {
            if(SqlConnectCreated){
                SqlConnect.getSqlConnect().setDatabaseParams(url,user,password);
            }else{
                SqlConnect.getSqlConnect(url,dbname,password);
            }
            JOptionPane.showMessageDialog(null, "连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        }catch (SQLException e) {
            // 捕获数据库连接异常
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void test(SqlConnect c) throws SQLException {
        Connection connection =  c.getConnection(); // 调用连接数据库的方法
        Statement statement = connection.createStatement(); // 创建声明对象

        String sql = "select  * from aam_appasset limit 3,20;";
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            System.out.println(resultSet.getString("ASSETNAME"));
        }
    }

    public static void main(String[] args) {
 
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DatabaseImportPage importApp = new DatabaseImportPage();
                importApp.setVisible(true);
            }
        });

    }
}
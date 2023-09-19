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
import java.util.ArrayList;

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

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel folderLabel = new JLabel("文件夹位置:");
        folderField = new JTextField();
        JButton folderButton = new JButton("选择文件夹");
        panel.add(folderField);
        panel.add(folderButton);

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
        add(panel);
        add(importButton);
        add(testConnectionButton);

        // 导入文件夹下sql文件，并测试连接
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

                importData(dbAddress,dbName ,user, password, folder);


            }
        });

        // 测试数据库配置是否成功
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


        // 选择文件夹或者文件
        folderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    // 用户选择了文件或文件夹
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedPath = selectedFile.getAbsolutePath();
                    folderField.setText(selectedPath);
                } else {
                    // 用户取消了选择
                    folderField.setText("");
                }
            }
        });
    }

    // 添加导入数据的逻辑方法
    private void importData(String dbAddress,String dbname, String user, String password, String folderPath)  {
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
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            SqlConnect.getSqlConnect().setConnection(null);
            // 捕获数据库连接异常
            e.printStackTrace();

        }

        SqlConnect connection = SqlConnect.getSqlConnect();

        Connection con =  connection.getConnection(); // 调用连接数据库的方法


        Statement statement = null; // 创建声明对象
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        java.util.List<String> sqlFiles = new ArrayList<>();

        if(folderPath.endsWith(".sql")){
            sqlFiles.add(folderPath);
        }else{
            File folder = new File(folderPath);
            sqlFiles = FileHandle.findSqlFiles(folder);
        }


//        try {
//            con.setAutoCommit(false);  // 以文件为单位进行事务处理

        boolean continueExecute = true;
        for (String sqlFilePath : sqlFiles) {
            if(continueExecute){

                String result = new String();

                try {  // 捕获文件未取到的 exception
                    result = FileHandle.readFileContent(sqlFilePath);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                String[] lines = result.split("\n");
                for (String line : lines) {
                    if(continueExecute){
                        try {
                            System.out.println(line);
                            statement.execute(line);
                        } catch (SQLException e) { // 捕获sql异常，提示用用户修改
                            DeBugPage newFrame = new DeBugPage(line,lines,sqlFilePath, sqlFiles,statement);
                            continueExecute = false;
                            throw new RuntimeException(e);
                        }
                    }else break;
                }
            }else break;
        }

//        JOptionPane.showMessageDialog(null, "执行成功" , "提示", JOptionPane.INFORMATION_MESSAGE);


//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                con.rollback();
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                con.setAutoCommit(true);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }

    }

//    private static void ExcuteSql(Statement statement,java.util.List<String> sqlFiles,int start){
//
////        这里通过索引，跳过已经执行过的那个sql文件，直接执行下一个
//        for (int i = start+1;i<sqlFiles.size();i++) {
//            String result = new String();
//
//            try {  // 捕获文件未取到的 exception
//                result = FileHandle.readFileContent(sqlFiles.get(i));
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//            String[] lines = result.split("\n");
//            for (String line : lines) {
//                try {
//                    statement.execute(line);
//                } catch (SQLException e) { // 捕获sql异常，提示用用户修改
//                    DeBugPage newFrame = new DeBugPage(line,lines,sqlFiles.get(i), sqlFiles,statement);
//                }
//            }
//        }
//    }


    // 添加测试数据库连接的逻辑方法
    private void testDatabaseConnection(String dbAddress, String dbname,String user, String password) throws SQLException {
        String url = "jdbc:mysql://"+dbAddress+"/"+dbname;
//        SqlConnect c = SqlConnect.getSqlConnect("jdbc:mysql://123/
//        123",
//        "123",
//        "123");

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
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImportPage extends JFrame{

    private JTextField dbAddressField;
    private JPasswordField passwordField;
    private JTextField userField;
    private JTextField folderField;
    private JTextField dbNameField;
    protected static JTextField FileTypeField;

    boolean continueExecute = true;

    public DatabaseImportPage() {
        setTitle("数据库导入工具");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
//        setLayout(new GridLayout(8, 2)); // 添加文件编码输入框
        setLayout(new GridLayout(7, 2));
        ImageIcon icon = new ImageIcon("src/pic/icon.jpg");
        setIconImage(icon.getImage());

        JLabel dbTypeLabel = new JLabel("服务器类型:");
        JComboBox<String> dbTypecomboBox = new JComboBox<>(new String[]{"mysql", "oracle"});

        JLabel dbAddressLabel = new JLabel("服务器地址:");
        dbAddressField = new JTextField();
        JLabel dbName = new JLabel("数据库名称:");
        dbNameField = new JTextField();
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField();
        JLabel userLabel = new JLabel("用户:");
        userField = new JTextField();

        JLabel fileTypeLabel = new JLabel("文件编码:");
        FileTypeField = new JTextField("UTF-8");

        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel folderLabel = new JLabel("文件夹位置:");
        folderField = new JTextField();
        JButton folderButton = new JButton("选择文件夹");
        panel.add(folderField);
        panel.add(folderButton);

        JButton importButton = new JButton("开始导入");
        JButton testConnectionButton = new JButton("测试连接");

//        字体居中
        dbTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dbAddressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dbName.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        folderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fileTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);

//        字体大小
        Font labelFont = new Font("Font.BOLD", Font.PLAIN, 16); // 替换 "Arial" 和 16 为你想要的字体和大小
        dbTypeLabel.setFont(labelFont);
        dbAddressLabel.setFont(labelFont);
        dbName.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        userLabel.setFont(labelFont);
        folderLabel.setFont(labelFont);
        fileTypeLabel.setFont(labelFont);

        add(dbTypeLabel);
        add(dbTypecomboBox);

        add(dbAddressLabel);
        add(dbAddressField);
        add(dbName);
        add(dbNameField);
        add(userLabel);
        add(userField);
        add(passwordLabel);
        add(passwordField);

//        add(fileTypeLabel);
//        add(FileTypeField);

        add(folderLabel);
        add(panel);

        add(importButton);
        add(testConnectionButton);

        // 导入文件夹下sql文件，并测试连接
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取文本框中的值
                String dbType = (String) dbTypecomboBox.getSelectedItem();
                String dbAddress = dbAddressField.getText();
                String dbName = dbNameField.getText();
                String password = new String(passwordField.getPassword());
                String user = userField.getText();
                String folder = folderField.getText();
                String fileType = FileTypeField.getText(); // 文件编码格式

                // 执行导入操作，你可以在这里调用相应的方法或函数
                importData(dbAddress,dbName ,user, password, folder,dbType,fileType);
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
                String type = (String) dbTypecomboBox.getSelectedItem();

                // 执行测试连接的操作，你可以在这里调用相应的方法或函数
                try {
                    testDatabaseConnection(dbAddress,dbName ,user, password,type);
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
    private void importData(String dbAddress,String dbname, String user, String password, String folderPath,String dbtype,String fileType)  {
        dbAddress = "123";
        dbname = "sqltool";
        user = "root";
        password = "shangyi";
        folderPath = "C:\\Users\\Administrator\\Desktop\\test";
        dbtype = "mysql";

        //        oracle配置
        dbAddress = "localhost:1521";
        dbname = "XE";
        user = "C###SHANGYI";
        password = "1";
        folderPath = "C:\\Users\\Administrator\\Desktop\\test";
        dbtype = "oracle";


        Connection con = null;
        if (dbtype == "mysql"){
            String url = "jdbc:" + dbtype+ "://" + dbAddress + "/" + dbname;

//            boolean SqlConnectCreated = MysqlConnection.isInstanceCreated();
//
//            try {
//                if (SqlConnectCreated) {
//                    MysqlConnection.getSqlConnect().setDatabaseParams(url, user, password);
//                } else {
//                    MysqlConnection.getSqlConnect(url, dbname, password);
//                }
//
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
//                MysqlConnection.getSqlConnect().setConnection(null);
//                // 捕获数据库连接异常
//                e.printStackTrace();
//
//            }
//
//            MysqlConnection connection = MysqlConnection.getSqlConnect();

//        Connection con = connection.getConnection(); // 调用连接数据库的方法
//            con = connection.getSqlConnect().getConnection();
        }else{
            String url = "jdbc:" + dbtype +":thin:@"+"//"+dbAddress+":"+dbname;

            System.out.println(url);

            try {
                OracleConnection oracleConnection = new OracleConnection(url,user,password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            con = OracleConnection.connection;
        }


        Statement statement = null; // 创建声明对象
        try {
            statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        java.util.List<String> sqlFiles = new ArrayList<>();

        if (folderPath.endsWith(".sql")) {
            sqlFiles.add(folderPath);
        } else {
            File folder = new File(folderPath);
            sqlFiles = FileHandle.findSqlFiles(folder);
        }


        ProgressBarPage progressBarPage = ProgressBarPage.getProgressBarPage();

        Statement finalStatement = statement;

        int length = 100 / sqlFiles.size(); // 进度条的长度

        java.util.List<String> finalSqlFiles = sqlFiles;

        SwingWorker<Void, ProgressBarPage.ProgressData> worker = new SwingWorker<Void, ProgressBarPage.ProgressData>() {
            @Override
            protected Void doInBackground() throws Exception {
                int totalTasks = finalSqlFiles.size();

                for (int i = 0; i < totalTasks; i++) {
                    if(! continueExecute){
                        break;
                    }
                    // 模拟执行任务
                    String sql = "执行：" + finalSqlFiles.get(i);
                    updateProgress(i+1, totalTasks, sql);

                    try {
                        Thread.sleep(500); // 模拟耗时操作
                        ExcuteSql(finalStatement, finalSqlFiles.get(i), finalSqlFiles,fileType);

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                return null;
            }

            @Override
            protected void process(List<ProgressBarPage.ProgressData> chunks) {
                // 更新进度条和SQL信息
                for (ProgressBarPage.ProgressData data : chunks) {
                    ProgressBarPage.setProcess(data.getProgress());
                    ProgressBarPage.setRunningSql(data.getSql() );
                }
            }

            private void updateProgress(int currentTask, int totalTasks, String sql) {
                int progress = (int) ((double) currentTask / totalTasks * 100);
                publish(new ProgressBarPage.ProgressData(progress, sql));
            }

            @Override
            protected void done() {
                // 任务完成后执行操作
                System.out.println("执行完毕");
                ProgressBarPage.setRunningSql("执行完毕");
                continueExecute = true;
            }
        };

        worker.execute();
    }


    private void ExcuteSql(Statement statement, String sqlFilePath, java.util.List<String> SqlFiles,String fileType){

                String result = new String();

                try {  // 捕获文件未取到的 exception
                    result = FileHandle.readFileContent(sqlFilePath,fileType);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if(result.equals("")){
                    JOptionPane.showMessageDialog(null, "文件读取失败", "", JOptionPane.INFORMATION_MESSAGE);
                    this.continueExecute = false;
                    return;
                }

                String[] lines = result.split("\n");
                for (String line : lines) {
                    if(this.continueExecute){
                        try {
                            System.out.println(line);
                            statement.execute(line);
                        } catch (SQLException e) { // 捕获sql异常，提示用用户修改
                            DeBugPage newFrame = new DeBugPage(line,lines,sqlFilePath, SqlFiles,statement,e);

                            this.continueExecute = false;
                            throw new RuntimeException(e);
                        }
                    }else break;
                }
    }


    // 添加测试数据库连接的逻辑方法
    private void testDatabaseConnection(String dbAddress, String dbname,String user, String password,String type) throws SQLException {
//        mysql配置
        dbAddress = "localhost:3306";
        dbname = "sqltool";
        user = "root";
        password = "shangyi";
//        folderPath = "C:\\Users\\Administrator\\Desktop\\test";
        type = "mysql";

//        oracle配置
//        dbAddress = "123";
//        dbname = "123";
//        user = "123";
//        password = "1";
//        String folderPath = "C:\\Users\\Administrator\\Desktop\\test";
//        type = "oracle";

        Connection connection = null;
        if(type == "mysql"){
            String url = "jdbc:" + type +"://"+dbAddress+"/" + dbname;

            System.out.println(url);

            try {
                connection = DriverManager.getConnection(url, user, password);
                JOptionPane.showMessageDialog(null, "连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            String url = "jdbc:" + type +":thin:@"+"//"+dbAddress+"/"+dbname;
            try {

                OracleConnection oracleConnection = new OracleConnection(url,user,password);
                JOptionPane.showMessageDialog(null, "连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }catch (SQLException e) {
                // 捕获数据库连接异常
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
            }

        }

//        boolean SqlConnectCreated =  MysqlConnection.isInstanceCreated();
//
//        try {
//            if(SqlConnectCreated){
//                MysqlConnection.getSqlConnect().setDatabaseParams(url,user,password);
//            }else{
//                MysqlConnection.getSqlConnect(url,dbname,password);
//            }
//            JOptionPane.showMessageDialog(null, "连接成功", "提示", JOptionPane.INFORMATION_MESSAGE);
//        }catch (SQLException e) {
//            // 捕获数据库连接异常
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
//        } catch (Exception e){
//            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
//        }

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




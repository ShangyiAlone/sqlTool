import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeBugPage extends JFrame {
    private static Statement statement;

    public DeBugPage(String line, String[] SqlFile, String sqlFilePath,java.util.List<String> sqlFilePaths,Statement statement) {

        this.statement = statement;

        JTextArea textArea = new JTextArea(
                    "错误文件："+ sqlFilePath+"\n" +
                            "错误语句："+line
        );

        StringBuilder res = new StringBuilder();
        for(String sql : SqlFile){
            res.append(sql);
            res.append("\n");
        }

//        计算问题sql语句位置
        int startIndex = res.indexOf(line.trim());
//        System.out.println(startIndex);


        JTextPane textPane2 = new JTextPane();
        textPane2.setText(res.toString());

//        修改样式，将问题sql进行高亮显示
        // 创建一个样式
        StyleContext styleContext = new StyleContext();
        Style style = styleContext.addStyle("CustomStyle", null);
        StyleConstants.setForeground(style, Color.RED); // 设置文本颜色为红色

        // 创建一个文档
        StyledDocument document = textPane2.getStyledDocument();

        // 设置文档的样式
        document.setCharacterAttributes(startIndex, line.trim().length(), style, true);

        textArea.setEditable(false);
//        textArea2.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
//        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        JScrollPane scrollPane2 = new JScrollPane(textPane2);

//        JPanel panel = new JPanel(new GridLayout(2, 1));
        JPanel panel = new JPanel(new GridBagLayout());

//        设置二者的位置
        GridBagConstraints constraints = new GridBagConstraints();

        // 设置第一个 JScrollPane
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1; // 比例为 1
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0.25; // 比例为 1:3
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, constraints);

        // 设置第二个 JScrollPane
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1; // 比例为 1
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0; // 比例为 1:4
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane2, constraints);


//        添加两个新的按钮
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2; // 一行两列
        constraints.gridheight = 1;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0; // 不分配垂直空间
        constraints.fill = GridBagConstraints.HORIZONTAL; // 水平填充

        JPanel panel2 = new JPanel(new GridLayout(1, 2));
        // 创建保存修改按钮
        JButton saveButton = new JButton("保存修改");
        panel2.add(saveButton);

        // 创建继续执行按钮
        JButton continueButton = new JButton("继续执行");
        panel2.add(continueButton);

        panel.add(panel2,constraints);

        add(panel);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textPane2.getText();
                SaveDate(sqlFilePath,text);
                JOptionPane.showMessageDialog(null, "保存成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String restSql = textPane2.getText().substring(startIndex);

//                String restSql = res.substring(startIndex);

                ContineExecute(restSql,sqlFilePath,sqlFilePaths);


//                SaveDate(sqlFilePath,text);
            }
        });
    }

    private static void SaveDate(String sqlFilePath,String text){
        try {
            // 创建一个 FileWriter 对象来写入文件
            FileWriter writer = new FileWriter(sqlFilePath);

            // 写入文本到文件
            writer.write(text);

            // 关闭文件写入流
            writer.close();

            System.out.println("文本已成功写入文件：" + sqlFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("写入文件时发生错误：" + e.getMessage());
        }
    }

//    还需要添加参数，需要调用主程序的import函数
    private static void ContineExecute(String restSql,String sqlFilePath,java.util.List<String> sqlFilePaths){

        //  标记剩余sql执行过程中是否有错
        boolean allSqlSuccessful = true;

        //  先处理本文件下未执行的sql
        String[] lines = restSql.split("\n");
        for (String line : lines) {
            if(allSqlSuccessful){
                try {
                    System.out.println(line);
                    statement.execute(line);
                } catch (SQLException e) { // 捕获sql异常，提示用用户修改
                    // 重新创建一个debug页面对象
                    e.printStackTrace();
                    allSqlSuccessful = false;
                    DeBugPage newFrame = new DeBugPage(line,lines,sqlFilePath,  sqlFilePaths,statement);
                    break;
                }
            }
        }


//        接下来执行剩余文件的sql语句，（调用主程序的执行函数）
//        先得到当前sql文件的位置的索引
        int index = -1;
        for(int i = 0;i<sqlFilePaths.size();i++){
            System.out.println(sqlFilePaths.get(i));
            if(sqlFilePaths.get(i).equals(sqlFilePath)){
                index = i;
                System.out.println(index);
                System.out.println(sqlFilePath);
                break;
            }
        }

        // 执行出现错误的sql文件后面的所有文件
        ExcuteSql(statement,sqlFilePaths,index);

//        if(allSqlSuccessful){
//            JOptionPane.showMessageDialog(null, "所有SQL执行成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
//        }


    }


//    private static Statement  getSqlStament(String dbAddress,String dbname, String user, String password, String folderPath){
//        dbAddress = "123";
//        dbname = "123";
//        user = "123";
//        password = "123";
//        folderPath = "C:\\Users\\Administrator\\Desktop\\test";
//
//
//        String url = "jdbc:mysql://"+dbAddress+"/"+dbname;
//        boolean SqlConnectCreated =  SqlConnect.isInstanceCreated();
//
//        try {
//            if(SqlConnectCreated){
//                SqlConnect.getSqlConnect().setDatabaseParams(url,user,password);
//            }else{
//                SqlConnect.getSqlConnect(url,dbname,password);
//            }
//
//        }catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "连接失败：" + e.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
//            SqlConnect.getSqlConnect().setConnection(null);
//            // 捕获数据库连接异常
//            e.printStackTrace();
//
//        }
//
//        SqlConnect connection = SqlConnect.getSqlConnect();
//
//        Connection con =  connection.getConnection(); // 调用连接数据库的方法
//
//
//        Statement statement = null; // 创建声明对象
//        try {
//            statement = con.createStatement();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return statement;
//
//    }

    private static void ExcuteSql(Statement statement,java.util.List<String> sqlFiles,int start){

//        这里通过索引，跳过已经执行过的那个sql文件，直接执行下一个
        boolean continueExecute = true;
        for (int i = start+1;i<sqlFiles.size();i++) {
            if(continueExecute){
                String result = new String();
                try {  // 捕获文件未取到的 exception
                    result = FileHandle.readFileContent(sqlFiles.get(i));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

                String[] lines = result.split("\n");
                for (String line : lines) {
                    if(continueExecute){
                        try {
                            statement.execute(line);
                        } catch (SQLException e) { // 捕获sql异常，提示用用户修改
                            continueExecute = false;
                            DeBugPage newFrame = new DeBugPage(line,lines,sqlFiles.get(i), sqlFiles,statement);
                        }
                    }else break;
                }
            }else break;
        }
    }
}


import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class DeBugPage extends JFrame {
    public DeBugPage(String line, String[] SqlFile, String sqlFilePath) {


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




//        JTextArea textArea2 = new JTextArea(
//            res.toString()
//        );

        JTextPane textPane2 = new JTextPane();
        textPane2.setText(res.toString());

//        修改样式，将问题sql进行高亮显示
        // 创建一个样式
        StyleContext styleContext = new StyleContext();
        Style style = styleContext.addStyle("CustomStyle", null);
        StyleConstants.setForeground(style, Color.RED); // 设置文本颜色为红色

        // 创建一个文档
        StyledDocument document = textPane2.getStyledDocument();

        //
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


//        panel.add(scrollPane);
//        panel.add(scrollPane2);

        add(panel);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}


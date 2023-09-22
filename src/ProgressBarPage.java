import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressBarPage extends Frame{

    static ProgressBarPage progressBarPage ;

    static JFrame  frame = new JFrame("进度");
    static JProgressBar progressBar = new JProgressBar(0, 100);

    static JLabel statusLabel = new JLabel("正在执行：");

    private ProgressBarPage(){
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new BorderLayout());

        progressBar.setValue(0);
        progressBar.setStringPainted(true);

//        frame.add(progressBar);
        frame.add(progressBar, BorderLayout.NORTH); // 将进度条放在上方
        frame.add(statusLabel, BorderLayout.CENTER); // 将状态信息放在中间

        // 创建窗口关闭监听器
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                frame.setVisible(true);
            }
        });

        frame.setVisible(true);
    }

    public static void setProcess(int i){
        progressBar.setValue(i);
    }

    public static void setRunningSql(String str){
        statusLabel.setText(str);
    }

    public  static ProgressBarPage getProgressBarPage(){
        return new ProgressBarPage();
    }

    public void close(){
        this.progressBarPage.dispose();
    }

    public static class ProgressData {
        private int progress;
        private String sql;

        public ProgressData(int progress, String sql) {
            this.progress = progress;
            this.sql = sql;
        }

        public int getProgress() {
            return progress;
        }

        public String getSql() {
            return sql;
        }
    }


    public static void main(String[] args) {
        ProgressBarPage progressBarPage = new ProgressBarPage();

    }

}


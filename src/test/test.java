package test;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.FileInputStream;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        try {
            // 读取文件内容
            FileInputStream fis = new FileInputStream("C:\\Users\\33718\\Desktop\\sqlTool\\sqlTool\\src\\test\\test2.py");
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();

            // 创建 UniversalDetector 实例
            UniversalDetector detector = new UniversalDetector(null);

            // 设置文件内容
            detector.handleData(data, 0, data.length);

            // 结束检测
            detector.dataEnd();

            // 获取检测结果
            String encoding = detector.getDetectedCharset();

            // 输出检测结果
            System.out.println("Detected Charset: " + encoding);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

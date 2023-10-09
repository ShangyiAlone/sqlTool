package test;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.FileInputStream;
import java.io.IOException;

// 错误的字符串识别脚本 待改正
public class test {
    public static void main(String[] args) {
        try {
            // 读取文件内容
            FileInputStream fis = new FileInputStream("C:\\Users\\Administrator\\" +
                    "Desktop\\init_unmaned_plane_system-master\\sqlTool\\src\\test\\2.txt");
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

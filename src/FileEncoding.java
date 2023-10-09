import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

// 识别文件编码类型工具类
public class FileEncoding {
    public static Charset detectFileEncoding(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        BufferedInputStream bis = new BufferedInputStream(fis);

        Charset charset = Charset.defaultCharset();
        byte[] buffer = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);

        int bytesRead;
        while ((bytesRead = bis.read(buffer)) != -1) {
            if (detector.isDone()) {
                break;
            }

            detector.handleData(buffer, 0, bytesRead);
        }

        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        if (encoding != null) {
            charset = Charset.forName(encoding);
        }

        detector.reset();
        bis.close();
        fis.close();

        return charset;
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Administrator\\" +
                "Desktop\\init_unmaned_plane_system-master\\sqlTool\\src\\test\\2.txt";

        try {
            Charset fileEncoding = detectFileEncoding(filePath);
            System.out.println("File Encoding: " + fileEncoding.name());
        } catch (IOException e) {
            System.out.println("Error occurred while detecting file encoding: " + e.getMessage());
        }
    }
}

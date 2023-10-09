import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class FileHandle {
    public static void main(String[] args) throws FileNotFoundException {
        String folderPath = "C:\\Users\\Administrator\\Desktop\\init_unmaned_plane_system-master\\sqlTool\\src\\FileHandle.java"; // 替换为实际的文件夹路径
        String result = readFileContent(folderPath,"utf-8");
//        System.out.println(result);
    }

    public static List<Integer> getAllSql(String folderPath){
        File folder = new File(folderPath);

        List<String> sqlFiles = findSqlFiles(folder);

        StringBuilder builder = new StringBuilder();
        for (String sqlFilePath : sqlFiles) {
            String result = new String();
            try {
                result =  readFileContent(sqlFilePath,"UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            builder.append(result);

        }

        return new ArrayList<Integer>();
    }

    public static String readFileContent(String filePath,String type) throws FileNotFoundException {
        String encoding = type;
        try {
            Charset fileEncoding = FileEncoding.detectFileEncoding(filePath);
            System.out.println("File Encoding: " + fileEncoding.name());
            encoding = fileEncoding.name();
        } catch (IOException e) {
            System.out.println("Error occurred while detecting file encoding: " + e.getMessage());
        }

        StringBuilder content = new StringBuilder();
        FileReader fileHandle = new FileReader(filePath);

//        使用系统默认的编码格式读取
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)) ) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                content.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), encoding))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理每行文本
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

//  指定格式写入文本
    public static void SaveDate(String sqlFilePath,String text,String type){
//        try {
//            // 创建一个 FileWriter 对象来写入文件
//            FileWriter writer = new FileWriter(sqlFilePath);
//
//            // 写入文本到文件
//            writer.write(text);
//
//            // 关闭文件写入流
//            writer.close();
//
//            System.out.println("文本已成功写入文件：" + sqlFilePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("写入文件时发生错误：" + e.getMessage());
//        }
        try {
            // 指定文件编码为UTF-8
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sqlFilePath), type));

            // 写入文本到文件
            writer.write(text);

            // 关闭文件写入流
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("写入文件时发生错误：" + e.getMessage());
        }


    }


    public static List<String> findSqlFiles(File folder) {
        List<String> sqlFilesList = new ArrayList<>();

        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".sql");
            }
        });

        if (files != null) {
            for (File file : files) {
                // 处理SQL文件，例如读取文件内容
                String filePath = file.getAbsolutePath();
                sqlFilesList.add(filePath);

                // 在这里可以使用文件读取方法来读取SQL文件的内容
            }
        }

        // 递归处理子文件夹
        File[] subfolders = folder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });

        if (subfolders != null) {
            for (File subfolder : subfolders) {
                sqlFilesList.addAll(findSqlFiles(subfolder));
            }
        }

        return sqlFilesList;
    }



}

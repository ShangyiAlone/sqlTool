import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {
    public static void main(String[] args) {
        String folderPath = "C:\\Users\\Administrator\\Desktop\\test"; // 替换为实际的文件夹路径

//        String result =  getAllSql(folderPath);
//        System.out.println(result);
//        File folder = new File(folderPath);
//
//        List<String> sqlFiles = findSqlFiles(folder);
//        for (String sqlFilePath : sqlFiles) {
//
//            String result = new String();
//            try {
//                result =  readFileContent(sqlFilePath);
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.print(result);
//
//            // 处理每个 SQL 文件
////            try {
////                // 创建一个FileReader对象来读取文件
////                FileReader fileReader = new FileReader(sqlFilePath);
////
////                // 创建一个BufferedReader来缓冲读取
////                BufferedReader bufferedReader = new BufferedReader(fileReader);
////
////                String line;
////
////                // 逐行读取文件内容并打印
////                while ((line = bufferedReader.readLine()) != null) {
////                    System.out.println(line);
////                }
////
////                // 关闭资源
////                bufferedReader.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//
//        }

    }

    public static List<Integer> getAllSql(String folderPath){
        File folder = new File(folderPath);

        List<String> sqlFiles = findSqlFiles(folder);

        StringBuilder builder = new StringBuilder();
        for (String sqlFilePath : sqlFiles) {
            String result = new String();
            try {
                result =  readFileContent(sqlFilePath);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

//            System.out.print(result);
            builder.append(result);

        }

        return new ArrayList<Integer>();
    }


    public static String readFileContent(String filePath) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        FileReader fileHandle = new FileReader(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
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
//                System.out.println("SQL文件名：" + filePath);
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

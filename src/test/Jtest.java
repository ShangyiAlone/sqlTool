package test;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

public class Jtest {
    public static void main(String[] args) {
        String pythonCommand = "C:\\Users\\33718\\AppData\\Local\\Programs\\Python\\Python311\\python.exe";
        String path  = "C:\\Users\\33718\\Desktop\\sqlTool\\sqlTool\\src\\test\\test2.py" ;
        // TODO Auto-generated method stub
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(pythonCommand+" "+ path );// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}



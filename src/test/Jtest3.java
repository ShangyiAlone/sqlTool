package test;

import org.python.util.PythonInterpreter;

// 直接在Java程序中植入python代码
public class Jtest3 {
    public static void main(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("a=[10,8,3,1,7,0]; ");
        interpreter.exec("print(sorted(a));"); //此处python语句是3.x版本的语法

    }
}



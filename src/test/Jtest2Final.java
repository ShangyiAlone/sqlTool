package test;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.IOException;

// 调用脚本中的函数，支持传参
public class Jtest2Final {
    public static void main(String[] args) throws IOException, InterruptedException {

        // TODO Auto-generated method stub
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("C:\\Users\\33718\\Desktop\\sqlTool\\sqlTool\\src\\test\\test.py");

        // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
        PyFunction pyFunction = interpreter.get("add", PyFunction.class);
        int a = 5, b = 10;
        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
        PyObject pyobj = pyFunction.__call__(new PyInteger(a), new PyInteger(b));
        System.out.println("the anwser is: " + pyobj);

    }
}

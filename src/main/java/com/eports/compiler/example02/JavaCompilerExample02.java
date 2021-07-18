package com.eports.compiler.example02;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 动态编译示例
 * 外部获取java 文件编译成class 文件
 *
 * @author Lin
 */
public class JavaCompilerExample02 {


    public static void main(String[] args) {
        execute();
    }

    private static void execute(){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> javaFileObjectsFromFiles = standardFileManager.getJavaFileObjectsFromFiles(Arrays.asList(getTest1File()));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        //-classpath 用来指定搜索类文件(*.class文件)的路径
        //-sourcepath 用来指定搜索源文件(*.java文件)的路径
        //-d 指定放置生成的类文件的位置
        List<String> classPath = Arrays.asList("-d","target\\classes\\com\\eports\\compiler");

        // 可批量
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,null,diagnostics,classPath,null,javaFileObjectsFromFiles);
        // 执行编译
        task.call();

        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){
            System.out.println(diagnostic);
        }
    }


    private static File getTest1File(){
        File file = new File("E:\\Test1.java");
        return file;
    }

}

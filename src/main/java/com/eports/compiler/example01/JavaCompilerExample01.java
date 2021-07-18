package com.eports.compiler.example01;

import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 动态编译示例
 * 将 字符串 java 代码编译成class 文件
 *
 * @author Lin
 */
public class JavaCompilerExample01 {


    public static void main(String[] args) {
        execute();
    }

    private static void execute(){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        // 编译后的诊断信息
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        // JavaFileObject 对象
        List<StringJavaFileObj> stringJavaFileObjs = Arrays.asList(new StringJavaFileObj("Test", generateTestCode()));

        //-classpath 用来指定搜索类文件(*.class文件)的路径
        //-sourcepath 用来指定搜索源文件(*.java文件)的路径
        //-d 指定放置生成的类文件的位置
        List<String> classPath = Arrays.asList("-d","target\\classes\\com\\eports\\compiler");

        // 可批量
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,null,diagnostics,classPath,null,stringJavaFileObjs);
        // 执行编译
        task.call();

        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){
            System.out.println(diagnostic);
        }
    }


    static class StringJavaFileObj extends SimpleJavaFileObject {

        /**
         *  代码内容
         */
        private String code;


        /**
         * StringJavaFileObj 构造方法
         * @param fileName 文件名
         * @param code 代码内容
         */
        protected StringJavaFileObj(String fileName, String code) {
            super(URI.create("string:///" + fileName.replace('.','/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.code;
        }

    }


    /**
     * 生成 测试类 代码
     * @return
     */
    private static String generateTestCode(){
        return " class Test{\n" +
                "        public static void main(String[] args) {\n" +
                "            System.out.println(\"hello!\");\n" +
                "        }\n" +
                "    }";
    }

}

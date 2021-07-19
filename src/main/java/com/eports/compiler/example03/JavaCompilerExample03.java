package com.eports.compiler.example03;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 动态编译示例
 * 字符串 java 代码 编译至内存
 *
 * @author Lin
 */
public class JavaCompilerExample03 {
    private Map<String,OutClassFileObj> classFileObjMap  = new HashMap<String,OutClassFileObj>();

    public static void main(String[] args) {
        JavaCompilerExample03 javaCompilerExample03 = new JavaCompilerExample03();
        javaCompilerExample03.execute("People",javaCompilerExample03.generatePeopleCode());
    }

    private void execute(String className,String code){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        ExtendedStandardJavaFileManager javaFileManager = new ExtendedStandardJavaFileManager(javaCompiler.getStandardFileManager(null, null, null),classFileObjMap);

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        List<SourceFileObj> people = Arrays.asList(new SourceFileObj(className,code));

        // 可批量
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,javaFileManager,diagnostics,null,null,people);
        // 执行编译
        task.call();

        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){
            System.out.println(diagnostic);
        }

        ByteClassLoader byteClassLoader = new ByteClassLoader(this.getClass().getClassLoader(),className,classFileObjMap.get(className).getBytes());
        try {
            Class<?> aClass = byteClassLoader.loadClass(className);
            Method out = aClass.getMethod("out");
            Object o = aClass.getDeclaredConstructor().newInstance();
            out.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generatePeopleCode(){
        return " public class People {\n" +
                "        public void out(){\n" +
                "            System.out.println(\"你是真的狗\");\n" +
                "        }\n" +
                "    }";
    }

}

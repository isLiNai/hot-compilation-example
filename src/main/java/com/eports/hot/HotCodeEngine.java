package com.eports.hot;

import javax.tools.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:24
 */
public class HotCodeEngine {
    private File javaFile;
    private String outPath;
//    String classesPath=
    public HotCodeEngine(File javaFile) {
        this.javaFile = javaFile;
        String[] split = javaFile.getPath().split("src\\\\main\\\\java");
        this.outPath = "target\\classes\\" +  split[1].substring(1).substring(0, split[1].lastIndexOf("\\"));
    }

    public void execute(){
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> javaFileObjectsFromFiles = standardFileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

        //-classpath 用来指定搜索类文件(*.class文件)的路径
        //-sourcepath 用来指定搜索源文件(*.java文件)的路径
        //-d 指定放置生成的类文件的位置
        List<String> classPath = Arrays.asList("-d",outPath);

        // 可批量
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,null,diagnostics,classPath,null,javaFileObjectsFromFiles);
        // 执行编译
        task.call();

        for ( Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()){
            System.out.println(diagnostic);
        }
        try {
            ByteBuffer byteBuffer = readClassFile(outPath + "/" + javaFile.getName().replace(".java",".class"));
            HotClassLoader hotClassLoader = new HotClassLoader(this.getClass().getClassLoader(),byteBuffer);
            Class<?> aClass = hotClassLoader.loadClass(javaFile.getName());
            System.out.println(aClass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer readClassFile(String classFilePath) throws IOException {
        File file = new File(classFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        byteBuffer.flip();
        return byteBuffer;
    }


}

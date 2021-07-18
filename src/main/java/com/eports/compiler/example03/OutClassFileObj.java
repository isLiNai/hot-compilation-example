package com.eports.compiler.example03;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * 输出的 类文件 对象
 * @author Lin
 */
public class OutClassFileObj extends SimpleJavaFileObject {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    public OutClassFileObj(String className) {
        super(URI.create("string:///" + className.replace('.','/') + Kind.CLASS.extension), Kind.CLASS);
    }

    /**
     * 采取我们自己定义的输出流
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return byteArrayOutputStream;
    }


    public byte[] getBytes(){
        return byteArrayOutputStream.toByteArray();
    }

}

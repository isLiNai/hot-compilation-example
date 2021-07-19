package com.eports.hot;

import java.nio.ByteBuffer;

/**
 * 热加载自定义类加载器
 *
 * @author lin
 * @version 1.0
 * @date 2021/7/19 11:37
 */
public class HotClassLoader extends ClassLoader{
    private ByteBuffer byteBuffer;

    public HotClassLoader(ClassLoader parent, ByteBuffer byteBuffer) {
        super(parent);
        this.byteBuffer = byteBuffer;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return defineClass(name,byteBuffer,null);
    }
}

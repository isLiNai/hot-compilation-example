package com.eports.compiler.example03;

/**
 * @author Lin
 */
public class ByteClassLoader extends ClassLoader{

    private String className;

    private byte[] classBytes;

    public ByteClassLoader(ClassLoader parent, String className, byte[] classBytes) {
        super(parent);
        this.className = className;
        this.classBytes = classBytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return defineClass(className,classBytes,0,classBytes.length);
    }
}

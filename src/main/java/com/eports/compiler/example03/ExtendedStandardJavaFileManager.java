package com.eports.compiler.example03;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Map;

/**
 * @author Lin
 */
public class ExtendedStandardJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private Map<String,OutClassFileObj> classFileObjMap;

    /**
     * Creates a new instance of ForwardingJavaFileManager.
     *
     * @param fileManager delegate to this file manager
     */
    public ExtendedStandardJavaFileManager(JavaFileManager fileManager, Map<String, OutClassFileObj> classFileObjMap) {
        super(fileManager);
        this.classFileObjMap = classFileObjMap;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        OutClassFileObj outClassFileObj = new OutClassFileObj(className);
        classFileObjMap.put(className,outClassFileObj);
        return outClassFileObj;
    }
}

package com.eports.compiler.example03;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @author Lin
 */
public class SourceFileObj extends SimpleJavaFileObject {

    private String code;

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     */
    public SourceFileObj(String className,String code) {
        super(URI.create("string:///" + className.replace('.','/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.code = code;
    }


    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.code;
    }
}

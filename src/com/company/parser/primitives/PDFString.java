package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.InputStream;

/**
 * Created by val on 02/10/16.
 */
public class PDFString implements PDFObject {
    String string;

    @Override
    public PDFObject objectWithData(InputStream bytes) {
        return null;
    }

    public String toString() {
        return string;
    }
}

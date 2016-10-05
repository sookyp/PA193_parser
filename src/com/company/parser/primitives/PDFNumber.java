package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.InputStream;

import static java.lang.String.*;

/**
 * Created by val on 02/10/16.
 */
public class PDFNumber implements PDFObject {
    double value;

    @Override
    public PDFObject objectWithData(InputStream bytes) {
        return null;
    }

    public String toString() {
        return valueOf(value);
    }
}
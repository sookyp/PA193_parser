package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.InputStream;

/**
 * Created by val on 02/10/16.
 */
interface PDFObject {
    public PDFObject objectWithData(InputStream bytes);
}

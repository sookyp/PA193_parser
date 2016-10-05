package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.InputStream;

/**
 * Created by val on 02/10/16.
 */
public class PDFArray implements PDFObject {
    PDFObject[] objects;

    @Override
    public PDFObject objectWithData(InputStream bytes) {
        return null;
    }

    public PDFObject objectAtIndex(int index){
        if(index < 0 || index >= objects.length) {
            return null;
        }
        return objects[index];
    }
}
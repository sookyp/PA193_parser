package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;

/**
 * Created by val on 02/10/16.
 */
public class PDFIndirectObject extends PDFObject {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
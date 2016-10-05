package com.company.parser.primitives;

import java.io.InputStream;

/**
 * Created by val on 05/10/16.
 */
public class PDFBoolean implements PDFObject{
    boolean value;

    @Override
    public PDFObject objectWithData(InputStream bytes) {
        return null;
    }

    public String toString() {
        return String.valueOf(value);
    }
}

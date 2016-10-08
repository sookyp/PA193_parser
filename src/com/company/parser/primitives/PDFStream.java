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
public class PDFStream extends PDFObject {
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    static public PDFStream parseStream(CharSequence bytes) throws IOException, InvalidException {
        String regex = "^\\s*stream\\s*(.*?)\\s*endstream";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(bytes);

        while (matcher.find()) {
            PDFStream stream = new PDFStream();
            stream.setValue(matcher.group());
            return stream;
        }
        return null;
    }
}
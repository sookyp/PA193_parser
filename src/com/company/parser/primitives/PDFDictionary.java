package com.company.parser.primitives;

//import sun.jvm.hotspot.runtime.Bytes;
import java.io.InputStream;
import java.util.*;
/**
 * Created by val on 02/10/16.
 */
public class PDFDictionary implements PDFObject {
    private static final int NOT_FOUND = Integer.MAX_VALUE;

    Map<String, PDFObject> map = new HashMap<String, PDFObject>();

    String[] keys;
    PDFObject[] values;


    public PDFObject objectWithData(InputStream bytes) {
        return null;
    }

    public boolean setObjectForKey(PDFObject object, String key){
        try{
            map.put(key, object);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public PDFObject objectForKey(String key){
        try{
            return map.get(key);
        } catch (Exception e){
            return null;
        }
    }
}
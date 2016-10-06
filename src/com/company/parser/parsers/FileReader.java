package com.company.parser.parsers;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by val on 05/10/16.
 */
public class FileReader {
    public List<String> read(Path path, String startDelimiter, String endDelimiter) throws IOException {
        Boolean record = Boolean.FALSE;
        String parsedString = new String();

        BufferedReader br = this.readerForPath(path);
        try {
            String line = br.readLine();

            while (line != null) {
                if(line.contains(startDelimiter) || record.equals(Boolean.TRUE)){
                    record = Boolean.TRUE;
                    parsedString = parsedString.concat(line);
                }
                if(line.contains(endDelimiter)){
                    record = Boolean.FALSE;
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        parsedString = parsedString.replace(endDelimiter, "");
        List<String> metadata = Arrays.asList(parsedString.split(startDelimiter));

        return metadata;
    }

    private BufferedReader readerForPath(Path path){
        try{
            FileInputStream is = new FileInputStream(new File(path.toString()));
            BufferedReader br = new BufferedReader(new InputStreamReader((is)));
            return br;
        } catch(Exception e){
            return null;
        }
    }
}

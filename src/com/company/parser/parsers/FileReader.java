package com.company.parser.parsers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Created by val on 05/10/16.
 */
public class FileReader {
    public String read(Path path, String startDelimiter, String endDelimiter) throws IOException {
        Boolean record = Boolean.FALSE;
        StringBuilder parsed = new StringBuilder();
        String parsedString = new String();

        BufferedReader br = this.readerForPath(path);
        try {
            String line = br.readLine();

            while (line != null) {
                if(line.contains(startDelimiter) || record.equals(Boolean.TRUE)){
                    record = Boolean.TRUE;
                    parsed.append(line);
                }
                if(line.contains(endDelimiter)){
                    record = Boolean.FALSE;
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        parsedString = parsed.toString().replace(endDelimiter, "");

        return parsedString;
    }

    public String read(Path path){
        try {
            RandomAccessFile file = new RandomAccessFile(new File(path.toUri()), "r");
            byte[] byteArray = new byte[(int) file.length()];
            file.readFully(byteArray);
            String content = new String(byteArray, StandardCharsets.US_ASCII);
            return content;
        } catch(Exception e){
            return null;
        }
    }

    private BufferedReader readerForPath(Path path){
        try{
            FileInputStream is = new FileInputStream(new File(path.toString()));
            BufferedReader br = new BufferedReader(new InputStreamReader((is), StandardCharsets.US_ASCII));
            return br;
        } catch(Exception e){
            return null;
        }
    }
}

package com.company.parser.parsers;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by val on 05/10/16.
 */
public class FileReader {
    public List<String> read(Path path, String startDelimiter, String endDelimiter) throws IOException {
        Boolean record = Boolean.FALSE;
        List<String> metadata = new ArrayList<>();

        BufferedReader br = this.readerForPath(path);
        try {
            String line = br.readLine();

            while (line != null) {
                if(line.contains(startDelimiter) || record.equals(Boolean.TRUE)){
                    record = Boolean.TRUE;
                    metadata.add(line);
                }
                if(line.contains(endDelimiter)){
                    record = Boolean.FALSE;
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
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

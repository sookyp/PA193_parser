package com.company.parser.parsers;

import java.nio.file.*;
import com.company.parser.supporting.files.DocumentMetadata;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by val on 02/10/16.
 */
public class MetadataParser extends Parser {

    public MetadataParser(Path path){
        super(path);
    }

    public DocumentMetadata parseMetadata(){
        return new DocumentMetadata();
    }
   
    
    public void read(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader((is)));

        try {
            String line = br.readLine();

            while (line != null) {
                // I'll commit later
                line = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
    }    
    
    public void read(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            this.read(fis);
        }
    }

}

package com.company.parser.parsers;

import java.nio.file.*;
import com.company.parser.supporting.files.DocumentMetadata;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by val on 02/10/16.
 */
public class MetadataParser extends Parser {
    
    private static final String METADATA_BEGIN = "<?xpacket begin";
    private static final String METADATA_END = "<?xpacket end";

    public MetadataParser(Path path){
        super(path);
    }

    public DocumentMetadata parseMetadata(){
        return new DocumentMetadata();
    }
   
    
    public List<String> read(InputStream is) throws IOException {
        Boolean record = Boolean.FALSE;
        List<String> metadata = new ArrayList<>();
        
        BufferedReader br = new BufferedReader(new InputStreamReader((is)));

        try {
            String line = br.readLine();

           while (line != null) {
               if(line.contains(METADATA_BEGIN) || record.equals(Boolean.TRUE)){
                   record = Boolean.TRUE;
                   metadata.add(line);
               }
               if(line.contains(METADATA_END)){
                   record = Boolean.FALSE;
               }
               line = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException(e);
        }
        return metadata;
    }    
    
    public List<String> read(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return this.read(fis);
        }
    }

}

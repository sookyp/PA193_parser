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

    public DocumentMetadata parseMetadata() {
        try{
            List<String> metadata = this.parseMetadataFromFile();
            return new DocumentMetadata();
        } catch(Exception e){
            return null;
        }
    }

    private List<String> parseMetadataFromFile() throws IOException{
        return new FileReader().read(this.getPath(), METADATA_BEGIN, METADATA_END);
    }
}

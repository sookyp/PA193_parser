package com.company.parser.parsers;

import java.nio.file.*;
import com.company.parser.supporting.files.DocumentMetadata;

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
}

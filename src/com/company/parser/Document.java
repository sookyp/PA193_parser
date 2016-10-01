package com.company.parser;
import com.company.File;
import com.company.parser.parsers.MetadataParser;

import java.nio.file.*;

/**
 * Created by val on 01/10/16.
 */
public class Document extends File {

    DocumentMetadata metadata;

    public Document(Path path) {
        super(path);
        this.parseDocument();
    }

    private void parseDocument(){
        this.parseMetadata();
        this.parseForms();
    }

    private void parseForms(){
        // do stuff
    }

    private void parseMetadata(){
        this.metadata = new MetadataParser().parseMetadata(this.getPath());
    }

    public DocumentMetadata metadata(){
        return this.metadata;
    }

}

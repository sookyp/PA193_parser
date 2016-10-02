package com.company.parser.parsers;

import com.company.parser.Document;

import java.nio.file.Path;

/**
 * Created by val on 02/10/16.
 */
public class DocumentParser extends Parser {
    public DocumentParser(Path path){
        super(path);
    }

    public Document parseDocument(){
        return new Document( this.getPath() );
    }
}

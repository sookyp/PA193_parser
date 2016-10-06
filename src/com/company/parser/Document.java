package com.company.parser;
import com.company.File;
import com.company.parser.parsers.IndirectObjectParser;
import com.company.parser.parsers.PagesParser;
import com.company.parser.parsers.MetadataParser;
import com.company.parser.supporting.files.DocumentCatalog;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.*;

/**
 * Created by val on 01/10/16.
 */
public class Document extends File {

    DocumentMetadata metadata;
    DocumentCatalog catalog;

    public Document(Path path) {
        super(path);
        this.parseDocument();
    }

    public DocumentMetadata getMetadata(){
        return this.metadata;
    }

    private void parseDocument(){
        try {
            this.parseMetadata();
        } catch(Exception e){
            System.out.println("An error occured while parsing metadata");
        }

        try {
            this.parseCatalog();
        } catch(Exception e){
            System.out.println("An error occured while parsing pages of the pdf");
        }

        this.tryToParseIndirectObject("32 0 R");
    }

    private void tryToParseIndirectObject(String objId){
        System.out.print((new IndirectObjectParser(this.getPath())).parseIndirectObject(objId));
    }


    private void parseCatalog() throws Exception{
        this.catalog = new PagesParser(this.getPath()).parsePages();
    }

    private void parseMetadata(){
        this.metadata = new MetadataParser(this.getPath()).parseMetadata();
    }
}

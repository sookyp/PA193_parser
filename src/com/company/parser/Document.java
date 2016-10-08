package com.company.parser;
import com.company.File;
import com.company.parser.parsers.PagesParser;
import com.company.parser.parsers.MetadataParser;
import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.*;
import java.util.List;

/**
 * Created by val on 01/10/16.
 */
public class Document extends File {

    DocumentMetadata metadata;
    List<PDFPage> pages;

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
            this.parsePages();
        } catch(Exception e){
            System.out.println("An error occured while parsing pages of the pdf");
        }
    }

    private void parsePages() throws Exception{
        this.pages = new PagesParser(this.getPath()).parsePages();
    }

    private void parseMetadata(){
        this.metadata = new MetadataParser(this.getPath()).parseMetadata();
    }
}

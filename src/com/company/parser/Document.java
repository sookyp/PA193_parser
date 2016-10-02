package com.company.parser;
import com.company.File;
import com.company.parser.parsers.CatalogParser;
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

    private void parseDocument(){
        this.parseMetadata();
        this.parseCatalog();
    }

    private void parseCatalog(){ this.catalog = new CatalogParser(this.getPath()).parseCatalog(); }

    private void parseMetadata(){
        this.metadata = new MetadataParser(this.getPath()).parseMetadata();
    }

    public DocumentMetadata metadata(){
        return this.metadata;
    }

}

package com.company.parser.parsers;

import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentCatalog;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.Path;

/**
 * Created by val on 02/10/16.
 */
public class CatalogParser extends Parser{

    public CatalogParser(Path path){
        super(path);
    }

    public DocumentCatalog parseCatalog(){
        PDFPage[] pages = {};
        return new DocumentCatalog( pages );
    }
}

package com.company.parser.parsers;

import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentCatalog;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.Path;

/**
 * Created by val on 02/10/16.
 */
public class PagesParser extends Parser{

    public PagesParser(Path path){
        super(path);
    }

    public DocumentCatalog parsePages(){
        PDFPage[] pages = {};
        return new DocumentCatalog( pages );
    }


}
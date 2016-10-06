package com.company.parser.parsers;

import com.company.parser.primitives.PDFDictionary;
import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentCatalog;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by val on 02/10/16.
 */
public class PagesParser extends Parser{

    public PagesParser(Path path){
        super(path);
    }

    public DocumentCatalog parsePages() throws Exception{
        PDFDictionary trailer = new TrailerParser(getPath()).parseTrailer();
        PDFPage[] pages = {};
        return new DocumentCatalog( pages );
    }
}

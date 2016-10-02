package com.company.parser.supporting.files;

import com.company.parser.primitives.PDFPage;

import java.nio.file.Path;

/**
 * Created by val on 02/10/16.
 */
public class DocumentCatalog {

    PDFPage[] pages;

    public DocumentCatalog(PDFPage[] pages) {
        this.pages = pages;
    }

    public PDFPage pageForIndex(int index) {
        return pages[index];
    }

    public int numberOfPages(){
        return pages.length;
    }
}

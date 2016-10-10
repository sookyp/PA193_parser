package com.company.parser.primitives;

/**
 * Created by val on 09/10/16.
 */
public class PDFPage {
    private PDFStream stream;
    private int pageIndex;

    public PDFStream getStream(){
        return this.stream;
    }

    public PDFPage(int pageIndex, PDFStream stream){
        this.pageIndex = pageIndex;
        this.stream = stream;
    }
}

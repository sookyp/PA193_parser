package com.company.parser;

import java.nio.file.*;

/**
 * Created by val on 01/10/16.
 */
public class DocumentLoader {
    public Path pathForTestPdf(){
        return FileSystems.getDefault().getPath("Sample Documents", "Standards_keylength_PKI_2014.pdf");
    }
}

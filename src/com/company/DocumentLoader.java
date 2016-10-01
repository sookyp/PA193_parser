package com.company;

import java.nio.file.*;

/**
 * Created by val on 01/10/16.
 */
class DocumentLoader {
    public Path pathForTestPdf(){
        return FileSystems.getDefault().getPath("Sample Documents", "Sample Form.pdf");
    }
}

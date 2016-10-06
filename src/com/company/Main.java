package com.company;

import com.company.parser.Document;
import com.company.parser.*;
import com.company.parser.parsers.IndirectObjectParser;

public class Main {

    public static void main(String[] args) {
        DocumentLoader loader = new DocumentLoader();
        Document documentToParse = new Document( loader.pathForTestPdf() );

        documentToParse.printFileInfo();

    }
}

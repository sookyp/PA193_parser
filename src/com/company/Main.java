package com.company;

import com.company.parser.Document;
import com.company.parser.*;
import com.company.parser.parsers.DocumentParser;

public class Main {

    public static void main(String[] args) {
        DocumentLoader loader = new DocumentLoader();
        Document documentToParse = new DocumentParser(loader.pathForTestPdf()).parseDocument();

        documentToParse.printFileInfo();
    }
}

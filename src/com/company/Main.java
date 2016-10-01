package com.company;

public class Main {

    public static void main(String[] args) {
        DocumentLoader loader = new DocumentLoader();
        
        Document documentToParse = new Document( loader.pathForTestPdf() );

        documentToParse.printFileInfo();
    }
}

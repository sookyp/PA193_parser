package com.company.parser.supporting.files;

public class DocumentMetadata {

    int numberOfPages = 0;

    public DocumentMetadata() {

    }

    public DocumentMetadata(int numberOfPages) {

    }

    public int getNumberOfPages(){
        return this.numberOfPages;
    }

    public String getAuthor(){
        return "";
    }
    // TODO: other metadata
}


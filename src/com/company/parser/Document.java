package com.company.parser;
import com.company.File;
import com.company.parser.parsers.PagesParser;
import com.company.parser.parsers.MetadataParser;
import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentMetadata;
import com.company.parser.supporting.files.PDFVerification;

import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by val on 01/10/16.
 */
public class Document extends File {

    DocumentMetadata metadata;
    List<PDFPage> pages;

    public Document(Path path) {
        super(path);
    }

    public DocumentMetadata getMetadata(){
        return this.metadata;
    }

    public boolean parseDocument(){
        PDFVerification verificate = new PDFVerification(this.getPath());
        if(!verificate.verifyPDF()) {
            System.out.println("Given pdf is invalid");
            return false;
        }
        
        try {
            this.parsePages();
        } catch(Exception e){
            System.out.println("An error occured while parsing pages of the pdf");
            return false;
        }

        try {
            this.parseMetadata();
            metadata.setNumberOfPages(this.pages.size());
        } catch(Exception e){
            System.out.println("An error occured while parsing metadata");
            return false;
        }
        if(!this.writeOutput()){
            return false;
        }
        return true;
    }

    private void parsePages() throws Exception{
        this.pages = new PagesParser(this.getPath()).parsePages();
    }

    private void parseMetadata(){
        this.metadata = new MetadataParser(this.getPath()).parseMetadata();
    }

    private boolean writeOutput(){
        String json = null;

        String streamsJson = "";
        String metadataJson = "";

        try {
            List<String> streams = new ArrayList<String>();
            for (int index = 0; index < this.pages.size(); index++) {
                if (this.pages.get(index).getStream() != null) {
                    streams.add("\"" + this.pages.get(index).getStream().getValue() + "\"");
                }
            }

            streamsJson = "[";
            int size = streams.size();
            for (int index = 0; index < size; index++) {
                streamsJson += streams.get(index);
                if(index != (size - 1)) {
                    streamsJson += ",";
                }
            }
            streamsJson += "]";
        } catch (Exception e) {
            System.err.println("Error occurred while serializing streams data");
            return false;
        }

        try {
            metadataJson = this.getMetadata().getJSON();
        } catch (Exception e) {
            System.err.println("Error occurred while serializing metadata");
            return false;
        }

        String contents = "";
        
        if(metadataJson.length() > 0) {
            contents = "{\"metadata\":" + metadataJson + ",\"streams\":" + streamsJson + "}";
        } else {
            contents = "{\"streams\":" + streamsJson + "}";
        }
        
        if(!this.writeFile(contents)) {
            return false;
        }
        return true;
    }

    private boolean writeFile(String contents){
        try {
            String fileName = "output.json";
            PrintWriter file_writer = new PrintWriter(fileName, "UTF-8");
            file_writer.println(contents);
            file_writer.close();
            System.err.println("Successfully written to " + fileName);
            return true;
        } catch(Exception e){
            System.err.println("Error occurred while writing the output");
            return false;
        }
    }
}

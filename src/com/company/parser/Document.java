package com.company.parser;
import com.company.File;
import com.company.parser.parsers.PagesParser;
import com.company.parser.parsers.MetadataParser;
import com.company.parser.primitives.PDFPage;
import com.company.parser.supporting.files.DocumentMetadata;

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
        this.parseDocument();
    }

    public DocumentMetadata getMetadata(){
        return this.metadata;
    }

    private void parseDocument(){
        try {
            this.parsePages();
        } catch(Exception e){
            System.out.println("An error occured while parsing pages of the pdf");
        }

        try {
            this.parseMetadata();
            metadata.setNumberOfPages(this.pages.size());
        } catch(Exception e){
            System.out.println("An error occured while parsing metadata");
        }
    }

    private void parsePages() throws Exception{
        this.pages = new PagesParser(this.getPath()).parsePages();
    }

    private void parseMetadata(){
        this.metadata = new MetadataParser(this.getPath()).parseMetadata();
    }

    public void serializeToFile(){
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
            System.err.println("Error occurred while serializing");
        }

        try {
            metadataJson = this.getMetadata().getJSON();
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("Error occurred while serializing");
        }

        if(metadataJson.length() > 0) {
            this.writeFile("{\"metadata\":" + metadataJson + ",\"streams\":" + streamsJson + "}");
        } else {
            this.writeFile("{\"streams\":" + streamsJson + "}");
        }

    }

    private void writeFile(String contents){
        try {
            String fileName = "output.json";
            PrintWriter file_writer = new PrintWriter(fileName, "UTF-8");
            file_writer.println(contents);
            file_writer.close();
            System.err.println("Successfully written to " + fileName);
        } catch(Exception e){
            System.err.println("Error occurred while writing the output");
        }
    }
}

package com.company;


import com.company.parser.Document;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Main {
	public static void main(String[] args) {
            if (args.length > 0){
                Path path = FileSystems.getDefault().getPath(args[0]);    
                Document document = new Document(path);
                document.parseDocument();
            }	
	}        
    }

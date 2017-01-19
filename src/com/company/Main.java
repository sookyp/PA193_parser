package com.company;

import java.util.ArrayList;
import java.util.List;

import com.company.parser.Document;
import com.company.parser.parsers.PagesParser;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
            if (args.length > 0){
                Path path = FileSystems.getDefault().getPath(args[0]);    
                Document document = new Document(path);
                document.parseDocument();
            }	
	}
        
    }

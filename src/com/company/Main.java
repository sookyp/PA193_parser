package com.company;

import java.util.ArrayList;
import java.util.List;

import com.company.parser.Document;
import com.company.parser.DocumentLoader;
import com.company.parser.parsers.PagesParser;

public class Main {

	public static void main(String[] args) {
		DocumentLoader loader = new DocumentLoader();
		Document document = new Document(loader.pathForTestPdf());
		document.serializeToFile();
	}
}

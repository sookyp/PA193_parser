package com.company;

import java.util.ArrayList;
import java.util.List;

import com.company.parser.Document;
import com.company.parser.DocumentLoader;
import com.company.parser.parsers.PagesParser;

public class Main {

	public static void main(String[] args) {
		DocumentLoader loader = new DocumentLoader();
		Document documentToParse = new Document(loader.pathForTestPdf());

		String json = null;
		PagesParser parser = new PagesParser(documentToParse.getPath());
		List<String> streams = new ArrayList<String>();
		try {
			json = documentToParse.getMetadata().getJSON();
			for (int index = 0; index < parser.parsePages().size(); index++) {
				if (parser.parsePages().get(index).getStream() != null) {
					streams.add(parser.parsePages().get(index).getStream().getValue());
				}
			}
			documentToParse.printFileInfo(json, streams);
		} catch (Exception e) {
			System.err.println("Error occured during parsing");
			// e.printStackTrace();
		}
	}
}

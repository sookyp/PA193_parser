package com.company.parser.parsers;

import com.company.parser.primitives.PDFDictionary;
import com.company.parser.primitives.PDFObject;
import com.company.parser.primitives.PDFPage;
import com.company.parser.primitives.PDFStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by val on 02/10/16.
 */
public class PagesParser extends Parser{

    public PagesParser(Path path){
        super(path);
    }

    public List<PDFPage> parsePages() throws Exception{
        List<PDFPage> pages = new ArrayList<PDFPage>();

        String trailer = new TrailerParser(getPath()).parseTrailer();
        String rootObj = parseRoot(trailer);
        String pagesRef = PDFDictionary.parseIndirectObjectFromDictionary(rootObj, "/Pages");

        List<String> kids = parsePageTreeKids(pagesRef);
        traverseLeaves(pages, kids);

        System.out.print("pages parsed = " + pages.size() + "\n");
        return pages;

    }

    private void traverseLeaves(List<PDFPage> pages, List<String> kids) {
        for (int i = 0; i < kids.size(); i++) {
            try {
                parsePage(pages, kids.get(i));
            } catch (Exception e) {
                List<String> nodes = parsePageTreeKids(kids.get(i));
                this.traverseLeaves(pages, nodes);
            }
        }
    }

    private void parsePage(List<PDFPage> pages, String kid) throws IOException, PDFObject.InvalidException {
        String nodeObj = new IndirectObjectParser(getPath()).parseIndirectObject(kid);
        String contentsRef = PDFDictionary.parseIndirectObjectFromDictionary(nodeObj, "/Contents");
        String contentsObj = new IndirectObjectParser(getPath()).parseIndirectObject(contentsRef);
        PDFStream stream = PDFStream.parseStream(contentsObj);
        pages.add(new PDFPage(0, stream));
    }

    private List<String> parsePageTreeKids(String rootObj) {
        String pageObj = new IndirectObjectParser(getPath()).parseIndirectObject(rootObj);
        return PDFDictionary.parseIndirectObjectArrayDictionary(pageObj, "/Kids");
    }

    private String parseRoot(String trailer) {
        String rootRef = PDFDictionary.parseIndirectObjectFromDictionary(trailer, "/Root");
        return new IndirectObjectParser(getPath()).parseIndirectObject(rootRef);
    }
}

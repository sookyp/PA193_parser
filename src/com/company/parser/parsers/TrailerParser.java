package com.company.parser.parsers;

import com.company.parser.primitives.PDFDictionary;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Created by val on 05/10/16.
 *
 * The trailer of a PDF file enables a conforming reader to quickly find the cross-reference table
 * and certain special objects. Conforming readers should read a PDF file from its end.
 * The last line of the file shall contain only the end-of-file marker, %%EOF.
 * The two preceding lines shall contain, one per line and in order,
 * the keyword startxref and the byte offset in the decoded stream from the beginning of
 * the file to the beginning of the xref keyword in the last cross-reference section.
 * The startxref line shall be preceded by the trailer dictionary, consisting of the
 * keyword trailer followed by a series of key-value pairs enclosed in double angle
 * brackets (<<...>>) (using LESS-THAN SIGNs (3Ch) and GREATER-THAN SIGNs (3Eh)).
 */

public class TrailerParser extends Parser {
    private static final String TRAILER_BEGIN = "trailer";
    private static final String TRAILER_END = "startxref";

    public TrailerParser(Path path){
        super(path);
    }

    public PDFDictionary parseTrailer() throws IOException{
        String trailers = new FileReader().read(getPath(), TRAILER_BEGIN, TRAILER_END);
        PDFDictionary[] trailerObjects = new PDFDictionary[0];
        
        List<String> trailersList = Arrays.asList(trailers.split(TRAILER_BEGIN));
        int count = trailersList.toArray().length;
        for(int i = count - 1; i >= 0; i--){
            // System.out.print("\n" + trailersList.toArray()[i].getClass().getName() + " " + trailersList.toArray()[i]);
            // try to parse each trailer; whatever parsed successfully, is the trailer
            // TODO: needs implemented dictionary parsing
        }

        return null;
    }
}

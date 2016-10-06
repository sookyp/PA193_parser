package com.company.parser.parsers;
import com.company.parser.primitives.*;
import com.company.parser.supporting.files.DocumentMetadata;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by val on 06/10/16.
 */
public class IndirectObjectParser extends Parser {
    public IndirectObjectParser(Path path){
        super(path);
    }

    /*
    *   expects input in the form '30 0 R'
    *
     */
    public PDFObject parseIndirectObject(String objectId){
        /*try{
            String reference = objectId.replace("R", "obj");
            reference = " " + reference;

            List<String> indirectObject = new FileReader().read(this.getPath(), reference, "endobj");
            System.out.print(indirectObject);
        } catch(Exception e){
            return null;
        }*/
        return null;
    }

}

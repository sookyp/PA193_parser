package com.company.parser.parsers;
import java.nio.file.Path;
import java.util.regex.*;

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
    public String parseIndirectObject(String objectId) {
        try {
            String content = new FileReader().read(this.getPath());

            String prefix = objectId.replace("R", "obj");
            String suffix = "endobj";

            String regex = "^\\s*" + prefix + "\\s*(.*?)\\s*" + suffix;
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

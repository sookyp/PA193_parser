package com.company.parser.parsers;

import java.nio.file.Path;

/**
 * Created by val on 02/10/16.
 */
public abstract class Parser {
    Path path;
    public Parser(Path path) {
         this.path = path;
    }

    public Path getPath(){
        return this.path;
    }
}

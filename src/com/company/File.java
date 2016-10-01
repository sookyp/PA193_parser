package com.company;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by val on 01/10/16.
 */
public class File {
    Path path;

    public File(Path path){
        this.path = path;
    }

    public Path getPath(){
        return this.path;
    }

    public long fileSize(){
        try {
            return Files.size(this.path);
        } catch (Throwable t) {
            return 0;
        }
    }

    public String fileName(){
        try {
            return this.path.getFileName().toString();
        } catch (Throwable t) {
            return "";
        }
    }

    public void printFileInfo(){
        System.out.println(this.fileSize());
        System.out.println(this.fileName());
    }
}

package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by val on 01/10/16.
 */
public class File {
	Path path;

	public File(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return this.path;
	}

	public long fileSize() {
		try {
			return Files.size(this.path);
		} catch (Throwable t) {
			return 0;
		}
	}

	public String fileName() {
		try {
			return this.path.getFileName().toString();
		} catch (Throwable t) {
			return "";
		}
	}

	public String fileContents() {
		try {
			return new String(Files.readAllBytes(this.path));
		} catch (Throwable t) {
			return "";
		}
	}

	public void printFileInfo(String json, List<String> stream_list) throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println(this.fileSize());
		System.out.println(this.fileName());
		System.out.println(this.fileContents());
	}

    boolean isDirectory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    File[] listFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

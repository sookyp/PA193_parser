/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.parser.supporting.files;

import com.company.parser.parsers.FileReader;
import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author USER-Z
 */
public class PDFVerification {
    private static final int SIZE_KB = 1024;
    private static final int MAX_FILE_SIZE = 400;
    private static final String VERSION_1_4 = "%PDF-1.4";
    private static final String VERSION_1_3 = "%PDF-1.3";
    private static final String VERSION = "%PDF-";
    private static final String END = "%%EOF";    
    
    private String fileContent;
    private String fileContentPrepared;
    private File file;    

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileContentPrepared() {
        return fileContentPrepared;
    }

    public void setFileContentPrepared(String fileContentPrepared) {
        this.fileContentPrepared = fileContentPrepared;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public PDFVerification(Path path) {
        FileReader fr = new FileReader();
        this.fileContent = fr.read(path);
        this.fileContentPrepared = fileContent.replaceAll("(\\r|\\n)", "");;
        this.file = new File(path.toString());
    }
    
    public Boolean verifyPDF(){
        return  this.checkReadableFile() &&
                this.checkEmptyFile() && 
                this.checkFileSize() && 
                this.checkVersion() && 
                this.checkLastLine() &&
                this.checkRootBoject() &&
                this.checkPDFMinimum();
    }    
    
    public Boolean checkEmptyFile(){
        Boolean checkFile;
        checkFile = this.file.exists() && this.file.length() != 0;
        if(! checkFile){
            return Boolean.FALSE;
        }
        try{
            return ((this.fileContent != null) && !(this.fileContent.trim().isEmpty()));
        }
        catch(Exception e){
            return Boolean.FALSE;
        }
    }

    public Boolean checkFileSize(){
        long byteSize = this.file.length();
        long mbSize = byteSize / (SIZE_KB * SIZE_KB);
        return mbSize < MAX_FILE_SIZE;
    }

    public Boolean checkReadableFile(){
        return this.file.canRead();
    }    
        
    public Boolean checkVersion(){
        Pattern p = Pattern.compile(VERSION);
        Matcher m = p.matcher(this.fileContent);
        String fileString;
        int count = 0;

        while(m.find()){
            count++;
        }
        
        if(count > 1){
            return false;
        }
        
        fileString = fileContent.trim();
        String[] content;
        content = fileString.split("\n", 2);
        
        return content[0].contains(VERSION_1_4) || content[0].contains(VERSION_1_3);
    }

    Boolean checkLastLine(){
        String fileString;
        fileString = fileContent.trim();
        StringBuilder s = new StringBuilder();
        int begin = fileString.length() - 1;
        int end = begin - END.length();
        
        for(int i = begin; i > end; i--){
            s.append(fileString.charAt(i));
        }

        s = s.reverse();

        return s.toString().contains(END);
    }

    Boolean checkRootBoject(){
        String regex = "/Root \\d* 0 R";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.fileContent);

        return m.find();
    }    
    
    Boolean checkPDFMinimum(){
        return  this.checkCatalogOccurance() &&
                this.checkPageOccurance() &&
                this.checkOutlinesOccurance() &&
                this.checkRef();
    }      
 
    Boolean checkCatalogOccurance(){
        String obj = "\\d* 0 obj\\s* *<<.*\\s* */Type */Catalog.*\\s* *>> *\\s*endobj";
        Pattern p = Pattern.compile(obj);
        Matcher m = p.matcher(this.fileContentPrepared);
        
        return m.find();
    }

    Boolean checkPageOccurance(){
        String obj = "\\d* 0 obj\\s*<<.*/Type */Page.*>>\\s*endobj";
        Pattern p = Pattern.compile(obj);
        Matcher m = p.matcher(this.fileContentPrepared);
        
        return m.find();        
    }

    Boolean checkOutlinesOccurance(){        
        String obj = "\\d* 0 obj\\s*<<.*/Type */Outlines.*>>\\s*endobj";
        Pattern p = Pattern.compile(obj);
        Matcher m = p.matcher(this.fileContentPrepared);
        
        return m.find();        
    }

    Boolean checkRef(){       
        String obj = "xref\\s*\\d \\d\\s*[\\d{10} \\d{5} n\\s*|\\d \\d\\s*|\\d{10} \\d{5} f\\s*]+ *trailer *\\s*<< *\\s*/(.+)|( *)>>startxref\\d+%%EOF";        
        Pattern p = Pattern.compile(obj);
        Matcher m = p.matcher(this.fileContentPrepared);
        return m.find();         
    }        
    
}

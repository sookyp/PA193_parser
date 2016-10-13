package com.company.parser.supporting.files;

import java.lang.reflect.Field;
import java.util.Date;

public class DocumentMetadata {

    int numberOfPages = 0;
    // names of metadata which we parse
    private Date createDate;
    private String creator;
    private String creatorTool;
    private String keywords;
    private Date modifyDate;
    private String producer;    
    private String title;

    public DocumentMetadata() {

    }


    public int getNumberOfPages(){
        return this.numberOfPages;
    }

    public void setNumberOfPages(int number) {
        this.numberOfPages = number;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorTool() {
        return creatorTool;
    }

    public void setCreatorTool(String creatorTool) {
        this.creatorTool = creatorTool;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Constructor, saves metadata values in DocumentMetadata attributes
     * @param createDate
     * @param creator
     * @param creatorTool
     * @param keywords
     * @param modifyDate
     * @param producer
     * @param title 
     */
    public DocumentMetadata(Date createDate, String creator, String creatorTool, String keywords, Date modifyDate, String producer, String title) {
        this.createDate = createDate;
        this.creator = creator;
        this.creatorTool = creatorTool;
        this.keywords = keywords;
        this.modifyDate = modifyDate;
        this.producer = producer;
        this.title = title;
    }
    
    public String getJSON() throws IllegalArgumentException, IllegalAccessException {
    	String json = "{\n";
    	Field[] elements = this.getClass().getDeclaredFields();
    	for (Field element : elements) {
    		if (element == null)
    			continue;
    		else
			json += "\t\"" + element.getName() + "\"\t:\t\"" + element.get(this) + "\",\n";
    	}
    	json = json.substring(0, json.length() - 2);
    	json += "\n}";
		return json;	
	}
}


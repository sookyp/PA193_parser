package com.company.parser.parsers;

import java.nio.file.*;
import com.company.parser.supporting.files.DocumentMetadata;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by val on 02/10/16.
 */
public class MetadataParser extends Parser {
    /**
     * METADATA_BEGIN, METADATA_END - beginning and ending of metadata block
     */
    private static final String METADATA_BEGIN = "<?xpacket begin";
    private static final String METADATA_END = "<?xpacket end";
    
    /**
     * Map is used for parsing metadata values. Key is name and value is array - beginning and ending
     * of tag between which is stored value in pdf.
     */
private static final Map<String,String[]> METADATA_MAP;
static {
    Map<String,String[]> TMP_MAP = new TreeMap<>();
    TMP_MAP.put("producer", new String[] {"<pdf:Producer>","</pdf:Producer>"});
    TMP_MAP.put("keywords", new String[] {"<pdf:Keywords>","</pdf:Keywords>"});
    TMP_MAP.put("modifyDate", new String[] {"<xap:ModifyDate>","</xap:ModifyDate>"});
    TMP_MAP.put("createDate", new String[] {"<xap:CreateDate>","</xap:CreateDate>"});
    TMP_MAP.put("creatorTool", new String[] {"<xap:CreatorTool>","</xap:CreatorTool>"});
    TMP_MAP.put("title", new String[] {"<dc:title><rdf:Alt><rdf:li xml:lang=\"x-default\">","</rdf:li></rdf:Alt></dc:title>"});
    TMP_MAP.put("creator", new String[] {"<dc:creator><rdf:Seq><rdf:li>","</rdf:li></rdf:Seq></dc:creator>"});
    TMP_MAP.put("zmodifyDate", new String[] {"<xmp:ModifyDate>","</xmp:ModifyDate>"});
    TMP_MAP.put("zcreateDate", new String[] {"<xmp:CreateDate>","</xmp:CreateDate>"});
    TMP_MAP.put("zcreatorTool", new String[] {"<xmp:CreatorTool>","</xmp:CreatorTool>"});  
    METADATA_MAP = Collections.unmodifiableMap(TMP_MAP);
}    

    public MetadataParser(Path path){
        super(path);
    }

    /**
     * method takes metadata block and go through this block searching for values from
     * map (METADATA_MAP - searching for beginning and ending of tag in metadata block). 
     * When it finds a tag, it parses value, when this tag is not there it store empty string. 
     * Finally returns new DocumentMetadata whose attributes are metadata values.
     * @return DocumentMetadata containing metadata values stored in it's attributes 
     * return null if some exception occured.
     */
    public DocumentMetadata parseMetadata() {
        try{
            String MetadataBlock = this.parseMetadataFromFile();
            
            String metaBegin = "";
            String metaEnd = "";
            String metaFin = "";    
            String creatorToolV;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SS:SS");
            Date dateCreated = new Date();
            Date dateModified = new Date();

            List<String> metaParsed = new ArrayList<>();

            for (Map.Entry<String, String[]> meta : METADATA_MAP.entrySet()){
                if (MetadataBlock.contains(meta.getValue()[0]) && (MetadataBlock.contains(meta.getValue()[1]))){
                    metaBegin = meta.getValue()[0];
                    metaEnd = meta.getValue()[1];
                    metaFin = MetadataBlock.substring(MetadataBlock.lastIndexOf(metaBegin) + metaBegin.length(), MetadataBlock.lastIndexOf(metaEnd));
                }
                else{
                    metaFin = "";
                }
                metaParsed.add(metaFin);
            }

        if(!metaParsed.get(0).isEmpty() || metaParsed.get(7).isEmpty()){
            if(metaParsed.get(0).isEmpty()){
                dateCreated = dateFormat.parse(metaParsed.get(7));
            }
            else{
                dateCreated = dateFormat.parse(metaParsed.get(0));
            }
        }
        
        if(!metaParsed.get(4).isEmpty() || metaParsed.get(8).isEmpty()){
            if(metaParsed.get(4).isEmpty()){
                dateCreated = dateFormat.parse(metaParsed.get(8));
            }
            else{
                dateCreated = dateFormat.parse(metaParsed.get(4));
            }
        }
        
        if(metaParsed.get(2).isEmpty()){
            creatorToolV = metaParsed.get(9);
        }
        else{
            creatorToolV = metaParsed.get(2);
        }        
            
            return new DocumentMetadata(dateCreated, metaParsed.get(1), creatorToolV, metaParsed.get(3), dateModified, metaParsed.get(5), metaParsed.get(6));
        } catch(Exception e){
            return null;
        }
    }

    /**
     * From given pdf gets block of metadata.
     * @return String block of metadata (beginning with METADATA_BEGIN, ending with METADATA_END)
     * @throws IOException 
     */
    private String parseMetadataFromFile() throws IOException{
        return new FileReader().read(this.getPath(), METADATA_BEGIN, METADATA_END);
    }
}

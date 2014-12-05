/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class PostComplexDataCombination {
    
    private RDFID rdfId = null;
    private String encoding = null;
    private String mimeType = null;
    private String schema = null;
    
    
    public PostComplexDataCombination(RDFID rdfId){
        this.rdfId = rdfId;
    }
    
    
    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    
    
    
    
    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        ArrayList<LiteralExpression>lexpList = new ArrayList<>();
        ArrayList<ResourceExpression>rexpList = new ArrayList<>();

        if(mimeType != null ){
            LiteralExpression lexp = new LiteralExpression(Vocabulary.MimeType, mimeType);
            lexpList.add(lexp);
        }
        else{
            throw new NullPointerException("MimeType must not be null");
        }
        
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ComplexDataCombinationClass));
        rexpList.add(rexp);
        
        if(encoding != null ){
            LiteralExpression lexp = new LiteralExpression(Vocabulary.Encoding, encoding);
            lexpList.add(lexp);
        }
        
        if(schema != null ){
            LiteralExpression lexp = new LiteralExpression(Vocabulary.Schema, schema);
            lexpList.add(lexp);
        }

        res.setResources(rexpList.toArray(new ResourceExpression[rexpList.size()]));
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        return res;
    }
}

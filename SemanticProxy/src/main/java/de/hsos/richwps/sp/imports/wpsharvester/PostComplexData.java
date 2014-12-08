/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.math.BigInteger;
import java.util.ArrayList;






/**
 * Mutable class that represents a complex description
 *
 * @author fbensman
 */
public class PostComplexData extends PostInAndOutputForm {

    private BigInteger maxMegaBytes = null;
    private PostComplexDataCombination defaultFormat = null;
    private ArrayList<PostComplexDataCombination> supportedFormats = null;
    
    
    
    @Override
    public int getDataType() {
        return COMPLEX_TYPE;
    }
    private RDFID rdfId = null;

    public PostComplexData(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public BigInteger getMaxMegaBytes() {
        return maxMegaBytes;
    }

    public void setMaxMegaBytes(BigInteger maxMegaBytes) {
        this.maxMegaBytes = maxMegaBytes;
    }

    public PostComplexDataCombination getDefaultFormat() {
        return defaultFormat;
    }

    public void setDefaultFormat(PostComplexDataCombination defaultFormat) {
        this.defaultFormat = defaultFormat;
    }

    public ArrayList<PostComplexDataCombination> getSupportedFormats() {
        return supportedFormats;
    }

    public void setSupportedFormats(ArrayList<PostComplexDataCombination> supportedFormats) {
        this.supportedFormats = supportedFormats;
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

        if(maxMegaBytes != null ){
            LiteralExpression lexp = new LiteralExpression(Vocabulary.MaximumMegabytes, maxMegaBytes.toString());
            lexpList.add(lexp);
        }
        
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ComplexDataClass));
        rexpList.add(rexp);
        
        if(defaultFormat != null ){
            rexp = new ResourceExpression(Vocabulary.DefaultFormat, defaultFormat.getRdfId());
            rexpList.add(rexp);
        }
        else{
            throw new NullPointerException("Default data combination must not be null");
        }
        
        if(supportedFormats != null){
            for(PostComplexDataCombination com : supportedFormats){
                rexp = new ResourceExpression(Vocabulary.SupportedFormats, com.getRdfId());
                rexpList.add(rexp);
            }
        }

        res.setResources(rexpList.toArray(new ResourceExpression[rexpList.size()]));
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        return res;
    }

    public RDFID getRdfId() {
        return rdfId;
    }
}

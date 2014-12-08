/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.posttypes;

import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.ows.EIDType;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.ows.gettypes.ComplexDataCombination;
import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Mutable class that represents a Complex description
 *
 * @author fbensman
 */
public class PostComplexData extends PostInAndOutputForm {

    
    private BigInteger maximumMegaBytes = null;
    private PostComplexDataCombination defaultFormat = null;
    private ArrayList<PostComplexDataCombination> supportedFormats = null;
    
    
    @Override
    public int getDataType() {
        return COMPLEX_TYPE;
    }
    private RDFID rdfId = null;

    public PostComplexData() throws MalformedURLException, BadRequestException, InternalSPException, CommunicationException {
        this.rdfId = SPClient.getInstance().requestID(EIDType.COMPLEX);

    }

    public BigInteger getMaximumMegaBytes() {
        return maximumMegaBytes;
    }

    public void setMaximumMegaBytes(BigInteger maximumMegaBytes) {
        this.maximumMegaBytes = maximumMegaBytes;
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
        ArrayList<LiteralExpression>lexpList = new ArrayList<LiteralExpression>();
        ArrayList<ResourceExpression>rexpList = new ArrayList<ResourceExpression>();

        if(maximumMegaBytes != null ){
            LiteralExpression lexp = new LiteralExpression(Vocabulary.MaximumMegabytes, maximumMegaBytes.toString());
            lexpList.add(lexp);
        }
        
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ComplexDataClass));
        rexpList.add(rexp);
        
        if(defaultFormat != null ){
            rexp = new ResourceExpression(Vocabulary.DefaultComplexDataCombination, defaultFormat.getRdfId());
            rexpList.add(rexp);
        }
        else{
            throw new NullPointerException("Default data combination must not be null");
        }
        
        if(supportedFormats != null){
            for(PostComplexDataCombination com : supportedFormats){
                rexp = new ResourceExpression(Vocabulary.SupportedComplexDataCombination, com.getRdfId());
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

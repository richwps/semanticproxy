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
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Mutable class that represents a literal data description
 *
 * @author fbensman
 */
public class PostLiteralData extends PostInAndOutputForm {

    private RDFID rdfId = null;
    private String literalDataType = null;
    private String valuesReference = null;
    private String valuesForm = null;
    private boolean anyValue = false;
    private boolean allowedValues = false; // <- the list for this is currently not accessible
    private String defaultValue = null;

    @Override
    public int getDataType() {
        return LITERAL_TYPE;
    }

    public PostLiteralData() throws MalformedURLException, BadRequestException, InternalSPException, CommunicationException{
        this.rdfId = SPClient.getInstance().requestID(EIDType.LITERAL);

    }
    
    public String getLiteralDataType() {
        return literalDataType;
    }

    public void setLiteralDataType(String literalDataType) {
        this.literalDataType = literalDataType;
    }

    public String getValuesReference() {
        return valuesReference;
    }

    public void setValuesReference(String valuesReference) {
        this.valuesReference = valuesReference;
    }

    public String getValuesForm() {
        return valuesForm;
    }

    public void setValuesForm(String valuesForm) {
        this.valuesForm = valuesForm;
    }

    public boolean isAnyValue() {
        return anyValue;
    }

    public void setAnyValue(boolean anyValue) {
        this.anyValue = anyValue;
    }

    public boolean isAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(boolean allowedValues) {
        this.allowedValues = allowedValues;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    
    

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        ArrayList<LiteralExpression> lexpList = new ArrayList<LiteralExpression>();
        ArrayList<ResourceExpression> rexpList = new ArrayList<ResourceExpression>();

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.LiteralDataClass));
        rexpList.add(rexp);
        
         if (literalDataType != null) {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.LiteralDataType, literalDataType);
            lexpList.add(lexp);
        }

        if (valuesReference != null) {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.ValuesRefernce, valuesReference);
            lexpList.add(lexp);
        }

        if (valuesForm != null) {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.ValuesForm, valuesForm);
            lexpList.add(lexp);
        }

        //AnyValue
        {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.AnyValue, Boolean.toString(anyValue));
            lexpList.add(lexp);
        }

        //AllowedValues
        {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.AllowedValues, Boolean.toString(allowedValues));
            lexpList.add(lexp);
        }

        if (defaultValue != null) {
            LiteralExpression lexp = new LiteralExpression(Vocabulary.DefaultValue, defaultValue);
            lexpList.add(lexp);
        }
        
        res.setResources(rexpList.toArray(new ResourceExpression[rexpList.size()]));
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        
        return res;

    }

    public RDFID getRdfId() {
        return rdfId;
    }
}

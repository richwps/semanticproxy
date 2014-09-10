/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import java.net.URL;

/**
 *
 * @author fbensman
 */
public class RDFIDBuilder {

    private static URL wpsBaseURL = null;
    private static URL processBaseURL = null;
    private static URL inputBaseURL = null;
    private static URL outputBaseURL = null;
    private static URL literalDataBaseURL = null;
    private static URL complexDataBaseURL = null;
    private static URL boundingBoxDataBaseURL = null;

    public static void init(
            URL wpsBaseURL,
            URL processBaseURL,
            URL inputBaseURL,
            URL outputBaseURL,
            URL literalDataBaseURL,
            URL complexDataBaseURL,
            URL boundingBoxDataBaseURL) {
        RDFIDBuilder.wpsBaseURL = wpsBaseURL;
        RDFIDBuilder.processBaseURL = processBaseURL;
        RDFIDBuilder.inputBaseURL = inputBaseURL;
        RDFIDBuilder.outputBaseURL = outputBaseURL;
        RDFIDBuilder.literalDataBaseURL = literalDataBaseURL;
        RDFIDBuilder.complexDataBaseURL = complexDataBaseURL;
        RDFIDBuilder.boundingBoxDataBaseURL = boundingBoxDataBaseURL;
    }
    
    
    private URL wpsURL = null;
    private String processIdentifier = null;
    private String inputIdentifier = null;
    private String outputIdentifier = null;

    
    private RDFIDBuilder(){}
    
    
    public static RDFIDBuilder createID(){
        return new RDFIDBuilder();
    }
    
    public RDFIDBuilder withWpsURL(URL wpsURL) {
        this.wpsURL = wpsURL;
        return this;
    }

    public RDFIDBuilder withProcessIdentifier(String processIdentifier) {
        this.processIdentifier = processIdentifier;
        return this;
    }

    public RDFIDBuilder withInputIdentifier(String inputIdentifier) {
        this.inputIdentifier = inputIdentifier;
        return this;
    }

    public RDFIDBuilder withOutputIdentifier(String outputIdentifier) {
        this.outputIdentifier = outputIdentifier;
        return this;
    }
    
    public RDFID forWPS(){
        if(wpsURL == null){
            return null;
        }
        String str = wpsURL.toString();
        str = str.replace("://", "-");
        str = str.replaceAll("[\\.[://]/]", "-");
        String raw = wpsBaseURL.toString()+"/"+str;
        RDFID id = new RDFID(raw);
        return id;
        
    }
    
    public RDFID forProcess(){
        if(wpsURL == null || processIdentifier == null)
            return null;
        
        String str = wpsURL.toString()+"/"+processIdentifier;
        str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
        String raw = processBaseURL.toString()+"/"+str;
        RDFID id = new RDFID(raw);
        return id;
    }
    
    public RDFID forInput(){
        if(wpsURL == null || processIdentifier == null || inputIdentifier== null)
            return null;
       
        String str = wpsURL.toString()+"/"+processIdentifier+"/"+inputIdentifier;
        str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
        String raw = inputBaseURL.toString()+"/"+str;
        RDFID id = new RDFID(raw);
        return id;
    }
    
    public RDFID forOutput(){
        if(wpsURL == null || processIdentifier == null || outputIdentifier== null)
            return null;
       
        String str = wpsURL.toString()+"/"+processIdentifier+"/"+outputIdentifier;
        str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
        String raw = outputBaseURL.toString()+"/"+str;
        RDFID id = new RDFID(raw);
        return id;
    }
    
    public RDFID forLiteral(){
        if(wpsURL == null || processIdentifier == null)
            return null;
        else if(inputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+inputIdentifier;
            str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
            String raw =  literalDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        else if(inputIdentifier == null && outputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+outputIdentifier;
            str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
            String raw =  literalDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        return null;  
    }
    
    
    public RDFID forComplex(){
        if(wpsURL == null || processIdentifier == null)
            return null;
        else if(inputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+inputIdentifier;
            str = str.replace("://", "-");
        str = str.replaceAll("[\\.:/]", "-");
            String raw =  complexDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        else if(inputIdentifier == null && outputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+outputIdentifier;
            str = str.replace("://", "-");
            str = str.replaceAll("[\\.:/]", "-");
            String raw =  complexDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        return null;  
    }

    
     public RDFID forBoundingBox(){
        if(wpsURL == null || processIdentifier == null)
            return null;
        else if(inputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+inputIdentifier;
            str = str.replace("://", "-");
            str = str.replaceAll("[\\.:/]", "-");
            String raw =  boundingBoxDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        else if(inputIdentifier == null && outputIdentifier != null){
            String str = wpsURL.toString()+"/"+processIdentifier+"/"+outputIdentifier;
            str = str.replace("://", "-");
            str = str.replaceAll("[\\.:/]", "-");
            String raw =  boundingBoxDataBaseURL.toString()+"/"+str;
            RDFID id = new RDFID(raw);
            return id;
        }
        return null;  
    }
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

/**
 *
 * @author fbensman
 */
public class Vocabulary {
        
    private static final String RDF="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String RDFS="http://www.w3.org/2000/01/rdf-schema#";
    private static final String VOC="http://localhost:4567/semanticproxy/resources/vocab#";
    
    //process vocabulary
    public static final String Type = RDF+"type"; //not basic voc but common rdf
    public static final String ProcessType = VOC+"process";
    public static final String Identifier = VOC+"identifier";
    public static final String Title = VOC+"title";
    public static final String Abstract = VOC+"abstract";
    public static final String ProcessVersion = VOC+"processversion";
    public static final String Metadata = VOC+"metadata";
    public static final String Profile = VOC+"profile";
    public static final String WSDL = VOC+"wsdl";
    public static final String StoreSupported = VOC+"storesupported";
    public static final String StatusSupported = VOC+"statussupported";
    
    //wps vocabulary
    public static final String WPSType = VOC+"wpsclass";
    public static final String Endpoint = VOC+"endpoint";
    
    
    public static boolean isBasicPredicate(String sample){
        if(
                sample.equalsIgnoreCase(Identifier) ||
                sample.equalsIgnoreCase(Title) ||
                sample.equalsIgnoreCase(Abstract) ||
                sample.equalsIgnoreCase(ProcessVersion) ||
                sample.equalsIgnoreCase(Metadata) ||
                sample.equalsIgnoreCase(Profile) ||
                sample.equalsIgnoreCase(WSDL) ||
                sample.equalsIgnoreCase(StoreSupported) ||
                sample.equalsIgnoreCase(StatusSupported) ||
                sample.equalsIgnoreCase(Endpoint) 
                
                )
        {
            return true;
        }
        return false;
    }
    
    
    public static boolean isBasicType(String sample){
        if(
                sample.equalsIgnoreCase(ProcessType) ||
                sample.equalsIgnoreCase(WPSType) 
                )
        {
            return true;
        }
        return false;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

/**
 * Contains various definitions for vocabulary integrity
 * @author fbensman
 */
public class Vocabulary {
        
    private static final String RDF="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String RDFS="http://www.w3.org/2000/01/rdf-schema#";
    public static final String VOC="http://localhost:4567/semanticproxy/resources/vocab#";
    
    //predicate ids
    public static final String Type = RDF+"type"; //not basic voc but common rdf
    //top level
    public static final String Domain = VOC+"domain";
    public static final String Owner = VOC+"owner";
    public static final String WPS = VOC+"wps";
    //wps
    public static final String Endpoint = VOC+"endpoint";
    public static final String Process = VOC+"process";
    //process
    public static final String Identifier = VOC+"identifier";
    public static final String Title = VOC+"title";
    public static final String Abstract = VOC+"abstract";
    public static final String Metadata = VOC+"metadata";
    public static final String Profile = VOC+"profile";
    public static final String WSDL = VOC+"wsdl";
    public static final String ProcessVersion = VOC+"processversion";  
    public static final String StoreSupported = VOC+"storesupported";
    public static final String StatusSupported = VOC+"statussupported";
    public static final String Input = VOC +"input";
    public static final String Output = VOC +"output";
    //input
    public static final String MinOccurs = VOC+"minoccurs";
    public static final String MaxOccurs = VOC+"maxoccurs";
    public static final String InputFormChoice = VOC+"inputformchoice";
    //output
    public static final String OutputFormChoice = VOC+"outputformchoice";
    //types
    public static final String WPSClass = VOC+"wpsclass";
    public static final String ProcessClass = VOC+"processclass";
    public static final String DataInputClass = VOC+"datainputclass";
    public static final String ProcessOutputClass = VOC+"processoutputclass";
    public static final String ComplexDataClass = VOC+"complexdataclass";
    public static final String LiteralDataClass = VOC+"literaldataclass";
    public static final String BoundingBoxDataClass = VOC+"boundingboxdataclass";
    
    
    
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
                sample.equalsIgnoreCase(ProcessClass) ||
                sample.equalsIgnoreCase(WPSClass) 
                )
        {
            return true;
        }
        return false;
    }
    
}

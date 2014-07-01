/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import java.io.StringWriter;
import java.net.URL;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

/**
 * Contains various definitions for vocabulary integrity
 * @author fbensman
 */
public class Vocabulary {
        
    private static final String RDF="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String RDFS="http://www.w3.org/2000/01/rdf-schema#";
    public static String VOC="http://localhost:4567/semanticproxy/resources/vocab#";
    
    //predicate ids
    private static final String Label=RDFS+"label";
    public static final String Type=RDF+"type";
    
    //object ids
    private static final String SchemaClass = RDFS+"Class";
    

    //top level
    public static String Domain = null;
    public static String Owner = null;
    public static String WPS = null;
    //wps
    public static String Endpoint = null;
    public static String Process = null;
    //process
    public static String Identifier = null;
    public static String Title = null;
    public static String Abstract = null;
    public static String Metadata = null;
    public static String Profile = null;
    public static String WSDL = null;
    public static String ProcessVersion = null;
    public static String StoreSupported = null;
    public static String StatusSupported = null;
    public static String Input = null;
    public static String Output = null;
    //input
    public static String MinOccurs = null;
    public static String MaxOccurs = null;
    public static String InputFormChoice = null;
    //output
    public static String OutputFormChoice = null;
    //types
    public static String WPSClass = null;
    public static String ProcessClass = null;
    public static String DataInputClass = null;
    public static String ProcessOutputClass = null;
    public static String ComplexDataClass = null;
    public static String LiteralDataClass = null;
    public static String BoundingBoxDataClass = null;
    
    
    private static String RDF_XML_Representation = null;
    
    
    public static void init(URL vocabularyURL) throws Exception{
        VOC = vocabularyURL.toString()+"#";
        


        
        //generate RDF vocabulary document
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, sw);
        Statement stmt = null;
        try {
            writer.startRDF();
         
        //network
        Domain = VOC + "domain";
            stmt = new StatementImpl(new URIImpl(Domain), new URIImpl(Label), new LiteralImpl("Has domain"));
            writer.handleStatement(stmt);
        Owner = VOC+"owner";
            stmt = new StatementImpl(new URIImpl(Owner), new URIImpl(Label), new LiteralImpl("Is owned by"));
            writer.handleStatement(stmt);
        WPS = VOC+"wps";
            stmt = new StatementImpl(new URIImpl(WPS), new URIImpl(Label), new LiteralImpl("Has as WPS"));
            writer.handleStatement(stmt);
            
        //wps
        Endpoint = VOC+"endpoint";
            stmt = new StatementImpl(new URIImpl(Endpoint), new URIImpl(Label), new LiteralImpl("Has endpoint"));
            writer.handleStatement(stmt);
        Process = VOC+"process";
            stmt = new StatementImpl(new URIImpl(Process), new URIImpl(Label), new LiteralImpl("Has as process"));
            writer.handleStatement(stmt);
            
        //process
        Identifier = VOC+"identifier";
            stmt = new StatementImpl(new URIImpl(Identifier), new URIImpl(Label), new LiteralImpl("Has identifier"));
            writer.handleStatement(stmt);
        Title = VOC+"title";
            stmt = new StatementImpl(new URIImpl(Title), new URIImpl(Label), new LiteralImpl("Has title"));
            writer.handleStatement(stmt);
        Abstract = VOC+"abstract";
            stmt = new StatementImpl(new URIImpl(Abstract), new URIImpl(Label), new LiteralImpl("Has abstract"));
            writer.handleStatement(stmt);
        Metadata = VOC+"metadata";
            stmt = new StatementImpl(new URIImpl(Metadata), new URIImpl(Label), new LiteralImpl("Has metadata"));
            writer.handleStatement(stmt);
        Profile = VOC+"profile";
            stmt = new StatementImpl(new URIImpl(Profile), new URIImpl(Label), new LiteralImpl("Has profile"));
            writer.handleStatement(stmt);
        WSDL = VOC+"wsdl";
            stmt = new StatementImpl(new URIImpl(WSDL), new URIImpl(Label), new LiteralImpl("Is described with WSDL"));
            writer.handleStatement(stmt);
        ProcessVersion = VOC+"processversion";
            stmt = new StatementImpl(new URIImpl(ProcessVersion), new URIImpl(Label), new LiteralImpl("Has process version"));
            writer.handleStatement(stmt);
        StoreSupported = VOC+"storesupported";
            stmt = new StatementImpl(new URIImpl(StoreSupported), new URIImpl(Label), new LiteralImpl("Supports store"));
            writer.handleStatement(stmt);
        StatusSupported = VOC+"statussupported";
            stmt = new StatementImpl(new URIImpl(StatusSupported), new URIImpl(Label), new LiteralImpl("Supports status"));
            writer.handleStatement(stmt);
        Input = VOC +"input";
            stmt = new StatementImpl(new URIImpl(Input), new URIImpl(Label), new LiteralImpl("Has input"));
            writer.handleStatement(stmt);
        Output = VOC +"output";
            stmt = new StatementImpl(new URIImpl(Output), new URIImpl(Label), new LiteralImpl("Has output"));
            writer.handleStatement(stmt);
            
        //input
        MinOccurs = VOC+"minoccurs";
            stmt = new StatementImpl(new URIImpl(MinOccurs), new URIImpl(Label), new LiteralImpl("Occurs at minimum"));
            writer.handleStatement(stmt);
        MaxOccurs = VOC+"maxoccurs";
            stmt = new StatementImpl(new URIImpl(Domain), new URIImpl(Label), new LiteralImpl("Has domain"));
            writer.handleStatement(stmt);
        InputFormChoice = VOC+"inputformchoice";
            stmt = new StatementImpl(new URIImpl(InputFormChoice), new URIImpl(Label), new LiteralImpl("InputFormChoice"));
            writer.handleStatement(stmt);
            
        //output
        OutputFormChoice = VOC+"outputformchoice";
            stmt = new StatementImpl(new URIImpl(OutputFormChoice), new URIImpl(Label), new LiteralImpl("OutputFormChoice"));
            writer.handleStatement(stmt);
            
        //types
        WPSClass = VOC+"wpsclass";
            stmt = new StatementImpl(new URIImpl(WPSClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(WPSClass), new URIImpl(Label), new LiteralImpl("Web Processing Service"));
            writer.handleStatement(stmt);
        ProcessClass = VOC+"processclass";
            stmt = new StatementImpl(new URIImpl(ProcessClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ProcessClass), new URIImpl(Label), new LiteralImpl("WPS process"));
            writer.handleStatement(stmt);
        DataInputClass = VOC+"datainputclass";
            stmt = new StatementImpl(new URIImpl(DataInputClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(DataInputClass), new URIImpl(Label), new LiteralImpl("Data input"));
            writer.handleStatement(stmt);
        ProcessOutputClass = VOC+"processoutputclass";
            stmt = new StatementImpl(new URIImpl(ProcessOutputClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ProcessOutputClass), new URIImpl(Label), new LiteralImpl("Process output"));
            writer.handleStatement(stmt);
        ComplexDataClass = VOC+"complexdataclass";
            stmt = new StatementImpl(new URIImpl(ComplexDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ComplexDataClass), new URIImpl(Label), new LiteralImpl("Complex data"));
            writer.handleStatement(stmt);
        LiteralDataClass = VOC+"literaldataclass";
            stmt = new StatementImpl(new URIImpl(LiteralDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(LiteralDataClass), new URIImpl(Label), new LiteralImpl("Literal data"));
            writer.handleStatement(stmt);
        BoundingBoxDataClass = VOC+"boundingboxdataclass";
            stmt = new StatementImpl(new URIImpl(BoundingBoxDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(BoundingBoxDataClass), new URIImpl(Label), new LiteralImpl("BoundingBox data"));
            writer.handleStatement(stmt);
            

            writer.endRDF();
            RDF_XML_Representation =  sw.toString();
        } catch (RDFHandlerException e) {
            throw new Exception("Cannot generate RDF vocabulary "+e.getMessage());
        }
    
    }
    
    
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

    
    
    public static String getRDF_XML_Representation() {
        return RDF_XML_Representation;
    }
    
    
    
    
    
}

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
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;

/**
 * Contains various definitions for vocabulary integrity 
 *
 * @author fbensman
 */
public class Vocabulary {

    private static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
    public static String VOC = "http://localhost:4567/semanticproxy/resources/vocab#";
    //predicate ids
    private static final String Label = RDFS + "label";
    public static final String Type = RDF + "type";
    //object ids
    private static final String SchemaClass = RDFS + "Class";
    //top level
    public static String Domain = null;
    public static String Owner = null;
    public static String WPS = null;
    public static String WFS = null;
    //wps und wfs
    public static String Endpoint = null;
    //wps
    public static String Process = null;
    public static String RichWPSEndpoint = null;
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
    public static String QoSTarget = null;
    //input
    public static String MinOccurs = null;
    public static String MaxOccurs = null;
    public static String InputFormChoice = null;
    //output
    public static String OutputFormChoice = null;
    //complex data
    public static String MaximumMegabytes = null;
    public static String DefaultFormat = null;
    public static String SupportedFormats = null;
    //ComplexDataCombinationType
    public static String Encoding = null;
    public static String MimeType = null;
    public static String Schema = null;
    //literal data
    public static String LiteralDataType = null;
    public static String ValuesRefernce = null;
    public static String ValuesForm = null;
    public static String AnyValue = null;
    public static String AllowedValues = null;
    public static String DefaultValue = null;
    
    //QoSTarget
    public static String Ideal = null;
    public static String UOM = null;
    public static String Max = null;
    public static String Min = null;
    public static String Variance = null;
    
    //wfs
    public static String WFSVersion = null;
    public static String FeatureType = null;
    //feature type
    public static String FeatureTypeName = null;
    
    //types
    public static String NetworkClass = null;
    public static String WPSClass = null;
    public static String ProcessClass = null;
    public static String DataInputClass = null;
    public static String ProcessOutputClass = null;
    public static String ComplexDataClass = null;
    public static String LiteralDataClass = null;
    public static String BoundingBoxDataClass = null;
    public static String ComplexDataCombinationClass = null;
    public static String WFSClass = null;
    public static String FeatureTypeClass = null;
    public static String QoSTargetClass = null;
    
    private static String RDF_XML_Representation = null;

    /**
     * Initializes the vocabulary resources with the specified URL
     *
     * @param vocabularyURL
     * @throws Exception
     */
    public static void init(URL vocabularyURL) throws Exception {
        VOC = vocabularyURL.toString() + "#";




        //generate RDF vocabulary document
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, sw);
        Statement stmt = null; 
        try {
            writer.startRDF();
            writer.handleNamespace("rdf", org.openrdf.model.vocabulary.RDF.NAMESPACE);
            writer.handleNamespace("rdfs", org.openrdf.model.vocabulary.RDFS.NAMESPACE);
            writer.handleNamespace("xsd", XMLSchema.NAMESPACE);
            writer.handleNamespace("rwps", vocabularyURL.toString()+"#");
            
            //network
            Domain = VOC + "domain";
            stmt = new StatementImpl(new URIImpl(Domain), new URIImpl(Label), new LiteralImpl("Has domain"));
            writer.handleStatement(stmt);
            Owner = VOC + "owner";
            stmt = new StatementImpl(new URIImpl(Owner), new URIImpl(Label), new LiteralImpl("Is owned by"));
            writer.handleStatement(stmt);
            WPS = VOC + "wps";
            stmt = new StatementImpl(new URIImpl(WPS), new URIImpl(Label), new LiteralImpl("Has as WPS"));
            writer.handleStatement(stmt);
            WFS = VOC + "wfs";
            stmt = new StatementImpl(new URIImpl(WFS), new URIImpl(Label), new LiteralImpl("Has as WFS"));
            writer.handleStatement(stmt);

            //wps and wfs
            Endpoint = VOC + "endpoint";
            stmt = new StatementImpl(new URIImpl(Endpoint), new URIImpl(Label), new LiteralImpl("Has endpoint"));
            writer.handleStatement(stmt);
            
            //wps
            Process = VOC + "process";
            stmt = new StatementImpl(new URIImpl(Process), new URIImpl(Label), new LiteralImpl("Has as process"));
            writer.handleStatement(stmt);
            RichWPSEndpoint = VOC + "richwpsendpoint";
            stmt = new StatementImpl(new URIImpl(RichWPSEndpoint), new URIImpl(Label), new LiteralImpl("Has RichWPS endpoint"));
            writer.handleStatement(stmt);

            //process
            Identifier = VOC + "identifier";
            stmt = new StatementImpl(new URIImpl(Identifier), new URIImpl(Label), new LiteralImpl("Has identifier"));
            writer.handleStatement(stmt);
            Title = VOC + "title";
            stmt = new StatementImpl(new URIImpl(Title), new URIImpl(Label), new LiteralImpl("Has title"));
            writer.handleStatement(stmt);
            Abstract = VOC + "abstract";
            stmt = new StatementImpl(new URIImpl(Abstract), new URIImpl(Label), new LiteralImpl("Has abstract"));
            writer.handleStatement(stmt);
            Metadata = VOC + "metadata";
            stmt = new StatementImpl(new URIImpl(Metadata), new URIImpl(Label), new LiteralImpl("Has metadata"));
            writer.handleStatement(stmt);
            Profile = VOC + "profile";
            stmt = new StatementImpl(new URIImpl(Profile), new URIImpl(Label), new LiteralImpl("Has profile"));
            writer.handleStatement(stmt);
            WSDL = VOC + "wsdl";
            stmt = new StatementImpl(new URIImpl(WSDL), new URIImpl(Label), new LiteralImpl("Is described with WSDL"));
            writer.handleStatement(stmt);
            ProcessVersion = VOC + "processversion";
            stmt = new StatementImpl(new URIImpl(ProcessVersion), new URIImpl(Label), new LiteralImpl("Has process version"));
            writer.handleStatement(stmt);
            StoreSupported = VOC + "storesupported";
            stmt = new StatementImpl(new URIImpl(StoreSupported), new URIImpl(Label), new LiteralImpl("Supports store"));
            writer.handleStatement(stmt);
            StatusSupported = VOC + "statussupported";
            stmt = new StatementImpl(new URIImpl(StatusSupported), new URIImpl(Label), new LiteralImpl("Supports status"));
            writer.handleStatement(stmt);
            Input = VOC + "input";
            stmt = new StatementImpl(new URIImpl(Input), new URIImpl(Label), new LiteralImpl("Has input"));
            writer.handleStatement(stmt);
            Output = VOC + "output";
            stmt = new StatementImpl(new URIImpl(Output), new URIImpl(Label), new LiteralImpl("Has output"));
            writer.handleStatement(stmt);
            QoSTarget = VOC + "qostarget";
            stmt = new StatementImpl(new URIImpl(QoSTarget), new URIImpl(Label), new LiteralImpl("Occurs at minimum"));
            writer.handleStatement(stmt);

            //input
            MinOccurs = VOC + "minoccurs";
            stmt = new StatementImpl(new URIImpl(MinOccurs), new URIImpl(Label), new LiteralImpl("Occurs at minimum"));
            writer.handleStatement(stmt);
            MaxOccurs = VOC + "maxoccurs";
            stmt = new StatementImpl(new URIImpl(Domain), new URIImpl(Label), new LiteralImpl("Has domain"));
            writer.handleStatement(stmt);
            InputFormChoice = VOC + "inputformchoice";
            stmt = new StatementImpl(new URIImpl(InputFormChoice), new URIImpl(Label), new LiteralImpl("InputFormChoice"));
            writer.handleStatement(stmt);

            //output
            OutputFormChoice = VOC + "outputformchoice";
            stmt = new StatementImpl(new URIImpl(OutputFormChoice), new URIImpl(Label), new LiteralImpl("OutputFormChoice"));
            writer.handleStatement(stmt);
            
            //complex
            MaximumMegabytes = VOC + "maximummegabytes";
            stmt = new StatementImpl(new URIImpl(MaximumMegabytes), new URIImpl(Label), new LiteralImpl("MaximumMegaBytes"));
            writer.handleStatement(stmt);
            DefaultFormat = VOC + "defaultformat";
            stmt = new StatementImpl(new URIImpl(DefaultFormat), new URIImpl(Label), new LiteralImpl("has default format"));
            writer.handleStatement(stmt);
            SupportedFormats = VOC + "supportedformats";
            stmt = new StatementImpl(new URIImpl(SupportedFormats), new URIImpl(Label), new LiteralImpl("supports formats"));
            writer.handleStatement(stmt);
            
            //ComplexDataCombination
            Encoding = VOC + "encoding";
            stmt = new StatementImpl(new URIImpl(Encoding), new URIImpl(Label), new LiteralImpl("Encoding"));
            writer.handleStatement(stmt);
            MimeType = VOC + "mimetype";
            stmt = new StatementImpl(new URIImpl(MimeType), new URIImpl(Label), new LiteralImpl("MimeType"));
            writer.handleStatement(stmt);
            Schema = VOC + "schema";
            stmt = new StatementImpl(new URIImpl(Schema), new URIImpl(Label), new LiteralImpl("Schema"));
            writer.handleStatement(stmt);
            
            //literal
            LiteralDataType = VOC + "literaldatatype";
            stmt = new StatementImpl(new URIImpl(LiteralDataType), new URIImpl(Label), new LiteralImpl("has data type"));
            writer.handleStatement(stmt);
            ValuesRefernce = VOC + "valuesreference";
            stmt = new StatementImpl(new URIImpl(ValuesRefernce), new URIImpl(Label), new LiteralImpl("has reference"));
            writer.handleStatement(stmt);
            ValuesForm = VOC + "valuesform";
            stmt = new StatementImpl(new URIImpl(ValuesForm), new URIImpl(Label), new LiteralImpl("has form"));
            writer.handleStatement(stmt);
            AnyValue = VOC + "anyvalue";
            stmt = new StatementImpl(new URIImpl(AnyValue), new URIImpl(Label), new LiteralImpl("allows any value"));
            writer.handleStatement(stmt);
            AllowedValues = VOC + "allowedvalues";
            stmt = new StatementImpl(new URIImpl(AllowedValues), new URIImpl(Label), new LiteralImpl("allows a predefined set of values"));
            writer.handleStatement(stmt);
            DefaultValue = VOC + "defaultvalue";
            stmt = new StatementImpl(new URIImpl(DefaultValue), new URIImpl(Label), new LiteralImpl("has default value"));
            writer.handleStatement(stmt);

            
            //QoSTarget
            Ideal = VOC + "ideal";
            stmt = new StatementImpl(new URIImpl(Ideal), new URIImpl(Label), new LiteralImpl("has ideal value"));
            writer.handleStatement(stmt);
            UOM = VOC + "uom";
            stmt = new StatementImpl(new URIImpl(QoSTarget), new URIImpl(Label), new LiteralImpl("is measured in"));
            writer.handleStatement(stmt);
            Min = VOC + "min";
            stmt = new StatementImpl(new URIImpl(Min), new URIImpl(Label), new LiteralImpl("is bounded by minimum"));
            writer.handleStatement(stmt);
            Max = VOC + "max";
            stmt = new StatementImpl(new URIImpl(Max), new URIImpl(Label), new LiteralImpl("is bounded by maximum"));
            writer.handleStatement(stmt);
            Variance = VOC + "variance";
            stmt = new StatementImpl(new URIImpl(Variance), new URIImpl(Label), new LiteralImpl("has accepted variance"));
            writer.handleStatement(stmt);

            //wfs
            WFSVersion = VOC + "wfsversion";
            stmt = new StatementImpl(new URIImpl(WFSVersion), new URIImpl(Label), new LiteralImpl("WFSVersion"));
            writer.handleStatement(stmt);
            
            FeatureType = VOC + "featuretype";
            stmt = new StatementImpl(new URIImpl(FeatureType), new URIImpl(Label), new LiteralImpl("knows feature type"));
            writer.handleStatement(stmt);
            
            //feature type
            FeatureTypeName = VOC + "featuretypename";
            stmt = new StatementImpl(new URIImpl(FeatureTypeName), new URIImpl(Label), new LiteralImpl("has name"));
            writer.handleStatement(stmt);           
            
            //types
            NetworkClass = VOC + "networkclass";
            stmt = new StatementImpl(new URIImpl(NetworkClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(NetworkClass), new URIImpl(Label), new LiteralImpl("Network"));
            writer.handleStatement(stmt);
            WPSClass = VOC + "wpsclass";
            stmt = new StatementImpl(new URIImpl(WPSClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(WPSClass), new URIImpl(Label), new LiteralImpl("Web Processing Service"));
            writer.handleStatement(stmt);
            ProcessClass = VOC + "processclass";
            stmt = new StatementImpl(new URIImpl(ProcessClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ProcessClass), new URIImpl(Label), new LiteralImpl("WPS process"));
            writer.handleStatement(stmt);
            DataInputClass = VOC + "datainputclass";
            stmt = new StatementImpl(new URIImpl(DataInputClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(DataInputClass), new URIImpl(Label), new LiteralImpl("Data input"));
            writer.handleStatement(stmt);
            ProcessOutputClass = VOC + "processoutputclass";
            stmt = new StatementImpl(new URIImpl(ProcessOutputClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ProcessOutputClass), new URIImpl(Label), new LiteralImpl("Process output"));
            writer.handleStatement(stmt);
            ComplexDataClass = VOC + "complexdataclass";
            stmt = new StatementImpl(new URIImpl(ComplexDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ComplexDataClass), new URIImpl(Label), new LiteralImpl("Complex data"));
            writer.handleStatement(stmt);
            LiteralDataClass = VOC + "literaldataclass";
            stmt = new StatementImpl(new URIImpl(LiteralDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(LiteralDataClass), new URIImpl(Label), new LiteralImpl("Literal data"));
            writer.handleStatement(stmt);
            BoundingBoxDataClass = VOC + "boundingboxdataclass";
            stmt = new StatementImpl(new URIImpl(BoundingBoxDataClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(BoundingBoxDataClass), new URIImpl(Label), new LiteralImpl("BoundingBox data"));
            writer.handleStatement(stmt);
            ComplexDataCombinationClass = VOC + "complexdatacombinationclass";
            stmt = new StatementImpl(new URIImpl(ComplexDataCombinationClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(ComplexDataCombinationClass), new URIImpl(Label), new LiteralImpl("ComplexDataCombinationClass"));
            writer.handleStatement(stmt);
            WFSClass = VOC + "wfsclass";
            stmt = new StatementImpl(new URIImpl(WFSClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(WFSClass), new URIImpl(Label), new LiteralImpl("WFS"));
            writer.handleStatement(stmt);
            FeatureTypeClass = VOC + "featuretypeclass";
            stmt = new StatementImpl(new URIImpl(FeatureTypeClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(FeatureTypeClass), new URIImpl(Label), new LiteralImpl("FeatureType"));
            writer.handleStatement(stmt);
            QoSTargetClass = VOC + "qostargetclass";
            stmt = new StatementImpl(new URIImpl(QoSTargetClass), new URIImpl(Type), new URIImpl(SchemaClass));
            writer.handleStatement(stmt);
            stmt = new StatementImpl(new URIImpl(QoSTargetClass), new URIImpl(Label), new LiteralImpl("QoS target"));
            writer.handleStatement(stmt);
            

            writer.endRDF();
            RDF_XML_Representation = sw.toString();
        } catch (RDFHandlerException e) {
            throw new Exception("Cannot generate RDF vocabulary " + e.getMessage());
        }

    }

    /**
     * Checks whether a sample string is known by the vocabulary as a predicate
     * with literal object that describes a WPS
     *
     * @param sample
     * @return
     */
    public static boolean isBasicWPSPredicate(String sample) {
        if (sample.equalsIgnoreCase(Identifier)
                || sample.equalsIgnoreCase(Title)
                || sample.equalsIgnoreCase(Abstract)
                || sample.equalsIgnoreCase(ProcessVersion)
                || sample.equalsIgnoreCase(Metadata)
                || sample.equalsIgnoreCase(Profile)
                || sample.equalsIgnoreCase(WSDL)
                || sample.equalsIgnoreCase(StoreSupported)
                || sample.equalsIgnoreCase(StatusSupported)
                || sample.equalsIgnoreCase(Endpoint)
                || sample.equalsIgnoreCase(MinOccurs)
                || sample.equalsIgnoreCase(MaxOccurs)
                || sample.equalsIgnoreCase(QoSTarget)){
            return true;
        }
        return false;
    }
    
  
    
    

    /**
     * Checks whether a sample string is known by the vocabulary as an object
     * for the type predicate that describes a WPS
     *
     * @param sample
     * @return
     */
    public static boolean isType(String sample) {
        if (sample.equalsIgnoreCase(NetworkClass)
                || sample.equalsIgnoreCase(WPSClass)
                || sample.equalsIgnoreCase(ProcessClass)
                || sample.equalsIgnoreCase(DataInputClass)
                || sample.equalsIgnoreCase(ProcessOutputClass)
                || sample.equalsIgnoreCase(ComplexDataClass)
                || sample.equalsIgnoreCase(LiteralDataClass)
                || sample.equalsIgnoreCase(BoundingBoxDataClass)
                || sample.equalsIgnoreCase(QoSTargetClass)
                || sample.equalsIgnoreCase(WFSClass))
        {
            return true;
        }
        return false;
    }
    
    
    
    
    
  
    
    
    

 
    
    
    
    
    
    
    
    
    

    /**
     * Returns an XML/RDF representation of the vocabulary
     *
     * @return
     */
    public static String getRDF_XML_Representation() {
        return RDF_XML_Representation;
    }
}

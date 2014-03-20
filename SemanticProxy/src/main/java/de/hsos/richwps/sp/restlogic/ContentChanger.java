/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.Triple;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

/**
 *
 * @author fbensman
 */
public class ContentChanger {
    
    
    
    
    
    public static void pushProcessRDFintoDB(String rawRDF)throws Exception{
        ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
        ValidationResult result = Validator.checkForInsertProcess(statList);
        if(result.result){
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
            
            //create inverse link to reference from wps to this process
            Statement[] stats = Validator.getStatementsByPredicate(Vocabulary.WPS, statList);
            URI subject = new URI(stats[0].getObject().stringValue());
            URI predicate = new URI(Vocabulary.Process);
            URI object = new URI(stats[0].getSubject().stringValue());
            Triple inverseTriple = new Triple(subject,predicate,object);
            DBIO.insertTriple(inverseTriple); 
        }
        else{
            throw new Exception("Error cannot push process rdf into db, data malformed: "+result.message);
        }
    }
    
    
     /**
     * Deletes the content of the string directry into db
     * @param rawRDF An xml/rdf conform document
     * @throws Exception When db cannot be accessed
     */
    public static void deleteTriples(String rawRDF) throws Exception{
        DBDelete.deleteTriples(rawRDF);
    }

    public static void pushWPSRDFintoDB(String rawRDF) throws Exception{
        ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
        ValidationResult result = Validator.checkForInsertWPS(statList);
        if(result.result){
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
            
        }
        else{
            throw new Exception("Error cannot push wps rdf into db, data malformed: "+result.message);
        }
    }
    
    
    private static ArrayList<Statement> decomposeIntoStatements(String rdfXml) throws Exception{
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try{
            rdfParser.parse(new StringReader(rdfXml), URIConfiguration.RESOURCES_URI);
        }catch(IOException e){
            throw new Exception("Error cannot check rdf"+ e.getMessage());
        }
        return inputList;
    }
    
    
    
}

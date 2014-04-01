/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.SubjectList;
import de.hsos.richwps.sp.types.Triple;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import org.openrdf.model.Statement;
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
    public static boolean deleteProcess(String route) throws Exception{
        String fullRoute = URIConfiguration.HOST_URI+route;
        URI process = new URI(fullRoute);
        if(DBIO.subjectExists(process)){
            DBDelete.deleteProcess(fullRoute);
            return true;
        }
        else
            return false;
    }
    
    
    public static boolean deleteWPS(String route) throws Exception{
        String fullRoute = URIConfiguration.HOST_URI+route;
        URI wps = new URI(fullRoute);
        if(DBIO.subjectExists(wps)){
            DBDelete.deleteWPS(fullRoute);
            return true;
        }
        else
            return false;
    }
    

    public static void pushWPSRDFintoDB(String rawRDF) throws Exception{
        ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
        ValidationResult result = Validator.checkForInsertWPS(statList);
        
        if(result.result){
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
            
            Statement[] stats = Validator.getStatementsByPredicate(Vocabulary.Type, statList);
            URI subject = new URI(URIConfiguration.RESOURCES_URI);
            URI predicate = new URI(Vocabulary.WPS);
            URI object = new URI(stats[0].getSubject().stringValue());
            Triple inverseTriple = new Triple(subject,predicate,object);
            DBIO.insertTriple(inverseTriple); 
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

    public static void updateWPS(String rawRDF, String route) throws Exception {
        String fullRoute = URIConfiguration.HOST_URI+route;
        if(DBIO.subjectExists(new URI(fullRoute))){
            ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
            ValidationResult result = Validator.checkForUpdateWPS(statList);

            if(result.result){
                Statement[] stats = Validator.getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.WPSClass,statList);
                URI subject = new URI(stats[0].getSubject().stringValue());
                DBDelete.deleteWPSForUpdate(subject);
                DBIO.loadRDFXMLStringIntoDB(rawRDF);
            }
            else{
                throw new Exception(result.message);
            }
        }
        else{
            throw new Exception("No such WPS");
        }
        
    }

    public static void updateProcess(String rawRDF, String route) throws Exception{
        
        
        String fullRoute = URIConfiguration.HOST_URI+route;
        if(DBIO.subjectExists(new URI(fullRoute))){
            ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
            ValidationResult result = Validator.checkForUpdateProcess(statList);
            if(result.result){
                Statement[] stats = Validator.getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.WPSClass,statList);
                DBDelete.deleteProcess(fullRoute);
                DBIO.loadRDFXMLStringIntoDB(rawRDF);
            }
            else{
                throw new Exception(result.message);
            }
        }
        else{
            throw new Exception("No such process");
        }
    }
    
    
    
}

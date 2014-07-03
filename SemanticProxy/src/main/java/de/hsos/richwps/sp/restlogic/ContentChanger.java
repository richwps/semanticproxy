/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.Triple;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
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
    
    
    
    
    /**
     * Pushs process rdf into db
     * @param rawRDF
     * @throws Exception 
     */
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
     * Deletes a process from the db
     * @param route An rdf resource identifiere for the process
     * @return True if success
     * @throws Exception When sth is wrong with the db
     */
    public static boolean deleteProcess(String route) throws Exception{
        URL process = new URL(route);
        if(DBIO.subjectExists(process)){
            DBDelete.deleteProcess(route);
            return true;
        }
        else
            return false;
    }
    
    
    /**
     * Deletes a wps from the db
     * @param route An rdf resource identifiere for the wps
     * @return True if success
     * @throws Exception When sth is wrong with the db
     */
    public static boolean deleteWPS(String route) throws Exception{
        URL wps = new URL(route);
        if(DBIO.subjectExists(wps)){
            DBDelete.deleteWPS(route);
            return true;
        }
        else
            return false;
    }
    

    /**
     * pushes raw rdf into db
     * @param rawRDF
     * @throws Exception 
     */
    public static void pushWPSRDFintoDB(String rawRDF) throws Exception{
        ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
        ValidationResult result = Validator.checkForInsertWPS(statList);
        
        if(result.result){
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
            
            Statement[] stats = Validator.getStatementsByPredicate(Vocabulary.Type, statList);            
            URI subject = new URI(DBAdministration.getResourceURL().toString());
            URI predicate = new URI(Vocabulary.WPS);
            URI object = new URI(stats[0].getSubject().stringValue());
            Triple inverseTriple = new Triple(subject,predicate,object);
            DBIO.insertTriple(inverseTriple); 
        }
        else{
            throw new Exception("Error cannot push wps rdf into db, data malformed: "+result.message);
        }
    }
    
    
    /**
     * Decompoeses an rdf string into separate statements
     * @param rdfXml
     * @return
     * @throws Exception 
     */
    private static ArrayList<Statement> decomposeIntoStatements(String rdfXml) throws Exception{
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try{
            rdfParser.parse(new StringReader(rdfXml), DBAdministration.getResourceURL().toString());
        }catch(IOException e){
            throw new Exception("Error cannot check rdf"+ e.getMessage());
        }
        return inputList;
    }

    /**
     * Validates and updates a wps
     * @param rawRDF The rdf descripton of the wps
     * @param route The wps to update
     * @throws Exception 
     */
    public static void updateWPS(String rawRDF, String route) throws Exception {
        if(DBIO.subjectExists(new URL(route))){
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

    
    /**
     * Validates and updates a process
     * @param rawRDF The rdf descripton of the process
     * @param route The wps to update
     * @throws Exception 
     */
    public static void updateProcess(String rawRDF, String route) throws Exception{
        if(DBIO.subjectExists(new URL(route))){
            ArrayList<Statement> statList = decomposeIntoStatements(rawRDF);
            ValidationResult result = Validator.checkForUpdateProcess(statList);
            if(result.result){
                Statement[] stats = Validator.getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.WPSClass,statList);
                DBDelete.deleteProcess(route);
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

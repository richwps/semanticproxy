/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.RDFDescription;
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
public class Validator {
    private static final String RDF="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final String RDFS="http://www.w3.org/2000/01/rdf-schema#";
    private static final String VOC="http://localhost:4567/semanticproxy/resources/vocab#";
    
    private static String vocType = RDF+"type";
    private static String vocProcessType = VOC+"process";
    private static String vocIdentifier = VOC+"identifier";
    private static String vocTitle = VOC+"title";
    private static String vocAbstract = VOC+"abstract";
    private static String vocProcessVersion = VOC+"processversion";
    private static String vocMetadata = VOC+"metadata";
    private static String vocProfile = VOC+"profile";
    private static String vocWSDL = VOC+"wsdl";
    private static String vocStoreSupported = VOC+"storesupported";
    private static String vocStatusSupported = VOC+"statussupported";
    
   
            
    public static ValidationResult checkForInsertProcess(String rdfXml) throws Exception{
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try{
            rdfParser.parse(new StringReader(rdfXml), URIConfiguration.RESOURCES_URI);
        }catch(IOException e){
            throw new Exception("Error cannot check rdf"+ e.getMessage());
        }
        ArrayList<Statement>acceptList = new ArrayList<Statement>();
        
        //get the process
        Statement[] stats= getStatementsByPredicateAndObject(vocType,vocProcessType, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "Just one process allowed");
        shiftStats(inputList, acceptList, stats);
        
        String processId = stats[0].getSubject().stringValue();
        
        //check if process already exists
        if(DBIO.subjectExists(new URI(processId))){
             return new ValidationResult(false, "Process already exists");
        }
        
        //check if name correct
        if(!processId.startsWith(URIConfiguration.RESOURCES_URI)){
            return new ValidationResult(false, "Process does not fit into naming schema");
        }
        
        //get identifier
        stats = getStatementsBySubjectAndPredicate(processId, vocIdentifier, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process identifier found");
        shiftStats(inputList, acceptList, stats);
        
        //get title
        stats = getStatementsBySubjectAndPredicate(processId, vocTitle, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process title found");
        shiftStats(inputList, acceptList, stats);
        
        //get abstract
        stats = getStatementsBySubjectAndPredicate(processId, vocAbstract, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "More than one 0 or 1 abstracts found");
        shiftStats(inputList, acceptList, stats);
            
        
        //get process version
        stats = getStatementsBySubjectAndPredicate(processId, vocProcessVersion, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "1 process version required");
        shiftStats(inputList, acceptList, stats);
        
        //get wsdl
        stats = getStatementsBySubjectAndPredicate(processId, vocWSDL, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 wsdl allowed");
        shiftStats(inputList, acceptList, stats);
        
        //get store supported
        stats = getStatementsBySubjectAndPredicate(processId, vocStoreSupported, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 store supported allowed");
        shiftStats(inputList, acceptList, stats);
        
        //get status supported
        stats = getStatementsBySubjectAndPredicate(processId, vocStatusSupported, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 status supported allowed");
        shiftStats(inputList, acceptList, stats);
        
        
        return new ValidationResult(true, "No error");
        
    }
    
    
    
    private static void shiftStats(ArrayList<Statement> srcList, ArrayList<Statement> dstList, Statement[] stats){
        for(int i=0; i<stats.length;i++){
            srcList.remove(stats[i]);
            dstList.add(stats[i]);;
        }
    }

    
    
    
    private static boolean containsSubject(String subject, ArrayList<Statement> list){
        for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getSubject().stringValue().equals(subject))
                return true;
        }
        return false;
    }
    
    private static boolean containsPredicate(String predicate, ArrayList<Statement> list){
        for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getPredicate().stringValue().equals(predicate))
                return true;
        }
        return false;
    }
    
    private static boolean containsObject(String object, ArrayList<Statement> list){
        for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getObject().stringValue().equals(object))
                return true;
        }
        return false;
    }
    
     private static Statement[] getStatementsBySubject(String subject, ArrayList<Statement> list){
         ArrayList<Statement> retList = new ArrayList<Statement>();
         for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getSubject().stringValue().equals(subject))
                retList.add(st);
        }
        return retList.toArray(new Statement[retList.size()]);
    }
     
     private static Statement[] getStatementsByPredicate(String subject, ArrayList<Statement> list){
         ArrayList<Statement> retList = new ArrayList<Statement>();
         for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getPredicate().stringValue().equals(subject))
                retList.add(st);
        }
        return retList.toArray(new Statement[retList.size()]);
    }
     
     private static Statement[] getStatementsByObject(String object, ArrayList<Statement> list){
         ArrayList<Statement> retList = new ArrayList<Statement>();
         for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getObject().stringValue().equals(object))
                retList.add(st);
        }
        return retList.toArray(new Statement[retList.size()]);
    }
     
     private static Statement[] getStatementsByPredicateAndObject(String predicate, String object, ArrayList<Statement> list){
         ArrayList<Statement> retList = new ArrayList<Statement>();
         for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getObject().stringValue().equals(object) && st.getPredicate().stringValue().equals(predicate))
                retList.add(st);
        }
        return retList.toArray(new Statement[retList.size()]);
    }
     
     private static Statement[] getStatementsBySubjectAndPredicate(String subject, String predicate, ArrayList<Statement> list){
         ArrayList<Statement> retList = new ArrayList<Statement>();
         for(int i=0; i<list.size(); i++){
            Statement st = list.get(i);
            if(st.getSubject().stringValue().equals(subject) && st.getPredicate().stringValue().equals(predicate))
                retList.add(st);
        }
        return retList.toArray(new Statement[retList.size()]);
    }
     
     
            
}


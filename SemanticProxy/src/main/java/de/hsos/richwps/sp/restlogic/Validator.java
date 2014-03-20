/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
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
        Statement[] stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.ProcessType, inputList);
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
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Identifier, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process identifier found");
        shiftStats(inputList, acceptList, stats);
        
        //get title
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Title, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process title found");
        shiftStats(inputList, acceptList, stats);
        
        //get abstract
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Abstract, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "More than one 0 or 1 abstracts found");
        shiftStats(inputList, acceptList, stats);
            
        
        //get process version
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.ProcessVersion, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "1 process version required");
        shiftStats(inputList, acceptList, stats);
        
        //get wsdl
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.WSDL, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 wsdl allowed");
        shiftStats(inputList, acceptList, stats);
        
        //get store supported
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.StoreSupported, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 store supported allowed");
        shiftStats(inputList, acceptList, stats);
        
        //get status supported
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.StatusSupported, inputList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 status supported allowed");
        shiftStats(inputList, acceptList, stats);
        
        //wps uplink
        //gegenseitige Referenzierung
        
        
        //checks...
        //hole alle inputs
        //check ob alle inputs den prozess beschreiben
        //check für jeden input ob die attribute in richtiger anzahl vorhanden sind
        
        //mach das gleiche für die outputs
        
        //check for use of basic vocabulary in remaining statements
        for(int i=0; i<inputList.size();i++){
            Statement st = inputList.get(i);
            String pred = st.getPredicate().stringValue();
            if(Vocabulary.isBasicPredicate(pred))
                return new ValidationResult(false, "Unproper use of basic vocabulary");
        }
        
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

     
    public static ValidationResult checkForInsertWPS(String rdfXml) throws Exception {
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try{
            rdfParser.parse(new StringReader(rdfXml), URIConfiguration.RESOURCES_URI);
        }catch(IOException e){
            throw new Exception("Error cannot check rdf"+ e.getMessage());
        }
        ArrayList<Statement>acceptList = new ArrayList<Statement>();
        
        //get the wps
        Statement[] stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.WPSType, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "One WPS required");
        shiftStats(inputList, acceptList, stats);
        
        String wpsId = stats[0].getSubject().stringValue();
        
        //check if process already exists
        if(DBIO.subjectExists(new URI(wpsId))){
             return new ValidationResult(false, "WPS already exists");
        }
        
        //check if name correct
        if(!wpsId.startsWith(URIConfiguration.RESOURCES_URI)){
            return new ValidationResult(false, "WPS does not fit into naming schema");
        }
        
        //get endpoint
        stats = getStatementsBySubjectAndPredicate(wpsId, Vocabulary.Endpoint, inputList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one wps endpoint found");
        shiftStats(inputList, acceptList, stats);
        
        
        //get the process
        stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.ProcessType, inputList);
        if(stats.length >0)
            return new ValidationResult(false, "No process allowed at this point");
        shiftStats(inputList, acceptList, stats);
        
        //check for use of basic vocabulary in remaining statements
        for(int i=0; i<inputList.size();i++){
            Statement st = inputList.get(i);
            String pred = st.getPredicate().stringValue();
            if(Vocabulary.isBasicPredicate(pred))
                return new ValidationResult(false, "Unproper use of basic vocabulary");
        }
        
        return new ValidationResult(true, "No error");
        
    }
     
     
            
}


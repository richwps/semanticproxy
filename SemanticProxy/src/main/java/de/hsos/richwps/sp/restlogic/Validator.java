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
   
            
    public static ValidationResult checkForInsertProcess(ArrayList<Statement> openList) throws Exception{
        ArrayList<Statement>analizedList = new ArrayList<Statement>();
        
        //get the process
        Statement[] stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.ProcessClass, openList);
        if(stats.length != 1)
            return new ValidationResult(false, "Just one process allowed");
        shiftStats(openList, analizedList, stats);
        
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
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Identifier, openList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process identifier found");
        shiftStats(openList, analizedList, stats);
        
        //get title
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Title, openList);
        if(stats.length != 1)
            return new ValidationResult(false, "More than one process title found");
        shiftStats(openList, analizedList, stats);
        
        //get abstract
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Abstract, openList);
        if(stats.length > 1)
            return new ValidationResult(false, "More than one 0 or 1 abstracts found");
        shiftStats(openList, analizedList, stats);
            
        
        //get metadata
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Metadata, openList);
        shiftStats(openList, analizedList, stats);
        
        
        //get process version
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.ProcessVersion, openList);
        if(stats.length != 1)
            return new ValidationResult(false, "1 process version required");
        shiftStats(openList, analizedList, stats);
        
        //get wsdl
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.WSDL, openList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 wsdl allowed");
        shiftStats(openList, analizedList, stats);
        
        //get store supported
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.StoreSupported, openList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 store supported allowed");
        shiftStats(openList, analizedList, stats);
        
        //get status supported
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.StatusSupported, openList);
        if(stats.length > 1)
            return new ValidationResult(false, "0 or 1 status supported allowed");
        shiftStats(openList, analizedList, stats);
        
        //get wps uplink
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.WPS, openList);
        if(stats.length  !=1)
            return new ValidationResult(false, "One wps required");
        shiftStats(openList, analizedList, stats);
        
        
        //checks...
        //hole alle inputs
        
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Input, openList);
        //check ob alle inputs den prozess beschreiben
        //check für jeden input ob die attribute in richtiger anzahl vorhanden sind
        String[] inputArr = new String[stats.length];
        for(int i=0 ;i<stats.length;i++)
            inputArr[i]=stats[i].getObject().stringValue();
        shiftStats(openList, analizedList, stats);
        
        //go through inputs
        for(int i=0; i<inputArr.length;i++){
            stats = getStatementsBySubjectAndPredicate(inputArr[i], Vocabulary.Type, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of type statements for input");
            if(!stats[0].getObject().stringValue().equals(Vocabulary.DataInputClass))
                return new ValidationResult(false, "Input is not of type input class");
            shiftStats(openList, analizedList, stats);
            
            String dataInput = inputArr[i];
            
            //get identifier
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Identifier, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of data input identifier found");
            shiftStats(openList, analizedList, stats);

            //get title
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Title, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of data input title found");
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);
            
            //get metadata
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);
            
            //get minoccurs
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MinOccurs, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of minoccurs for data input found");
            shiftStats(openList, analizedList, stats);
            
            //get maxoccurs
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MaxOccurs, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of maxoccurs for data input found");
            shiftStats(openList, analizedList, stats);
            
            //get input form choice
            stats = getStatementsBySubjectAndPredicate(dataInput, Vocabulary.InputFormChoice, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of input form choice for data input found");
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "More than one type for data type found");
            String inputFormChoice = stats[0].getObject().stringValue();
            if(!inputFormChoice.equals(Vocabulary.ComplexDataClass) && 
                    !inputFormChoice.equals(Vocabulary.LiteralDataClass) &&
                    !inputFormChoice.equals(Vocabulary.BoundingBoxDataClass)){
                return new ValidationResult(false, "Illegal input form choice");
            }
            shiftStats(openList, analizedList, stats);
        }
       
        //mach das gleiche für die outputs
        
        stats = getStatementsBySubjectAndPredicate(processId, Vocabulary.Output, openList);
        
        String[] outputArr = new String[stats.length];
        for(int i=0 ;i<stats.length;i++)
            outputArr[i]=stats[i].getObject().stringValue();
        shiftStats(openList, analizedList, stats);
        
        //check für jeden output ob die attribute in richtiger anzahl vorhanden sind
        //go through inputs
        for(int i=0; i<outputArr.length;i++){
            stats = getStatementsBySubjectAndPredicate(outputArr[i], Vocabulary.Type, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of type statements for output");
            if(!stats[0].getObject().stringValue().equals(Vocabulary.ProcessOutputClass))
                return new ValidationResult(false, "Output is not of type output class");
            shiftStats(openList, analizedList, stats);
            
            String processOutput = outputArr[i];
            
            //get identifier
            stats = getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Identifier, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of data output identifier found");
            shiftStats(openList, analizedList, stats);

            //get title
            stats = getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Title, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of data output title found");
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);
            
            //get metadata
            stats = getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);
            
            //get output form choice
            stats = getStatementsBySubjectAndPredicate(processOutput, Vocabulary.OutputFormChoice, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of output form choice for data output found");
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if(stats.length != 1)
                return new ValidationResult(false, "Wrong count of types for data type found");
            String outputFormChoice = stats[0].getObject().stringValue();
            if(!outputFormChoice.equals(Vocabulary.ComplexDataClass) &&
                    !outputFormChoice.equals(Vocabulary.LiteralDataClass) &&
                    !outputFormChoice.equals(Vocabulary.BoundingBoxDataClass)){
                return new ValidationResult(false, "Illegal output form choice");
            }
            shiftStats(openList, analizedList, stats);
        }
        
        //check für jedes verbleibende stmt ob es etwas beschreibt das in einer der beiden listen vorhanden ist...
        
        
        //check for use of basic vocabulary in remaining statements
        for(int i=0; i<openList.size();i++){
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if(Vocabulary.isBasicPredicate(pred))
                return new ValidationResult(false, "Unproper use of basic vocabulary");
        }
        //put statements back
        openList.addAll(analizedList);
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
     
     public static Statement[] getStatementsByPredicate(String subject, ArrayList<Statement> list){
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

     
     
    public static ValidationResult checkForInsertWPS(ArrayList<Statement>inputList) throws Exception {
        ArrayList<Statement>acceptList = new ArrayList<Statement>();
        
        //get the wps
        Statement[] stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.WPSClass, inputList);
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
        stats= getStatementsByPredicateAndObject(Vocabulary.Type,Vocabulary.ProcessClass, inputList);
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


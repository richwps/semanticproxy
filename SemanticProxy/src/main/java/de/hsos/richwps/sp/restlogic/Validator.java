/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.openrdf.model.Statement;
import org.openrdf.repository.RepositoryException;

/**
 * Class containing various methods for rdf validation
 *
 * @author fbensman
 */
public class Validator {

    /**
     * Validates rdf statements for a create process
     *
     * @param openList
     * @return
     * @throws Exception
     */
    public static ValidationResult checkForInsertProcess(ArrayList<Statement> openList) throws Exception {
        ArrayList<Statement> analizedList = new ArrayList<>();

        //get the process
        Statement[] stats = ValidationUtils.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.ProcessClass, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "Exactly one process required");
        }
        shiftStats(openList, analizedList, stats);

        String processId = stats[0].getSubject().stringValue();
        

        //check if process already exists
        try {
            if (DBIO.subjectExists(new URL(processId))) {
                return new ValidationResult(false, "Process already exists");
            }
        } catch (MalformedURLException | IllegalStateException | RepositoryException | RDFException e) {
            throw new Exception("Unable to validate rdf for process insertion.", e);
        }

        //check if name correct
        if (!processId.startsWith(DBAdministration.getResourceURL().toString())) {
            return new ValidationResult(false, "Process does not fit into naming schema");
        }

        //get identifier
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Identifier, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one process identifier found");
        }
        shiftStats(openList, analizedList, stats);

        //get title
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Title, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one process title found");
        }
        shiftStats(openList, analizedList, stats);

        //get abstract
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Abstract, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "More than one 0 or 1 abstracts found");
        }
        shiftStats(openList, analizedList, stats);


        //get metadata
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Metadata, openList);
        shiftStats(openList, analizedList, stats);


        //get process version
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.ProcessVersion, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "1 process version required");
        }
        shiftStats(openList, analizedList, stats);

        //get wsdl
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.WSDL, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 wsdl allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get store supported
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.StoreSupported, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 store supported allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get status supported
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.StatusSupported, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 status supported allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get wps uplink
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.WPS, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "One wps required");
        }
        shiftStats(openList, analizedList, stats);


        //checks...
        //hole alle inputs

        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Input, openList);
        //check ob alle inputs den prozess beschreiben
        //check für jeden input ob die attribute in richtiger anzahl vorhanden sind
        String[] inputArr = new String[stats.length];
        for (int i = 0; i < stats.length; i++) {
            inputArr[i] = stats[i].getObject().stringValue();
        }
        shiftStats(openList, analizedList, stats);

        //go through inputs
        for (int i = 0; i < inputArr.length; i++) {
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(inputArr[i], Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of type statements for input");
            }
            if (!stats[0].getObject().stringValue().equals(Vocabulary.DataInputClass)) {
                return new ValidationResult(false, "Input is not of type input class");
            }
            shiftStats(openList, analizedList, stats);

            String dataInput = inputArr[i];

            //get identifier
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Identifier, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data input identifier found");
            }
            shiftStats(openList, analizedList, stats);

            //get title
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Title, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data input title found");
            }
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);

            //get metadata
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);

            //get minoccurs
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MinOccurs, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of minoccurs for data input found");
            }
            shiftStats(openList, analizedList, stats);

            //get maxoccurs
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MaxOccurs, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of maxoccurs for data input found");
            }
            shiftStats(openList, analizedList, stats);

            //get input form choice
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.InputFormChoice, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of input form choice for data input found");
            }
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "More than one type for data type found");
            }
            String inputFormChoice = stats[0].getObject().stringValue();
            if (!inputFormChoice.equals(Vocabulary.ComplexDataClass)
                    && !inputFormChoice.equals(Vocabulary.LiteralDataClass)
                    && !inputFormChoice.equals(Vocabulary.BoundingBoxDataClass)) {
                return new ValidationResult(false, "Illegal input form choice");
            }
            shiftStats(openList, analizedList, stats);
        }

        //same thing for the outputs

        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Output, openList);

        String[] outputArr = new String[stats.length];
        for (int i = 0; i < stats.length; i++) {
            outputArr[i] = stats[i].getObject().stringValue();
        }
        shiftStats(openList, analizedList, stats);

        //check für jeden output ob die attribute in richtiger anzahl vorhanden sind
        //go through inputs
        for (int i = 0; i < outputArr.length; i++) {
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(outputArr[i], Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of type statements for output");
            }
            if (!stats[0].getObject().stringValue().equals(Vocabulary.ProcessOutputClass)) {
                return new ValidationResult(false, "Output is not of type output class");
            }
            shiftStats(openList, analizedList, stats);

            String processOutput = outputArr[i];

            //get identifier
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Identifier, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data output identifier found");
            }
            shiftStats(openList, analizedList, stats);

            //get title
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Title, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data output title found");
            }
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);

            //get metadata
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);

            //get output form choice
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.OutputFormChoice, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of output form choice for data output found");
            }
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of types for data type found");
            }
            String outputFormChoice = stats[0].getObject().stringValue();
            if (!outputFormChoice.equals(Vocabulary.ComplexDataClass)
                    && !outputFormChoice.equals(Vocabulary.LiteralDataClass)
                    && !outputFormChoice.equals(Vocabulary.BoundingBoxDataClass)) {
                return new ValidationResult(false, "Illegal output form choice");
            }
            shiftStats(openList, analizedList, stats);
        }


        //check for use of basic vocabulary in remaining statements
        for (int i = 0; i < openList.size(); i++) {
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if (Vocabulary.isBasicWPSPredicate(pred)) {
                return new ValidationResult(false, "Unproper use of basic vocabulary");
            }
        }
        //put statements back
        openList.addAll(analizedList);
        return new ValidationResult(true, "No error");

    }

    /**
     * Removes certain statements from one list and puts it in another
     *
     * @param srcList The source list
     * @param dstList The destination list
     * @param stats The statements in question
     */
    private static void shiftStats(ArrayList<Statement> srcList, ArrayList<Statement> dstList, Statement[] stats) {
        for (int i = 0; i < stats.length; i++) {
            srcList.remove(stats[i]);
            dstList.add(stats[i]);;
        }
    }

    


    /**
     * Validates a list of statements for insertion of a wps
     *
     * @param openList List of statements to validate
     * @return Result of the validation
     * @throws Exception
     */
    public static ValidationResult checkForInsertWPS(ArrayList<Statement> openList) throws Exception {
        ArrayList<Statement> analizedList = new ArrayList<>();

        //get the wps
        Statement[] stats = ValidationUtils.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.WPSClass, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "One WPS required");
        }
        shiftStats(openList, analizedList, stats);

        String wpsId = stats[0].getSubject().stringValue();

        //check if process already exists
        try {
            if (DBIO.subjectExists(new URL(wpsId))) {
                return new ValidationResult(false, "WPS already exists");
            }
        } catch (MalformedURLException | IllegalStateException | RepositoryException | RDFException e) {
            throw new Exception("Cannot validate rdf for insert wps.", e);
        }

        //check if name correct
        if (!wpsId.startsWith(DBAdministration.getResourceURL().toString())) {
            return new ValidationResult(false, "WPS does not fit into naming schema");
        }

        //get endpoint
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wpsId, Vocabulary.Endpoint, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one WPS endpoint found");
        }
        shiftStats(openList, analizedList, stats);
        
        //get WPS-T endpoint
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wpsId, Vocabulary.WPSTEndpoint, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "More than one WPS-T endpoint found");
        }
        shiftStats(openList, analizedList, stats);


        //get the process
        stats = ValidationUtils.getStatementsByPredicate(Vocabulary.Process, openList);
        if (stats.length > 0) {
            return new ValidationResult(false, "No process allowed at this point");
        }
        shiftStats(openList, analizedList, stats);

        //check for use of basic vocabulary in remaining statements
        for (int i = 0; i < openList.size(); i++) {
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if (Vocabulary.isBasicWPSPredicate(pred)) {
                return new ValidationResult(false, "Unproper use of basic vocabulary");
            }
        }
        openList.addAll(analizedList);

        return new ValidationResult(true, "No error");

    }
    
    
    
    
    /**
     * Validates a list of statements for insertion of a wps
     *
     * @param openList List of statements to validate
     * @return Result of the validation
     * @throws Exception
     */
    public static ValidationResult checkForInsertWFS(ArrayList<Statement> openList) throws Exception {
        ArrayList<Statement> analizedList = new ArrayList<>();

        //get the wps
        Statement[] stats = ValidationUtils.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.WFSClass, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "One WFS required");
        }
        shiftStats(openList, analizedList, stats);

        String wfsId = stats[0].getSubject().stringValue();

        //check if WFS already exists
        try {
            if (DBIO.subjectExists(new URL(wfsId))) {
                return new ValidationResult(false, "WFS already exists");
            }
        } catch (MalformedURLException | IllegalStateException | RepositoryException | RDFException e) {
            throw new Exception("Cannot validate RDF for insert WFS.", e);
        }

        //check if RDF id correct
        if (!wfsId.startsWith(DBAdministration.getResourceURL().toString())) {
            return new ValidationResult(false, "WFS "+wfsId+" does not fit into naming schema");
        }

        //get endpoint
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wfsId, Vocabulary.Endpoint, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "Wrong count of endpoints found for WFS");
        }
        shiftStats(openList, analizedList, stats);
        
        
        //get version
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wfsId, Vocabulary.WFSVersion, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "Wrong count of version attributes found for WFS");
        }
        shiftStats(openList, analizedList, stats);


        //check feature types
        //get all feature types
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wfsId, Vocabulary.FeatureType, openList);
       
        //put featuretype subjects into a String array
        String[] featureTypeArr = new String[stats.length];
        for (int i = 0; i < stats.length; i++) {
            featureTypeArr[i] = stats[i].getObject().stringValue();
        }
        shiftStats(openList, analizedList, stats);

        //go through feature types
        for (int i = 0; i < featureTypeArr.length; i++) {
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(featureTypeArr[i], Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of type statements for FeatureType found "+stats.length);
            }
            if (!stats[0].getObject().stringValue().equals(Vocabulary.FeatureTypeClass)) {
                return new ValidationResult(false, "FeatureType is not of type feature type class");
            }
            shiftStats(openList, analizedList, stats);

            String featureType = featureTypeArr[i];

            //get name
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(featureType, Vocabulary.FeatureTypeName, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of name attributes found");
            }
            shiftStats(openList, analizedList, stats);

        }

        //check for use of basic vocabulary in remaining statements
        for (int i = 0; i < openList.size(); i++) {
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if (Vocabulary.isBasicWPSPredicate(pred)) {
                return new ValidationResult(false, "Unproper use of basic vocabulary");
            }
        }
        openList.addAll(analizedList);

        return new ValidationResult(true, "No error");

    }
    

    /**
     * Validates a list of statements for update of a wps
     *
     * @param openList List of statements to validate
     * @return Result of validation
     * @throws Exception
     */
    public static ValidationResult checkForUpdateWPS(ArrayList<Statement> openList) throws Exception {
        ArrayList<Statement> analizedList = new ArrayList<Statement>();

        //get the wps
        Statement[] stats = ValidationUtils.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.WPSClass, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "One WPS required");
        }
        shiftStats(openList, analizedList, stats);

        String wpsId = stats[0].getSubject().stringValue();

        //check if process already exists
        try {
            if (!DBIO.subjectExists(new URL(wpsId))) {
                return new ValidationResult(false, "WPS does not exist");
            }
        } catch (MalformedURLException | IllegalStateException | RepositoryException | RDFException e) {
            throw new Exception("Cannot validate rdf for updating wps.", e);
        }

        //check if name correct
        if (!wpsId.startsWith(DBAdministration.getResourceURL().toString())) {
            return new ValidationResult(false, "WPS does not fit into naming schema");
        }

        //get endpoint
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(wpsId, Vocabulary.Endpoint, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one wps endpoint found");
        }
        shiftStats(openList, analizedList, stats);

        //get the process
        stats = ValidationUtils.getStatementsByPredicate(Vocabulary.Process, openList);
        if (stats.length > 0) {
            return new ValidationResult(false, "No process allowed at this point");
        }
        shiftStats(openList, analizedList, stats);


        //check for use of basic vocabulary in remaining statements
        for (int i = 0; i < openList.size(); i++) {
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if (Vocabulary.isBasicWPSPredicate(pred)) {
                return new ValidationResult(false, "Unproper use of basic vocabulary");
            }
        }
        openList.addAll(analizedList);

        return new ValidationResult(true, "No error");
    }

    
    
    
    
    /**
     * Validates a list of statements for update of a process
     *
     * @param openList List of statements to validate
     * @return Result of validation
     * @throws Exception
     */
    public static ValidationResult checkForUpdateProcess(ArrayList<Statement> openList) throws Exception{
        ArrayList<Statement> analizedList = new ArrayList<Statement>();

        //get the process
        Statement[] stats = ValidationUtils.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.ProcessClass, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "Just one process allowed");
        }
        shiftStats(openList, analizedList, stats);

        String processId = stats[0].getSubject().stringValue();

        //check if process already exists
        try{
        if (!DBIO.subjectExists(new URL(processId))) {
            return new ValidationResult(false, "Process does not exists");
        }
        }catch(MalformedURLException | IllegalStateException | RepositoryException | RDFException e){
            throw new Exception("Cannot validate rdf for updating process.",e);
        }
        
        //check if name correct
        if (!processId.startsWith(DBAdministration.getResourceURL().toString())) {
            return new ValidationResult(false, "Process does not fit into naming schema");
        }

        //get identifier
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Identifier, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one process identifier found");
        }
        shiftStats(openList, analizedList, stats);

        //get title
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Title, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "More than one process title found");
        }
        shiftStats(openList, analizedList, stats);

        //get abstract
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Abstract, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "More than one 0 or 1 abstracts found");
        }
        shiftStats(openList, analizedList, stats);


        //get metadata
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Metadata, openList);
        shiftStats(openList, analizedList, stats);


        //get process version
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.ProcessVersion, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "1 process version required");
        }
        shiftStats(openList, analizedList, stats);

        //get wsdl
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.WSDL, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 wsdl allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get store supported
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.StoreSupported, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 store supported allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get status supported
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.StatusSupported, openList);
        if (stats.length > 1) {
            return new ValidationResult(false, "0 or 1 status supported allowed");
        }
        shiftStats(openList, analizedList, stats);

        //get wps uplink
        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.WPS, openList);
        if (stats.length != 1) {
            return new ValidationResult(false, "One wps required");
        }
        shiftStats(openList, analizedList, stats);


        //checks...
        //hole alle inputs

        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Input, openList);
        //check ob alle inputs den prozess beschreiben
        //check für jeden input ob die attribute in richtiger anzahl vorhanden sind
        String[] inputArr = new String[stats.length];
        for (int i = 0; i < stats.length; i++) {
            inputArr[i] = stats[i].getObject().stringValue();
        }
        shiftStats(openList, analizedList, stats);

        //go through inputs
        for (int i = 0; i < inputArr.length; i++) {
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(inputArr[i], Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of type statements for input");
            }
            if (!stats[0].getObject().stringValue().equals(Vocabulary.DataInputClass)) {
                return new ValidationResult(false, "Input is not of type input class");
            }
            shiftStats(openList, analizedList, stats);

            String dataInput = inputArr[i];

            //get identifier
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Identifier, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data input identifier found");
            }
            shiftStats(openList, analizedList, stats);

            //get title
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Title, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data input title found");
            }
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);

            //get metadata
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);

            //get minoccurs
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MinOccurs, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of minoccurs for data input found");
            }
            shiftStats(openList, analizedList, stats);

            //get maxoccurs
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.MaxOccurs, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of maxoccurs for data input found");
            }
            shiftStats(openList, analizedList, stats);

            //get input form choice
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataInput, Vocabulary.InputFormChoice, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of input form choice for data input found");
            }
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "More than one type for data type found");
            }
            String inputFormChoice = stats[0].getObject().stringValue();
            if (!inputFormChoice.equals(Vocabulary.ComplexDataClass)
                    && !inputFormChoice.equals(Vocabulary.LiteralDataClass)
                    && !inputFormChoice.equals(Vocabulary.BoundingBoxDataClass)) {
                return new ValidationResult(false, "Illegal input form choice");
            }
            shiftStats(openList, analizedList, stats);
        }

        //mach das gleiche für die outputs

        stats = ValidationUtils.getStatementsBySubjectAndPredicate(processId, Vocabulary.Output, openList);

        String[] outputArr = new String[stats.length];
        for (int i = 0; i < stats.length; i++) {
            outputArr[i] = stats[i].getObject().stringValue();
        }
        shiftStats(openList, analizedList, stats);

        //check für jeden output ob die attribute in richtiger anzahl vorhanden sind
        //go through inputs
        for (int i = 0; i < outputArr.length; i++) {
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(outputArr[i], Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of type statements for output");
            }
            if (!stats[0].getObject().stringValue().equals(Vocabulary.ProcessOutputClass)) {
                return new ValidationResult(false, "Output is not of type output class");
            }
            shiftStats(openList, analizedList, stats);

            String processOutput = outputArr[i];

            //get identifier
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Identifier, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data output identifier found");
            }
            shiftStats(openList, analizedList, stats);

            //get title
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Title, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of data output title found");
            }
            shiftStats(openList, analizedList, stats);

            //get abstract
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Abstract, openList);
            shiftStats(openList, analizedList, stats);

            //get metadata
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.Metadata, openList);
            shiftStats(openList, analizedList, stats);

            //get output form choice
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(processOutput, Vocabulary.OutputFormChoice, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of output form choice for data output found");
            }
            shiftStats(openList, analizedList, stats);
            String dataType = stats[0].getObject().stringValue();
            stats = ValidationUtils.getStatementsBySubjectAndPredicate(dataType, Vocabulary.Type, openList);
            if (stats.length != 1) {
                return new ValidationResult(false, "Wrong count of types for data type found");
            }
            String outputFormChoice = stats[0].getObject().stringValue();
            if (!outputFormChoice.equals(Vocabulary.ComplexDataClass)
                    && !outputFormChoice.equals(Vocabulary.LiteralDataClass)
                    && !outputFormChoice.equals(Vocabulary.BoundingBoxDataClass)) {
                return new ValidationResult(false, "Illegal output form choice");
            }
            shiftStats(openList, analizedList, stats);
        }

        //check für jedes verbleibende stmt ob es etwas beschreibt das in einer der beiden listen vorhanden ist...


        //check for use of basic vocabulary in remaining statements
        for (int i = 0; i < openList.size(); i++) {
            Statement st = openList.get(i);
            String pred = st.getPredicate().stringValue();
            if (Vocabulary.isBasicWPSPredicate(pred)) {
                return new ValidationResult(false, "Unproper use of basic vocabulary");
            }
        }
        //put statements back
        openList.addAll(analizedList);
        return new ValidationResult(true, "No error");

    }
}

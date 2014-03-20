/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;

/**
 *
 * @author fbensman
 */
public class ContentChanger {
    
    
    
    
    
    public static void pushProcessRDFintoDB(String rawRDF)throws Exception{
        ValidationResult result = Validator.checkForInsertProcess(rawRDF);
        if(result.result)
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
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
        ValidationResult result = Validator.checkForInsertWPS(rawRDF);
        if(result.result)
            DBIO.loadRDFXMLStringIntoDB(rawRDF);
        else{
            throw new Exception("Error cannot push wps rdf into db, data malformed: "+result.message);
        }
    }
    
    
    
    
}

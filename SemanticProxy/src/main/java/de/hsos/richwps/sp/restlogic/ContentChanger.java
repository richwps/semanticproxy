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
    
    public static void pushRawRDFintoDB(String rawRDF)throws Exception{
        DBIO.loadRDFXMLStringIntoDB(rawRDF);
    }
    
    
     /**
     * Deletes the content of the string directry into db
     * @param rawRDF An xml/rdf conform document
     * @throws Exception When db cannot be accessed
     */
    public static void deleteTriples(String rawRDF) throws Exception{
        DBDelete.deleteTriples(rawRDF);
    }
    
    
}

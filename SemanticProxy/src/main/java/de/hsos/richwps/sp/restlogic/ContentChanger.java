/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;

/**
 *
 * @author fbensman
 */
public class ContentChanger {
    
    public static void pushRawRDFintoDB(String rawRDF)throws Exception{
        DBIO.loadRDFXMLStringIntoDB(rawRDF);
    }
}

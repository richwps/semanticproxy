/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.types.EIDType;
import de.hsos.richwps.sp.types.IDGenerator;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author fbensman
 */
public class IDGeneratorRestAccess {
    
    
    
    
    
    /**
     * Retrieves an RDFID from the generator and wrappes it in an URL-List
     * @param t given type
     * @return List of one subject.
     * @throws MalformedURLException 
     */
    public static SubjectList retrieveID(EIDType t) throws MalformedURLException{     
        URL retVal =  IDGenerator.getInstance().generateID(t);
        
        SubjectList list = new SubjectList();
        list.add(retVal);
        return list;
    }
    
}

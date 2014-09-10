/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports;

/**
 *
 * @author fbensman
 */
public class ImportException extends Exception {
    
    public ImportException(String msg, Exception innerException){
        super(msg, innerException);
    }
    
    public ImportException(String msg){
        super(msg);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

/**
 * Indicates any RDF related exception
 * @author fbensman
 */
public class RDFException extends Exception{
    
    public RDFException(String msg, Exception innerException){
        super(msg, innerException);
    }
    
    public RDFException(String msg){
        super(msg);
    }
}

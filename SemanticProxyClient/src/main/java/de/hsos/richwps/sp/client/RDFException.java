/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 * Indicates an error during an RDF parsing/generating operation
 *
 * @author fbensman
 */
public class RDFException extends Exception {

    public RDFException(String msg) {
        super(msg);
    }
    
    public RDFException(String msg, Exception parent) {
        super(msg,parent);
    }
}

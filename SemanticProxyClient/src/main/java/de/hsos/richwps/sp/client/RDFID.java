/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 *
 * @author fbensman
 */
public class RDFID {
    public String rdfID = null;
    
    public RDFID(){}
    
    public RDFID(String rdfID){
        this.rdfID = rdfID;
    }
    
    public RDFID(RDFID clone){
        this.rdfID = clone.rdfID;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.rdf;

/**
 * Class that stores an RDF resource id
 *
 * @author fbensman
 */
public class RDFID {

    public String rdfID = null;

    public RDFID() {
    }

    public RDFID(String rdfID) {
        this.rdfID = rdfID;
    }

    public RDFID(RDFID clone) {
        this.rdfID = clone.rdfID;
    }
    
    @Override
    public String toString(){
        return rdfID;
    }
}

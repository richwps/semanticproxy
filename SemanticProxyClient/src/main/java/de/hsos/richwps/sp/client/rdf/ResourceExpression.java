/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.rdf;

/**
 * Class to store refernced resource ids
 *
 * @author fbensman
 */
public class ResourceExpression {

    public String predicate = null;
    public RDFID rdfID = null;

    public ResourceExpression(String pred, RDFID rdfID) {
        predicate = pred;
        this.rdfID = rdfID;
    }
}

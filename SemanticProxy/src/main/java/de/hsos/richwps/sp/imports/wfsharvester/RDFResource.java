/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsharvester;

import de.hsos.richwps.sp.imports.wpsharvester.*;
import java.util.ArrayList;

/**
 * Represents an RDF resource and stores describing statements as properties and
 * object references. Class operates on RDF level.
 *
 * @author fbensman
 */
public class RDFResource {

    /**
     * RDF id of the RDF resource that this object stands for.
     */
    private RDFID rdfID = null;
    private LiteralExpression[] fields = null;
    private ResourceExpression[] resources = null;

    /**
     * Creates an empty RDFResource with out properties etc.
     *
     * @param rdfID
     */
    public RDFResource(RDFID rdfID) {
        this.rdfID = rdfID;
        fields = new LiteralExpression[0];
        resources = new ResourceExpression[0];
    }

    public RDFID getRdfID() {
        return rdfID;
    }

    public void setRdfID(RDFID rdfID) {
        this.rdfID = rdfID;
    }

    public LiteralExpression[] getFields() {
        return fields;
    }

    public void setFields(LiteralExpression[] fields) {
        this.fields = fields;
    }

    public ResourceExpression[] getResources() {
        return resources;
    }

    public void setResources(ResourceExpression[] resources) {
        this.resources = resources;
    }

    /**
     * Helper for wrapper classes.
     *
     * @param predicate
     * @return
     */
    public String[] findLiterals(String predicate) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].predicate.equals(predicate)) {
                list.add(fields[i].literal);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Helper for wrapper classes.
     *
     * @param predicate
     * @return
     */
    public RDFID[] findResources(String predicate) {
        ArrayList<RDFID> list = new ArrayList<RDFID>();
        for (int i = 0; i < resources.length; i++) {
            if (resources[i].predicate.equals(predicate)) {
                list.add(resources[i].rdfID);
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new RDFID[list.size()]);
    }
}

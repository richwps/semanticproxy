/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URI;

/**
 * Store an rdf triple
 *
 * @author fbensman
 */
public class Triple {

    private URI subject = null;
    private URI predicate = null;
    private URI objectAsResource = null;
    private String objectAsLiteral = null;

    /**
     * Creates an object
     *
     * @param subject The subject
     * @param predicate The predicate
     * @param object The object
     */
    public Triple(URI subject, URI predicate, URI object) {
        this.subject = subject;
        this.predicate = predicate;
        this.objectAsResource = object;
        this.objectAsLiteral = null;
    }

    /**
     * Creates an object
     *
     * @param subject The subject
     * @param predicate The predicate
     * @param object The object
     */
    public Triple(URI subject, URI predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.objectAsResource = null;
        this.objectAsLiteral = object;
    }

    public URI getSubject() {
        return subject;
    }

    public void setSubject(URI subject) {
        this.subject = subject;
    }

    public URI getPredicate() {
        return predicate;
    }

    public void setPredicate(URI predicate) {
        this.predicate = predicate;
    }

    public URI getObjectAsResource() {
        return objectAsResource;
    }

    /**
     * Sets a URI resource as object, if there was a literal object it is
     * removed
     *
     * @param objectAsResource
     */
    public void setObjectAsResource(URI objectAsResource) {
        this.objectAsResource = objectAsResource;
        this.objectAsLiteral = null;
    }

    public String getObjectAsLiteral() {
        return objectAsLiteral;
    }

    /**
     * Sets a literal as object, if there was a resource uri it is removed
     *
     * @param objectAsResource
     */
    public void setObjectAsLiteral(String objectAsLiteral) {
        this.objectAsLiteral = objectAsLiteral;
        this.objectAsResource = null;
    }

    /**
     * Generates an rdf/xml representation of a triple
     *
     * @param tabs The number of tabs to use
     * @return An rdf/xml representation of a triple
     */
    public String toRDFTab(int tabs) {
        String tab = "    ";
        String allTabs = "";
        String str = "";
        for (int i = 0; i < tabs; i++) {
            allTabs += tab;
        }

        if (objectAsLiteral == null) { //we need a resource tag
            str += allTabs + "<" + predicate + " rdf:resource=\"" + objectAsResource + "\"/>";
        } else {
            str += allTabs + "<" + predicate + ">" + objectAsLiteral + "</" + predicate + ">";
        }
        return str;
    }
}

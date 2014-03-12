/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Describes a resource by storing the defining triples
 *
 * @author fbensman
 */
public class RDFDescription {

    private ArrayList<Triple> tripleList = null;
    private URI sharedSubject = null;

    /**
     * Creates an object
     *
     * @param subject The subject all triples describe
     */
    public RDFDescription(URI subject) {
        sharedSubject = subject;
        tripleList = new ArrayList<Triple>();
    }

    /**
     * Adds a triple if it matches the described subject
     *
     * @param triple The triple to store
     * @return True if triple was added false if not. Triples can be rejected if
     * they do not match the described subject or are already in the desciption.
     */
    public boolean addTriple(Triple triple) {
        if (triple.getSubject().equals(sharedSubject)) {
            if (!tripleList.contains(triple)) {
                tripleList.add(triple);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a list of triples to the description. Triples are silently rejected
     * if they do not match the described subject or are already in the
     * desciption.
     *
     * @param list The list of triples
     */
    public void addTriples(ArrayList<Triple> list) {
        for (int i = 0; i < list.size(); i++) {
            addTriple(list.get(i));
        }
    }

    /**
     * Return an RDF/XML representation of this description
     *
     * @param tabs Tab count for formatting
     * @return An RDF/XML representation of this description
     */
    public String toRDFTag(int tabs) {
        String tab = "    ";
        String allTabs = "";
        String str = "";
        for (int i = 0; i < tabs; i++) {
            allTabs += tab;
        }

        str += allTabs + "<rdf:Description rdf:about=\"" + sharedSubject.toString() + "\">";

        for (int i = 0; i < tripleList.size(); i++) {
            str += "\n" + allTabs + tripleList.get(i).toRDFTab(tabs + 1);
        }

        str += "\n" + allTabs + "</rdf:Description>";
        return str;
    }

    /**
     * Returns whether a triple describes the described subject
     *
     * @param triple The triple to check
     * @return True if trible describes the described subject, false if not
     */
    public boolean acceptsTriple(Triple triple) {
        if (sharedSubject.equals(triple.getSubject())) {
            return true;
        }
        return false;
    }

    /**
     * Returns the described subject
     *
     * @return The described subject
     */
    public URI getSharedSubject() {
        return sharedSubject;
    }

    /**
     * Returns the count of triples in this description
     *
     * @return The count of triples in this description
     */
    public int getTripleCount() {
        return tripleList.size();
    }
}

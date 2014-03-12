/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URI;
import java.util.ArrayList;

/**
 * Stores a couple of descriptions
 *
 * @author fbensman
 */
public class RDFDocument {

    private ArrayList<RDFDescription> descriptionList = null;

    public RDFDocument() {
        descriptionList = new ArrayList<RDFDescription>();
    }

    /**
     * Checks wether a description describing the same resource is already in
     * the document
     *
     * @param description The description to check
     * @return True if description is not yet stored in the document
     */
    public boolean acceptsDescription(RDFDescription description) {
        URI sharedSubject = description.getSharedSubject();
        for (int i = 0; i < descriptionList.size(); i++) {
            if (descriptionList.get(i).getSharedSubject().equals(sharedSubject)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a description to the document
     *
     * @param description The description to add
     * @return True if the could be added, false if not, a description can be
     * added if a description describing the same resource is not yet in the
     * document
     */
    public boolean addDescription(RDFDescription description) {
        if (descriptionList.contains(description)) {
            return false;
        }
        descriptionList.add(description);
        return true;
    }

    /**
     * Check whether a triple can be added to the document. The triples is
     * accepted if there is a description in the document that describes the
     * same resource an if the triple is not yet in the document.
     *
     * @param triple The triple to check
     * @return True if triple can be added, false if not
     */
    public boolean acceptsTriple(Triple triple) {
        for (int i = 0; i < descriptionList.size(); i++) {
            if (descriptionList.get(i).acceptsTriple(triple)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a triple to the corresponding description in this document
     *
     * @param triple The triple to add
     * @return True if triple could be added, false if not
     */
    public boolean addTriple(Triple triple) {
        for (int i = 0; i < descriptionList.size(); i++) {
            if (descriptionList.get(i).acceptsTriple(triple)) {
                return descriptionList.get(i).addTriple(triple);
            }
        }
        return false;
    }

    /**
     * Creates an xml/rdf representation of this document
     *
     * @return An xml/rdf representation of this document
     */
    public String rDFXMLRepresentation() {
        String tab = "    ";
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<rdf:RDF\n"
                + tab + "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + tab + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">";


        for (int i = 0; i < descriptionList.size(); i++) {
            str += "\n\n" + descriptionList.get(i).toRDFTag(1);
        }


        str += "\n</rdf:RDF>\n";
        return str;
    }

    /**
     * Returns the count of triples in this document
     *
     * @return The count of triples in this document
     */
    public int getTripleCount() {
        int cnt = 0;
        for (int i = 0; i < descriptionList.size(); i++) {
            cnt += descriptionList.get(i).getTripleCount();
        }
        return cnt;
    }
}

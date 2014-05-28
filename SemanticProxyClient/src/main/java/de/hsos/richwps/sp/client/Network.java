/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.client.Vocabulary;

/**
 * Wraps an RDFResource object that represents the network. This wrapper
 * abstracts the rdf handling
 *
 * @author fbensman
 */
public class Network {

    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res The rdf resource this object wraps
     */
    private Network(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static Network createWrapper(RDFResource res) {
        String owner = res.findLiterals(Vocabulary.Owner)[0];
        String domain = res.findLiterals(Vocabulary.Domain)[0];

        if (owner != null && domain != null) {
            return new Network(res);
        }
        return null;
    }

    public String getOwner() {
        String[] owner = res.findLiterals(Vocabulary.Owner);
        if (owner.length == 1) {
            return owner[0];
        }
        return null;
    }

    public String getDomain() {
        String[] domain = res.findLiterals(Vocabulary.Domain);
        if (domain.length == 1) {
            return domain[0];
        }
        return null;
    }

    /**
     * Returns the available WPS servers in this network
     *
     * @return Wrapper for RDF resources that describe WPSs
     */
    public WPS[] getWPSs() {
        RDFID[] rdfids = res.findResources(Vocabulary.WPS);
        SPClient spc = SPClient.getInstance();
        WPS[] wpss = new WPS[rdfids.length];
        try {
            for (int i = 0; i < rdfids.length; i++) {
                wpss[i] = spc.getWPS(rdfids[i]);
            }
        } catch (Exception e) {
            return null;
        }
        return wpss;
    }
}

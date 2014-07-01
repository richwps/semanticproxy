/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.Vocabulary;

/**
 * Wraps an RDFResource object that represents a wps server. This wrapper
 * abstracts the rdf handling
 *
 * @author fbensman
 */
public class WPS {

    private RDFResource res = null;

    private WPS(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static WPS createWrapper(RDFResource res) {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.WPSClass)) {
                return new WPS(res);
            }
        }
        return null;
    }

    public String getEndpoint() {
        String[] endpoint = res.findLiterals(Vocabulary.Endpoint);
        if (endpoint.length == 1) {
            return endpoint[0];
        }
        return null;
    }

    /**
     * Returns the available WPS processes for this WPS server
     *
     * @return Wrapper for RDF resources that describe processes
     */
    public Process[] getProcesses() {

        RDFID[] rdfids = res.findResources(Vocabulary.Process);
        SPClient spc = SPClient.getInstance();
        Process[] processes = new Process[rdfids.length];
        try {
            for (int i = 0; i < rdfids.length; i++) {
                processes[i] = spc.getProcess(rdfids[i]);
            }
        } catch (Exception e) {
            return null;
        }
        return processes;
    }
}

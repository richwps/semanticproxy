/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.SPClient;
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
    public static WPS createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.WPSClass)) {
                return new WPS(res);
            }
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + type.length + " type-attributes");
    }

    private String getSingleAttribute(String pred) throws RDFException {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + val.length + " " + pred + "-attributes");
    }

    public String getEndpoint() throws RDFException {
        return getSingleAttribute(Vocabulary.Endpoint);
    }

    /**
     * Returns the available WPS processes for this WPS server
     *
     * @return Wrapper for RDF resources that describe processes
     */
    public Process[] getProcesses() throws Exception{

        RDFID[] rdfids = res.findResources(Vocabulary.Process);
        SPClient spc = SPClient.getInstance();
        Process[] processes = new Process[rdfids.length];
        try {
            for (int i = 0; i < rdfids.length; i++) {
                processes[i] = spc.getProcess(rdfids[i]);
            }
        } catch (Exception e) {
            throw new Exception("Unable to get processes: ",e);
        }
        return processes;
    }
}

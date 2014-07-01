/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ResourceNotFoundException;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.Vocabulary;

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
    public static Network createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.NetworkClass)) {
                return new Network(res);
            }
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+type.length+" type-attributes");
    }
    
    
    
    
    
    
    private String getSingleAttribute(String pred) throws RDFException {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+pred+"-attributes");
    }
    

    public String getOwner() throws RDFException{
        return getSingleAttribute(Vocabulary.Owner);
    }

    public String getDomain() throws RDFException{
       return getSingleAttribute(Vocabulary.Domain);
    }

    /**
     * Returns the available WPS servers in this network
     *
     * @return Wrapper for RDF resources that describe WPSs
     */
    public WPS[] getWPSs() throws ResourceNotFoundException, InternalSPException, CommunicationException, RDFException{
        RDFID[] rdfids = res.findResources(Vocabulary.WPS);
        SPClient spc = SPClient.getInstance();
        WPS[] wpss = new WPS[rdfids.length];
       
        for (int i = 0; i < rdfids.length; i++) {
            wpss[i] = spc.getWPS(rdfids[i]);
        }
        
        return wpss;
    }
}

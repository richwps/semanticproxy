/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

/**
 *
 * @author fbensman
 */
public class WFS {
    
    private RDFResource res = null;

    private WFS(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static WFS createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.WFSClass)) {
                return new WFS(res);
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
     * Returns the WPS-T endpoint if available
     * @return The WPS-T endpoint if available else null
     * @throws RDFException 
     */
    public String getWFSVersion() throws RDFException{
        return getSingleAttribute(Vocabulary.WFSVersion);
    }
    
    
    /**
     * Returns the available WPS featureTypes for this WPS server
     *
     * @return Wrapper for RDF resources that describe featureTypes
     */
    public FeatureType[] getFeatureTypes() throws Exception{

        RDFID[] rdfids = res.findResources(Vocabulary.FeatureType);
        SPClient spc = SPClient.getInstance();
        FeatureType[] featureTypes = new FeatureType[rdfids.length];
        try {
            for (int i = 0; i < rdfids.length; i++) {
                featureTypes[i] = spc.getFeatureType(rdfids[i]);
            }
        } catch (Exception e) {
            throw new Exception("Unable to get feature types: ",e);
        }
        return featureTypes;
    }
}

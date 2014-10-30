/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

/**
 *
 * @author fbensman
 */
public class FeatureType {
    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res Resource to wrap
     */
    private FeatureType(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static FeatureType createWrapper(RDFResource res) throws RDFException{
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.FeatureTypeClass)) {
                return new FeatureType(res);
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

    public String getName() throws RDFException{
        return getSingleAttribute(Vocabulary.FeatureTypeName);
    }

    
}

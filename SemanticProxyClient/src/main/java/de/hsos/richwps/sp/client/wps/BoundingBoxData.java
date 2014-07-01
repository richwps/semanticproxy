/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

/**
 *
 * @author fbensman
 */
public class BoundingBoxData extends InAndOutputForm {

    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res Resource to wrap
     */
    private BoundingBoxData(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static BoundingBoxData createWrapper(RDFResource res) {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.BoundingBoxDataClass)) {
                return new BoundingBoxData(res);
            }
        }
        return null;
    }

    private String getSingleAttribute(String pred) {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        return null;
    }

    @Override
    public int getDataType() {
        return BOUNDING_BOX_TYPE;
    }
    //TODO: Make further attributes accessible
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.posttypes;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.wps.Vocabulary;
import static de.hsos.richwps.sp.client.wps.gettypes.InAndOutputForm.LITERAL_TYPE;

/**
 * Mutable class that represents a Complex description
 *
 * @author fbensman
 */
public class PostComplexData extends PostInAndOutputForm {

    @Override
    public int getDataType() {
        return COMPLEX_TYPE;
    }
    private RDFID rdfId = null;

    public PostComplexData(RDFID rdfId) {
        this.rdfId = rdfId;

    }

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ComplexDataClass));
        res.setResources(new ResourceExpression[]{rexp});

        return res;
    }

    public RDFID getRdfId() {
        return rdfId;
    }
}

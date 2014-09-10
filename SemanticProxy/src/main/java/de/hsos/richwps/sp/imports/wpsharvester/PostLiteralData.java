/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import de.hsos.richwps.sp.restlogic.Vocabulary;





/**
 * Mutable class that represents a literal data description
 *
 * @author fbensman
 */
public class PostLiteralData extends PostInAndOutputForm {

    @Override
    public int getDataType() {
        return LITERAL_TYPE;
    }
    private RDFID rdfId = null;

    public PostLiteralData(RDFID rdfId) {
        this.rdfId = rdfId;

    }

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.LiteralDataClass));
        res.setResources(new ResourceExpression[]{rexp});

        return res;
    }

    public RDFID getRdfId() {
        return rdfId;
    }
}

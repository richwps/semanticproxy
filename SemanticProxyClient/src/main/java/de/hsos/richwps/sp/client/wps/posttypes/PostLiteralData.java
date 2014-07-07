/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.posttypes;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.wps.Vocabulary;

/**
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

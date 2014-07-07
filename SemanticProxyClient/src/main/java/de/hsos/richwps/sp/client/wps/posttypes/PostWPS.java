/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.posttypes;

import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.wps.Vocabulary;
import java.net.URL;

/**
 *
 * @author fbensman
 */
public class PostWPS {
    
    private URL endpoint = null;
    private RDFID rdfId = null;
    
    public PostWPS(URL endpoint, RDFID rdfId){
        this.endpoint = endpoint;
        this.rdfId=rdfId;
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }
    
    
    
    
    public RDFResource toRDFResource(){
        RDFResource res = new RDFResource(rdfId);
        LiteralExpression lexp = new LiteralExpression(Vocabulary.Endpoint, endpoint.toString());
        res.setFields(new LiteralExpression[]{lexp});
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.WPSClass));
        res.setResources(new ResourceExpression[]{rexp});
        return res;
    }
    
    
}

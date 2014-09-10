/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;



import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;

/**
 * Mutable class that represents a wps description
 *
 * @author fbensman
 */
public class PostWPS {

    private URL endpoint = null;
    private RDFID rdfId = null;

    public PostWPS(URL endpoint, RDFID rdfId) {
        this.endpoint = endpoint;
        this.rdfId = rdfId;
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        LiteralExpression lexp = new LiteralExpression(Vocabulary.Endpoint, endpoint.toString());
        res.setFields(new LiteralExpression[]{lexp});
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.WPSClass));
        res.setResources(new ResourceExpression[]{rexp});
        return res;
    }
}

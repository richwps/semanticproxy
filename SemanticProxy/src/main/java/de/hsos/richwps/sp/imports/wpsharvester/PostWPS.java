/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;



import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents a wps description
 *
 * @author fbensman
 */
public class PostWPS {

    private URL endpoint = null;
    private URL wpstEndpoint = null;
    private RDFID rdfId = null;

    public PostWPS(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public URL getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URL endpoint) {
        this.endpoint = endpoint;
    }

    public URL getWpstEndpoint() {
        return wpstEndpoint;
    }

    public void setWpstEndpoint(URL wpstEndpoint) {
        this.wpstEndpoint = wpstEndpoint;
    }
    
    
    

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        ArrayList<LiteralExpression> lexpList = new ArrayList<LiteralExpression>();
        LiteralExpression lexpEndpoint = new LiteralExpression(Vocabulary.Endpoint, endpoint.toString());
        lexpList.add(lexpEndpoint);
        if(wpstEndpoint != null){
            LiteralExpression lexpWPSTEndpoint = new LiteralExpression(Vocabulary.WPSTEndpoint, wpstEndpoint.toString());
            lexpList.add(lexpWPSTEndpoint);
        }
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.WPSClass));
        res.setResources(new ResourceExpression[]{rexp});
        return res;
    }
}

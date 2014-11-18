/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.posttypes;



import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.ows.EIDType;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents a wps description
 *
 * @author fbensman
 */
public class PostWPS {

    private URL endpoint = null;
    private URL richWPSEndpoint = null;
    private RDFID rdfId = null;

    public PostWPS() throws MalformedURLException, BadRequestException, InternalSPException, CommunicationException {
        this.rdfId = SPClient.getInstance().requestID(EIDType.WPS);
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

    public URL getRichWPSEndpoint() {
        return richWPSEndpoint;
    }

    public void setRichWPSEndpoint(URL richWPSEndpoint) {
        this.richWPSEndpoint = richWPSEndpoint;
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
        if(richWPSEndpoint != null){
            LiteralExpression lexpRichWPSEndpoint = new LiteralExpression(Vocabulary.RichWPSEndpoint, richWPSEndpoint.toString());
            lexpList.add(lexpRichWPSEndpoint);
        }
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.WPSClass));
        res.setResources(new ResourceExpression[]{rexp});
        return res;
    }
}

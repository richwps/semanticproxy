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
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.net.MalformedURLException;

/**
 * Mutable class that represents a BoundingBoxData description
 *
 * @author fbensman
 */
public class PostBoundingBoxData extends PostInAndOutputForm {

    @Override
    public int getDataType() {
        return BOUNDING_BOX_TYPE;
    }
    private RDFID rdfId = null;

    public PostBoundingBoxData() throws MalformedURLException, BadRequestException, InternalSPException, CommunicationException {
        this.rdfId = SPClient.getInstance().requestID(EIDType.BOUNDINGBOX);

    }

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.BoundingBoxDataClass));
        res.setResources(new ResourceExpression[]{rexp});

        return res;
    }

    public RDFID getRdfId() {
        return rdfId;
    }
}

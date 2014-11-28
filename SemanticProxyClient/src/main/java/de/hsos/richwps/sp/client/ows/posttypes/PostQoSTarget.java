/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.posttypes;

import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.ows.EIDType;
import de.hsos.richwps.sp.client.ows.EUOM;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class PostQoSTarget {
    private RDFID rdfId = null;
    
    private String title = null;
    private String bstract = null;
    private double ideal = Double.NaN;
    private EUOM uom = EUOM.UNDEFINED;
    private double max = Double.NaN;
    private double min = Double.NaN;
    private double variance = Double.NaN;
    
    
    public PostQoSTarget() throws MalformedURLException, BadRequestException, InternalSPException, CommunicationException {
        this.rdfId = SPClient.getInstance().requestID(EIDType.QOSTARGET);
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBstract() {
        return bstract;
    }

    public void setBstract(String bstract) {
        this.bstract = bstract;
    }

    public double getIdeal() {
        return ideal;
    }

    public void setIdeal(double ideal) {
        this.ideal = ideal;
    }

    public EUOM getUOM() {
        return uom;
    }

    public void setUOM(EUOM UOM) {
        this.uom = UOM;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }
    
    
     /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        ArrayList<LiteralExpression> literalList = new ArrayList<LiteralExpression>();
        ArrayList<ResourceExpression> resourceList = new ArrayList<ResourceExpression>();
        
        //Check values
        if(ideal == Double.NaN)
            throw new ArithmeticException("Ideal value has not been set.");
        if(max == Double.NaN)
            throw new ArithmeticException("Maximum value has not been set.");
        if(min == Double.NaN)
            throw new ArithmeticException("Minimum value has not been set.");
        if(variance == Double.NaN)
            throw new ArithmeticException("Variance has not been set.");
        if( !( min < ideal && ideal < max && ideal - variance > min && ideal + variance < max ) )
            throw new ArithmeticException("Values are not set properly");
        
        if (title == null) {
            throw new NullPointerException("Process title has not been set.");
        }
        LiteralExpression lexp = new LiteralExpression(Vocabulary.Title, title);
        literalList.add(lexp);
        if (bstract != null) {
            lexp = new LiteralExpression(Vocabulary.Abstract, bstract);
            literalList.add(lexp);
        }
       
        lexp = new LiteralExpression(Vocabulary.Ideal, String.valueOf(ideal));
        literalList.add(lexp);
        if (uom == EUOM.UNDEFINED) {
            throw new ArithmeticException("UOM has not been set.");
        }
        lexp = new LiteralExpression(Vocabulary.UOM, uom.name());
        literalList.add(lexp);
        lexp = new LiteralExpression(Vocabulary.Max, String.valueOf(max));
        literalList.add(lexp);
        lexp = new LiteralExpression(Vocabulary.Min, String.valueOf(min));
        literalList.add(lexp);
        lexp = new LiteralExpression(Vocabulary.Variance, String.valueOf(variance));
        literalList.add(lexp);
        
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.QoSTargetClass));
        resourceList.add(rexp);
        
        res.setFields(literalList.toArray(new LiteralExpression[literalList.size()]));
        res.setResources(resourceList.toArray(new ResourceExpression[resourceList.size()]));
        return res;
    }
    
}

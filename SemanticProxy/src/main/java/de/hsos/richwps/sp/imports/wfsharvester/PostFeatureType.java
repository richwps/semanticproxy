/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsharvester;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class PostFeatureType {
    
    private RDFID rdfId = null;
    private String name = null;
    private PostWFS wfs = null;
    
    
    public PostFeatureType(RDFID rdfId){
        this.rdfId =rdfId;
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostWFS getWfs() {
        return wfs;
    }

    public void setWfs(PostWFS wfs) {
        this.wfs = wfs;
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

        if (name == null) {
            throw new NullPointerException("FeatureType name has not been set.");
        }
        LiteralExpression lexp = new LiteralExpression(Vocabulary.FeatureTypeName, name);
        literalList.add(lexp);
        
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.FeatureTypeClass));
        resourceList.add(rexp);
        rexp = new ResourceExpression(Vocabulary.WFS, wfs.getRdfId());
        resourceList.add(rexp);

        res.setFields(literalList.toArray(new LiteralExpression[literalList.size()]));
        res.setResources(resourceList.toArray(new ResourceExpression[resourceList.size()]));
        return res;
    }
    
    
}

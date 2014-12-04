/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsharvester;




import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents a wps description
 *
 * @author fbensman
 */
public class PostWFS {

    private URL endpoint = null;
    private RDFID rdfId = null;
    private String version = null;
    private ArrayList<PostFeatureType> featureTypeList = null;
    

    public PostWFS() throws MalformedURLException {
        
    }
    
    
    /**
     * Constructs a PostWPS with a given RDFID
     * @param rdfid 
     */
    public PostWFS(RDFID rdfid) {
        this.rdfId = rdfid;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<PostFeatureType> getFeatureTypeList() {
        return featureTypeList;
    }

    public void setFeatureTypeList(ArrayList<PostFeatureType> featureTypeList) {
        this.featureTypeList = featureTypeList;
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
        
        lexpEndpoint = new LiteralExpression(Vocabulary.WFSVersion, version);
        lexpList.add(lexpEndpoint);
        
        
        ArrayList<ResourceExpression> rexpList = new ArrayList<ResourceExpression>();
        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.WFSClass));
        rexpList.add(rexp);
        
        for(PostFeatureType ft : featureTypeList){
            rexp = new ResourceExpression(Vocabulary.FeatureType, ft.getRdfId());
            rexpList.add(rexp);
        }
        
        res.setFields(lexpList.toArray(new LiteralExpression[lexpList.size()]));
        res.setResources(rexpList.toArray(new ResourceExpression[rexpList.size()]));
        return res;
    }
}

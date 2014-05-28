/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class RDFResource {
    
 
    private RDFID rdfID = null;
    private LiteralExpression[] fields = null;
    private ResourceExpression[] resources = null;
    
    
    public RDFResource(RDFID rdfID){
        this.rdfID = rdfID;
        
    }

    public RDFID getRdfID() {
        return rdfID;
    }

    public void setRdfID(RDFID rdfID) {
        this.rdfID = rdfID;
    }

    public LiteralExpression[] getFields() {
        return fields;
    }

    public void setFields(LiteralExpression[] fields) {
        this.fields = fields;
    }

    public ResourceExpression[] getResources() {
        return resources;
    }

    public void setResources(ResourceExpression[] resources) {
        this.resources = resources;
    }
    
    protected String[] findLiterals(String predicate){
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0; i< fields.length; i++){
            if(fields[i].predicate.equals(predicate)){
                list.add( fields[i].literal );
            }
        }
        if(list.size()==0)
            return null;
        return list.toArray(new String[list.size()]);
    }
    
    
    protected RDFID[] findResources(String predicate){
        ArrayList<RDFID> list = new ArrayList<RDFID>();
        for(int i=0; i< resources.length; i++){
            if(resources[i].predicate.equals(predicate)){
                list.add(resources[i].rdfID);
            }
        }
        if(list.size()==0)
            return null;
        return list.toArray(new RDFID[list.size()]);
    }
    
    
    
}

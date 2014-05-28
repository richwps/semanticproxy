/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.restlogic.Vocabulary;



/**
 *
 * @author fbensman
 */
public class Network {
    
    private RDFResource res = null;
   
    private Network(RDFResource res){
        this.res = res;
    }
    
    public static Network createWrapper(RDFResource res){
        String owner = res.findLiterals(Vocabulary.Owner)[0];
        String domain = res.findLiterals(Vocabulary.Domain)[0];
        
        if(owner != null && domain != null){
            return new Network(res);
        }
        return null;
    }
    
    
    public String getOwner(){
        String[] owner = res.findLiterals(Vocabulary.Owner);
        if(owner.length == 1)
            return owner[0];
        return null;
    }
    
    
    public String getDomain(){
        String[] domain = res.findLiterals(Vocabulary.Domain);
        if(domain.length == 1)
            return domain[0];
        return null;
    }
    
    public WPS[] getWPSs(){
        RDFID[] rdfids = res.findResources(Vocabulary.WPS);
        SPClient spc = SPClient.getInstance();
        WPS[] wpss = new WPS[rdfids.length];
        try{
            for(int i=0; i<rdfids.length;i++){
               wpss[i] = spc.getWPS(rdfids[i]);
            }
        }catch(Exception e){
            return null;
        }
        return wpss;
    }
    
    
}

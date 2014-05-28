/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.client.Vocabulary;

/**
 *
 * @author fbensman
 */
public class WPS {
    private RDFResource res = null;
   
    private WPS(RDFResource res){
        this.res = res;
    }
    
    public static WPS createWrapper(RDFResource res){
        RDFID[] type = res.findResources(Vocabulary.Type);
        if(type.length == 1){
            if(type[0].rdfID.equals(Vocabulary.WPSClass)){
                return new WPS(res);
            }
        }
        return null;
    }
    
    
    public String getEndpoint(){
        String[] endpoint = res.findLiterals(Vocabulary.Endpoint);
        if(endpoint.length == 1)
            return endpoint[0];
        return null;
    }
    
    
    public Process[] getProcesses(){
        
        RDFID[] rdfids = res.findResources(Vocabulary.Process);
        SPClient spc = SPClient.getInstance();
        Process[] processes = new Process[rdfids.length];
        try{
            for(int i=0; i<rdfids.length;i++){
               processes[i] = spc.getProcess(rdfids[i]);
            }
        }catch(Exception e){
            return null;
        }
        return processes;
    }
}

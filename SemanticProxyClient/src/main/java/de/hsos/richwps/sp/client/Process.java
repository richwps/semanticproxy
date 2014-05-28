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
public class Process {
    
    private RDFResource res = null;
   
    private Process(RDFResource res){
        this.res = res;
    }
    
    public static Process createWrapper(RDFResource res){
        RDFID[] type = res.findResources(Vocabulary.Type);
        if(type.length == 1){
            if(type[0].rdfID.equals(Vocabulary.ProcessClass)){
                return new Process(res);
            }
        }
        return null;
    }
    
    
    private String getSingleAttribute(String pred){
        String[] val = res.findLiterals(pred);
        if(val.length == 1)
            return val[0];
        return null;
    }
    
    public String getIdentifier(){
        return getSingleAttribute(Vocabulary.Identifier);
    }
    
    public String getTitle(){
        return getSingleAttribute(Vocabulary.Title);
    }
    
    public String getAbstract(){
        return getSingleAttribute(Vocabulary.Abstract);
    }
    
    public String getProcessVersion(){
        return getSingleAttribute(Vocabulary.ProcessVersion);
    }
    
    
}

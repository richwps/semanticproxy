/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.rdf.RDFClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import java.net.URL;

/**
 *
 * @author fbensman
 */
public class Output {
    
    
     private RDFResource res = null;

    private Output(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static Output createWrapper(RDFResource res) {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ProcessOutputClass)) {
                return new Output(res);
            }
        }
        return null;
    }
    
    
    
    private String getSingleAttribute(String pred) {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        return null;
    }

    public String getIdentifier() {
        return getSingleAttribute(Vocabulary.Identifier);
    }

    public String getTitle() {
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() {
        return getSingleAttribute(Vocabulary.Abstract);
    }
    
   
    public URL getMetadata(){
        String tmp = getSingleAttribute(Vocabulary.Metadata);
        if(tmp == null)
            return null;
        else{
            URL md = null;
            try{
                md = new URL(tmp);
            }catch(Exception e){
                return null;
            }
            return md;
        }
    }
    
    
    public InAndOutputForm getOutputFormChoice(){
        //get the statement about the input form choice
        RDFID[] ofc = res.findResources(Vocabulary.OutputFormChoice);
        if (ofc.length == 1) { //if there is only one...
            SPClient spc = SPClient.getInstance();
            RDFClient rdfc = spc.getRdfClient();
            try{
                //get the data type resource... 
                RDFResource oufoch = rdfc.retrieveResource(ofc[0]);
                //determine its type (complex, literal, ...)
                RDFID[] type = oufoch.findResources(Vocabulary.Type);
                if(type[0].rdfID.equals(Vocabulary.ComplexDataClass))
                    return spc.getComplexData(ofc[0]);
                else if (type[0].rdfID.equals(Vocabulary.LiteralDataClass))
                    return spc.getLiteralData(ofc[0]);
                else
                    return spc.getBoundingBoxData(ofc[0]);
            }catch(Exception e){
                return null;
            }
  
        }
        return null;
    }
}

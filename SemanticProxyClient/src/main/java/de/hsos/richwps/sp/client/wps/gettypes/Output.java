/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.gettypes;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.rdf.RDFClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.SPClient;
import de.hsos.richwps.sp.client.wps.Vocabulary;
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
    public static Output createWrapper(RDFResource res) throws RDFException{
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ProcessOutputClass)) {
                return new Output(res);
            }
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+type.length+" type-attributes");
    }
    
    
    
    private String getSingleAttribute(String pred) throws RDFException {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+pred+"-attributes");
    }

    public String getIdentifier() throws RDFException{
        return getSingleAttribute(Vocabulary.Identifier);
    }

    public String getTitle() throws RDFException{
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() throws RDFException{
        return getSingleAttribute(Vocabulary.Abstract);
    }
    
   
    public URL getMetadata()throws RDFException{
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
    
    
    public InAndOutputForm getOutputFormChoice() throws RDFException, CommunicationException, BadRequestException, InternalSPException{
        //get the statement about the input form choice
        RDFID[] ofc = res.findResources(Vocabulary.OutputFormChoice);
        if (ofc.length == 1) { //if there is only one...
            SPClient spc = SPClient.getInstance();
            RDFClient rdfc = spc.getRdfClient();
            
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
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+ofc.length+" OutputFormChoices");
    }
}

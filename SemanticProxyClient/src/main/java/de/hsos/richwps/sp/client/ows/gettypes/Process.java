/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wraps an RDFResource object that represents a wps process. This wrapper
 * abstracts the rdf handling
 *
 * @author fbensman
 */
public class Process {

    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res Resource to wrap
     */
    private Process(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static Process createWrapper(RDFResource res) throws RDFException{
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ProcessClass)) {
                return new Process(res);
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

    
    public WPS getWPS() throws Exception{
        RDFID[] rdfids = res.findResources(Vocabulary.WPS);
        SPClient spc = SPClient.getInstance();
        if(rdfids.length != 1)
            throw new Exception("Incorrect count of WPS properties"+res.getRdfID().rdfID);
        WPS wps = spc.getWPS(rdfids[0]);
        return wps;
    }
    
    
    public String getIdentifier() throws RDFException{
        return getSingleAttribute(Vocabulary.Identifier);
    }

    public String getTitle() throws RDFException{
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() throws RDFException {
        String[] abstracts = res.findLiterals(Vocabulary.Abstract);
        if(abstracts.length == 0)
            return "";
        return abstracts[0];
    }

    public String getProcessVersion() throws RDFException{
        return getSingleAttribute(Vocabulary.ProcessVersion);
    }
    
    
    public URL[] getMetadata() throws RDFException {
            String[] metadata = res.findLiterals(Vocabulary.Metadata);
            URL[] urls = new URL[metadata.length];
            for(int i=0; i<metadata.length;i++){
                try{
                    urls[i]= new URL(metadata[i]);
                }catch(MalformedURLException murle){
                    throw new RDFException("Malformed URL in metadata found: "+metadata[i],murle);
                }
            }
            return urls;
        }
    
   
    

    public Input[] getInputs() throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFID[] rdfids = res.findResources(Vocabulary.Input);
        Input[] inputs = new Input[rdfids.length];
        SPClient spc = SPClient.getInstance();

        for (int i = 0; i < rdfids.length; i++) {
            inputs[i] = spc.getInput(rdfids[i]);
        }

        return inputs;
    }

    public Output[] getOutputs() throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFID[] rdfids = res.findResources(Vocabulary.Output);
        Output[] outputs = new Output[rdfids.length];
        SPClient spc = SPClient.getInstance();
        for (int i = 0; i < rdfids.length; i++) {
            outputs[i] = spc.getOutput(rdfids[i]);
        }
        return outputs;
    }
    
    
    public QoSTarget[] getQoSTargets() throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFID[] rdfids = res.findResources(Vocabulary.QoSTarget);
        QoSTarget[] target = new QoSTarget[rdfids.length];
        SPClient spc = SPClient.getInstance();
        for (int i = 0; i < rdfids.length; i++) {
            target[i] = spc.getQoSTarget(rdfids[i]);
        }
        return target;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

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
    public static Process createWrapper(RDFResource res) {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ProcessClass)) {
                return new Process(res);
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

    public String getProcessVersion() {
        return getSingleAttribute(Vocabulary.ProcessVersion);
    }
    
    public Input[] getInputs(){
        RDFID[] rdfids = res.findResources(Vocabulary.Input);
        Input[] inputs = new Input[rdfids.length];
        SPClient spc = SPClient.getInstance();
        
        try {
            for (int i = 0; i < rdfids.length; i++) {
                inputs[i] = spc.getInput(rdfids[i]);
            }
        } catch (Exception e) {
            return null;
        }
        return inputs;
    }
    
    
    public Output[] getOutputs(){
        RDFID[] rdfids = res.findResources(Vocabulary.Output);
        Output[] outputs = new Output[rdfids.length];
        SPClient spc = SPClient.getInstance();
        
        try {
            for (int i = 0; i < rdfids.length; i++) {
                outputs[i] = spc.getOutput(rdfids[i]);
            }
        } catch (Exception e) {
            return null;
        }
        return outputs;
    }
    
    
        
        
        
        
    
}

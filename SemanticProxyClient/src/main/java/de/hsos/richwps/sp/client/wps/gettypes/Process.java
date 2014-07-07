/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.gettypes;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.SPClient;
import de.hsos.richwps.sp.client.wps.Vocabulary;

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

    public String getIdentifier() throws RDFException{
        return getSingleAttribute(Vocabulary.Identifier);
    }

    public String getTitle() throws RDFException{
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() throws RDFException{
        return getSingleAttribute(Vocabulary.Abstract);
    }

    public String getProcessVersion() throws RDFException{
        return getSingleAttribute(Vocabulary.ProcessVersion);
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
}

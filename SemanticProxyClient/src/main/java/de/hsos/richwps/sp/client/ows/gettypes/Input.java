/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.rdf.RDFClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author fbensman
 */
public class Input {

    private RDFResource res = null;

    private Input(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static Input createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.DataInputClass)) {
                return new Input(res);
            }
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + type.length + " type-attributes");
    }

    private String getSingleAttribute(String pred) throws RDFException {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + val.length + " " + pred + "-attributes");
    }

    public String getIdentifier() throws RDFException {
        return getSingleAttribute(Vocabulary.Identifier);
    }

    public String getTitle() throws RDFException {
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() throws RDFException {
        String[] abstracts = res.findLiterals(Vocabulary.Abstract);
        if(abstracts.length == 0)
            return "";
        return abstracts[0];
    }

    public int getMinOccurs() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.MinOccurs);
        return Integer.valueOf(tmp);
    }

    public int getMaxOccurs() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.MaxOccurs);
        return Integer.valueOf(tmp);
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

    public InAndOutputForm getInputFormChoice() throws RDFException, CommunicationException, BadRequestException, InternalSPException {
        //get the statement about the input form choice
        RDFID[] ifc = res.findResources(Vocabulary.InputFormChoice);
        if (ifc.length == 1) { //if there is only one...
            SPClient spc = SPClient.getInstance();
            RDFClient rdfc = spc.getRdfClient();

            //get the data type resource... 
            RDFResource infoch = rdfc.retrieveResource(ifc[0]);
            //determine its type (complex, literal, ...)
            RDFID[] type = infoch.findResources(Vocabulary.Type);
            if (type[0].rdfID.equals(Vocabulary.ComplexDataClass)) {
                return spc.getComplexData(ifc[0]);
            } else if (type[0].rdfID.equals(Vocabulary.LiteralDataClass)) {
                return spc.getLiteralData(ifc[0]);
            } else if (type[0].rdfID.equals(Vocabulary.BoundingBoxDataClass)){
                return spc.getBoundingBoxData(ifc[0]);
            }
            else
                throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found invalid InputFormChoice:" +type[0].rdfID);
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + ifc.length + " InputFormChoices");
    }
}

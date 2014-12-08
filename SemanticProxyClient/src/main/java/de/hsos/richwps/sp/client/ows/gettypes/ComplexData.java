/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.math.BigInteger;

/**
 *
 * @author fbensman
 */
public class ComplexData extends InAndOutputForm {

    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res Resource to wrap
     */
    private ComplexData(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static ComplexData createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ComplexDataClass)) {
                return new ComplexData(res);
            }
        }
        throw new RDFException("Resource " + res.getRdfID().rdfID + "malformed. Found " + type.length + " type-attributes");
    }

    @Override
    public int getDataType() {
        return COMPLEX_TYPE;
    }
   

   
    /**
     * Returns the MaximumMegabytes for this ComplexData, can be null
     * If the object belongs to an output getMaximumMegabytes returns always null
     * @return
     * @throws RDFException 
     */
    public BigInteger getMaximumMegabytes() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.MaximumMegabytes);
        if(val.length == 1){
            return new BigInteger(val[0]);
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.MaximumMegabytes+"-attributes");
        }
        else //0
            return null;
    }
    
    
    /**
     * Returns the default format for this ComplexData
     * @return
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public ComplexDataCombination getDefaultFormat() throws RDFException, BadRequestException, InternalSPException, CommunicationException{
        RDFID[] rdfids = res.findResources(Vocabulary.DefaultFormat);
        if(rdfids.length != 1)
            throw new RDFException("Incorrect count of defaults found in "+ res.getRdfID().rdfID+ ", found: "+rdfids.length);                 
        SPClient spc = SPClient.getInstance();
        ComplexDataCombination defaultCDC = spc.getComplexDataCombination(rdfids[0]);
        
        return defaultCDC;
    }
    
    
    /**
     * Returns the supported formats for this ComplexData
     * @return
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public ComplexDataCombination[] getSupportedFormats() throws RDFException, BadRequestException, InternalSPException, CommunicationException{
        RDFID[] rdfids = res.findResources(Vocabulary.SupportedFormats);
        ComplexDataCombination[] cdcArr = new ComplexDataCombination[rdfids.length];
        SPClient spc = SPClient.getInstance();

        for (int i = 0; i < rdfids.length; i++) {
            cdcArr[i] = spc.getComplexDataCombination(rdfids[i]);
        }

        return cdcArr;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import java.math.BigInteger;

/**
 *
 * @author fbensman
 */
public class ComplexDataCombination {
    
    private RDFResource res = null;
    
    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static ComplexDataCombination createWrapper(RDFResource res) throws RDFException{
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.ComplexDataCombinationClass)) {
                return new ComplexDataCombination(res);
            }
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+type.length+" type-attributes");
    }

    
    private ComplexDataCombination(RDFResource res) {
        this.res = res;
    }
    
    
    
    private String getSingleAttribute(String pred) throws RDFException {
        String[] val = res.findLiterals(pred);
        if (val.length == 1) {
            return val[0];
        }
        throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+pred+"-attributes");
    }

   
    /**
     * Returns a string representing the encoding of this format, can be null
     * @return
     * @throws RDFException 
     */
    public String getEncoding() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.Encoding);
        if (val.length == 1) {
            return val[0];
        }
        else if(val.length == 0)
            return null;
        else
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.Encoding+"-attributes");
    }
    
    
    /**
     * Returns a string representing the mimetype of this format
     * @return
     * @throws RDFException 
     */
    public String getMimeType() throws RDFException{
        return getSingleAttribute(Vocabulary.MimeType);
    }
    
    
    /**
     * Returns a string representing the schema of this format, can be null
     * @return
     * @throws RDFException 
     */
    public String getSchema() throws RDFException{
         String[] val = res.findLiterals(Vocabulary.Schema);
        if (val.length == 1) {
            return val[0];
        }
        else if(val.length == 0)
            return null;
        else
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.Schema+"-attributes");
    }
    
}

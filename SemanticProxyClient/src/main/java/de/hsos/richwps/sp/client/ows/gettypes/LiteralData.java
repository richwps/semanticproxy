/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import java.math.BigInteger;

/**
 *
 * @author fbensman
 */
public class LiteralData extends InAndOutputForm {

    private RDFResource res = null;

    /**
     * Ctor
     *
     * @param res Resource to wrap
     */
    private LiteralData(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static LiteralData createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.LiteralDataClass)) {
                return new LiteralData(res);
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

    @Override
    public int getDataType() {
        return LITERAL_TYPE;
    }
    
    
    
    /**
     * Returns the data type for this LiteralData, can be null
     * @return
     * @throws RDFException 
     */
    public String getLitealDataType() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.LiteralDataType);
        if(val.length == 1){
            return val[0];
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.LiteralDataType+" attributes");
        }
        else //0
            return null;
    }
    
    
    /**
     * Returns the ValuesReference attribute for this LiteralData, can be null
     * @return
     * @throws RDFException 
     */
    public String getValuesReference() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.ValuesRefernce);
        if(val.length == 1){
            return val[0];
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.ValuesRefernce+" attributes");
        }
        else //0
            return null;
    }
    
    
    /**
     * Returns the ValuesForm attribute for this LiteralData, can be null
     * @return
     * @throws RDFException 
     */
    public String getValuesForm() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.ValuesForm);
        if(val.length == 1){
            return val[0];
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.ValuesForm+" attributes");
        }
        else //0
            return null;
    }
    
    
    /**
     * Returns if the AnyValue attribute for this LiteralData is set, 
     * always false in outputs
     * @return true if any value is allowed, false otherwise
     * @throws RDFException 
     */
    public boolean isSetAnyValue() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.AnyValue);
        if(val.length == 1){
            boolean b = Boolean.valueOf(val[0]);
            return b;
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.AnyValue+" attributes");
        }
        return false;
    }
    
    
    /**
     * Returns if the AllowedValues attribute for this LiteralData is set,
     * the values themselves are currently not available, 
     * always false in outputs
     * @return true if some predefined values are allowed, false otherwise
     * @throws RDFException 
     */
    public boolean isSetAllowedValues() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.AllowedValues);
        if(val.length == 1){
            boolean b = Boolean.valueOf(val[0]);
            return b;
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.AllowedValues+" attributes");
        }
        return false;
    }
    
    
    /**
     * Returns the DefaultValue attribute for this LiteralData, can be null, 
     * always null in outputs
     * @return
     * @throws RDFException 
     */
    public String getDefaultValue() throws RDFException{
        String[] val = res.findLiterals(Vocabulary.DefaultValue);
        if(val.length == 1){
            return val[0];
        }
        else if (val.length > 1) {
            throw new RDFException("Resource "+ res.getRdfID().rdfID +"malformed. Found "+val.length+" "+Vocabulary.DefaultValue+" attributes");
        }
        else //0
            return null;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.ows.gettypes;

import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.ows.EUOM;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

/**
 *
 * @author fbensman
 */
public class QoSTarget {
    
    
    private RDFResource res = null;

    private QoSTarget(RDFResource res) {
        this.res = res;
    }

    /**
     * Creates a wrapper for the specified resource if possible
     *
     * @param res Resource to wrap
     * @return The wrapper, null if the resource is not a network objekt
     */
    public static QoSTarget createWrapper(RDFResource res) throws RDFException {
        RDFID[] type = res.findResources(Vocabulary.Type);
        if (type.length == 1) {
            if (type[0].rdfID.equals(Vocabulary.QoSTargetClass)) {
                return new QoSTarget(res);
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

    

    public String getTitle() throws RDFException {
        return getSingleAttribute(Vocabulary.Title);
    }

    public String getAbstract() throws RDFException {
        String[] abstracts = res.findLiterals(Vocabulary.Abstract);
        if(abstracts.length == 0)
            return "";
        return abstracts[0];
    }
    
    public double getIdeal() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.Ideal);
        return Double.valueOf(tmp);
    }
    
    public EUOM getUOM() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.UOM);
        return EUOM.valueOf(tmp);
    }

    public double getMin() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.Min);
        return Double.valueOf(tmp);
    }

    public double getMax() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.Max);
        return Double.valueOf(tmp);
    }
    
    public double getVariance() throws RDFException {
        String tmp = getSingleAttribute(Vocabulary.Variance);
        return Double.valueOf(tmp);
    }
    
    
    

    
}

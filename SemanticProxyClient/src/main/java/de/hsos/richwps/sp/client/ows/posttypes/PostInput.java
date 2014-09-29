/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.posttypes;

import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.wps.gettypes.InAndOutputForm;
import de.hsos.richwps.sp.client.wps.Vocabulary;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents an input description
 *
 * @author fbensman
 */
public class PostInput {

    private RDFID rdfId = null;
    private String identifier = null;
    private String title = null;
    private String bstract = null;
    private int minOcc = -1;
    private int maxOcc = -1;
    private ArrayList<URL> metadataList = null;
    private PostInAndOutputForm inputFormChoice = null;

    public PostInput(RDFID rdfId) {
        this.rdfId = rdfId;

    }

    /**
     * Creates an RDFResource from object
     *
     * @return
     */
    public RDFResource toRDFResource() {
        RDFResource res = new RDFResource(rdfId);
        ArrayList<LiteralExpression> literalList = new ArrayList<LiteralExpression>();
        ArrayList<ResourceExpression> resourceList = new ArrayList<ResourceExpression>();

        if (identifier == null) {
            throw new NullPointerException("Process identifier has not been set.");
        }
        LiteralExpression lexp = new LiteralExpression(Vocabulary.Identifier, identifier);
        literalList.add(lexp);
        if (title == null) {
            throw new NullPointerException("Process title has not been set.");
        }
        lexp = new LiteralExpression(Vocabulary.Title, title);
        literalList.add(lexp);
        if (bstract != null) {
            lexp = new LiteralExpression(Vocabulary.Abstract, bstract);
            literalList.add(lexp);
        }

        if (metadataList != null) {
            for (URL md : metadataList) {
                lexp = new LiteralExpression(Vocabulary.Metadata, md.toString());
                literalList.add(lexp);
            }
        }
        if (minOcc < 0) {
            throw new ArithmeticException("No negativ values for min. occurence in input " + identifier + " permitted");
        }
        lexp = new LiteralExpression(Vocabulary.MinOccurs, "" + minOcc);
        literalList.add(lexp);
        if (maxOcc < 0) {
            throw new ArithmeticException("No negativ values for max. occurence in input " + identifier + " permitted");
        }
        if (maxOcc < minOcc) {
            throw new ArithmeticException("Max. occurence needs to be larger than min. occurence in input " + identifier + " permitted");
        }
        lexp = new LiteralExpression(Vocabulary.MaxOccurs, "" + maxOcc);
        literalList.add(lexp);
        res.setFields(literalList.toArray(new LiteralExpression[literalList.size()]));

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.DataInputClass));
        resourceList.add(rexp);

        if (inputFormChoice == null) {
            throw new NullPointerException("Data type has not been set for input " + identifier);
        }
        if (inputFormChoice.getDataType() == InAndOutputForm.LITERAL_TYPE) {
            PostLiteralData literal = (PostLiteralData) inputFormChoice;
            rexp = new ResourceExpression(Vocabulary.InputFormChoice, literal.getRdfId());
        } else if (inputFormChoice.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
            PostComplexData complex = (PostComplexData) inputFormChoice;
            rexp = new ResourceExpression(Vocabulary.InputFormChoice, complex.getRdfId());
        } else {
            PostBoundingBoxData boundingBox = (PostBoundingBoxData) inputFormChoice;
            rexp = new ResourceExpression(Vocabulary.InputFormChoice, boundingBox.getRdfId());
        }
        resourceList.add(rexp);
        res.setResources(resourceList.toArray(new ResourceExpression[resourceList.size()]));


        return res;
    }

    public RDFID getRdfId() {
        return rdfId;
    }

    public void setRdfId(RDFID rdfId) {
        this.rdfId = rdfId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBstract() {
        return bstract;
    }

    public void setBstract(String bstract) {
        this.bstract = bstract;
    }

    public int getMinOcc() {
        return minOcc;
    }

    public void setMinOcc(int minOcc) {
        this.minOcc = minOcc;
    }

    public int getMaxOcc() {
        return maxOcc;
    }

    public void setMaxOcc(int maxOcc) {
        this.maxOcc = maxOcc;
    }

    public ArrayList<URL> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(ArrayList<URL> metadataList) {
        this.metadataList = metadataList;
    }

    public PostInAndOutputForm getPostInputFormChoice() {
        return inputFormChoice;
    }

    public void setPostInputFormChoice(PostInAndOutputForm inputFormChoice) {
        this.inputFormChoice = inputFormChoice;
    }
}

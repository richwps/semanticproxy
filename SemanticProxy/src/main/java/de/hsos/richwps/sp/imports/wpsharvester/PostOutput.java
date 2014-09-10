/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;



import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents an output description
 *
 * @author fbensman
 */
public class PostOutput {

    private RDFID rdfId = null;
    private String identifier = null;
    private String title = null;
    private String bstract = null;
    private ArrayList<URL> metadataList = null;
    private PostInAndOutputForm outputFormChoice = null;

    public PostOutput(RDFID rdfId) {
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
        res.setFields(literalList.toArray(new LiteralExpression[literalList.size()]));

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ProcessOutputClass));
        resourceList.add(rexp);
        if (outputFormChoice == null) {
            throw new NullPointerException("Data type has not been set for input " + identifier);
        }
        if (outputFormChoice.getDataType() == InAndOutputForm.LITERAL_TYPE) {
            PostLiteralData literal = (PostLiteralData) outputFormChoice;
            rexp = new ResourceExpression(Vocabulary.OutputFormChoice, literal.getRdfId());
        } else if (outputFormChoice.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
            PostComplexData complex = (PostComplexData) outputFormChoice;
            rexp = new ResourceExpression(Vocabulary.OutputFormChoice, complex.getRdfId());
        } else {
            PostBoundingBoxData boundingBox = (PostBoundingBoxData) outputFormChoice;
            rexp = new ResourceExpression(Vocabulary.OutputFormChoice, boundingBox.getRdfId());
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

    public ArrayList<URL> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(ArrayList<URL> metadataList) {
        this.metadataList = metadataList;
    }

    public PostInAndOutputForm getPostOutputFormChoice() {
        return outputFormChoice;
    }

    public void setPostOutputFormChoice(PostInAndOutputForm outputFormChoice) {
        this.outputFormChoice = outputFormChoice;
    }
}

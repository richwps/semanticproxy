/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps.posttypes;

import de.hsos.richwps.sp.client.rdf.LiteralExpression;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.rdf.ResourceExpression;
import de.hsos.richwps.sp.client.wps.Vocabulary;
import java.net.URL;
import java.util.ArrayList;

/**
 * Mutable class that represents a process description
 *
 * @author fbensman
 */
public class PostProcess {

    private RDFID rdfId = null;
    private String identifier = null;
    private String title = null;
    private String bstract = null;
    private String processVersion = null;
    private ArrayList<URL> metadataList = null;
    private ArrayList<URL> profileList = null;
    private URL wsdl = null;
    private boolean storeSupported = false;
    private boolean useStoreSupported = false;
    private boolean statusSupported = false;
    private boolean useStatusSupported = false;
    private ArrayList<PostInput> inputs = null;
    private ArrayList<PostOutput> outputs = null;
    private PostWPS wps = null;

    public PostProcess(RDFID rdfId) {
        this.rdfId = rdfId;

    }

    public RDFID getRdfId() {
        return rdfId;
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
        if (processVersion == null) {
            throw new NullPointerException("Process version has not been set.");
        }
        lexp = new LiteralExpression(Vocabulary.ProcessVersion, processVersion);
        literalList.add(lexp);
        if (metadataList != null) {
            for (URL md : metadataList) {
                lexp = new LiteralExpression(Vocabulary.Metadata, md.toString());
                literalList.add(lexp);
            }
        }
        if (profileList != null) {
            for (URL profile : profileList) {
                lexp = new LiteralExpression(Vocabulary.Profile, profile.toString());
                literalList.add(lexp);
            }
        }
        if (wsdl != null) {
            lexp = new LiteralExpression(Vocabulary.WSDL, wsdl.toString());
            literalList.add(lexp);
        }
        if (useStoreSupported) {
            lexp = new LiteralExpression(Vocabulary.StoreSupported, Boolean.toString(storeSupported));
            literalList.add(lexp);
        }
        if (useStatusSupported) {
            lexp = new LiteralExpression(Vocabulary.StatusSupported, Boolean.toString(statusSupported));
            literalList.add(lexp);
        }
        res.setFields(literalList.toArray(new LiteralExpression[literalList.size()]));

        ResourceExpression rexp = new ResourceExpression(Vocabulary.Type, new RDFID(Vocabulary.ProcessClass));
        resourceList.add(rexp);
        for (PostInput r : inputs) {
            rexp = new ResourceExpression(Vocabulary.Input, r.getRdfId());
            resourceList.add(rexp);
        }
        for (PostOutput r : outputs) {
            rexp = new ResourceExpression(Vocabulary.Output, r.getRdfId());
            resourceList.add(rexp);
        }

        if (wps == null) {
            throw new NullPointerException("Parent WPS has not been set.");
        }
        rexp = new ResourceExpression(Vocabulary.WPS, wps.getRdfId());
        resourceList.add(rexp);

        res.setResources(resourceList.toArray(new ResourceExpression[resourceList.size()]));
        return res;
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

    public String getProcessVersion() {
        return processVersion;
    }

    public void setProcessVersion(String processVersion) {
        this.processVersion = processVersion;
    }

    public ArrayList<PostInput> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<PostInput> inputs) {
        this.inputs = inputs;
    }

    public ArrayList<PostOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(ArrayList<PostOutput> outputs) {
        this.outputs = outputs;
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

    public ArrayList<URL> getProfileList() {
        return profileList;
    }

    public void setProfileList(ArrayList<URL> profileList) {
        this.profileList = profileList;
    }

    public URL getWsdl() {
        return wsdl;
    }

    public void setWsdl(URL wsdl) {
        this.wsdl = wsdl;
    }

    public boolean isStoreSupported() {
        return storeSupported;
    }

    public void setStoreSupported(boolean storeSupported) {
        this.useStoreSupported = true;
        this.storeSupported = storeSupported;
    }

    public boolean isStatusSupported() {
        return statusSupported;
    }

    public void setStatusSupported(boolean statusSupported) {
        this.useStatusSupported = true;
        this.statusSupported = statusSupported;
    }

    public boolean isUseStoreSupported() {
        return useStoreSupported;
    }

    public void setUseStoreSupported(boolean useStoreSupported) {
        this.useStoreSupported = useStoreSupported;
    }

    public boolean isUseStatusSupported() {
        return useStatusSupported;
    }

    public void setUseStatusSupported(boolean useStatusSupported) {
        this.useStatusSupported = useStatusSupported;
    }

    public PostWPS getWps() {
        return wps;
    }

    public void setWps(PostWPS wps) {
        this.wps = wps;
    }
}

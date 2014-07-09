/*
 * To change this template, choose Tools | Templates
 * and open the template out the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.wps.gettypes.Output;
import de.hsos.richwps.sp.client.wps.gettypes.Process;
import de.hsos.richwps.sp.client.wps.gettypes.Network;
import de.hsos.richwps.sp.client.wps.gettypes.Input;
import de.hsos.richwps.sp.client.wps.gettypes.WPS;
import de.hsos.richwps.sp.client.wps.gettypes.ComplexData;
import de.hsos.richwps.sp.client.wps.gettypes.LiteralData;
import de.hsos.richwps.sp.client.wps.gettypes.BoundingBoxData;
import de.hsos.richwps.sp.client.wps.posttypes.PostWPS;
import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import de.hsos.richwps.sp.client.rdf.RDFClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;
import de.hsos.richwps.sp.client.wps.posttypes.PostBoundingBoxData;
import de.hsos.richwps.sp.client.wps.posttypes.PostComplexData;
import de.hsos.richwps.sp.client.wps.posttypes.PostInAndOutputForm;
import de.hsos.richwps.sp.client.wps.posttypes.PostInput;
import de.hsos.richwps.sp.client.wps.posttypes.PostLiteralData;
import de.hsos.richwps.sp.client.wps.posttypes.PostOutput;
import de.hsos.richwps.sp.client.wps.posttypes.PostProcess;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Client for SemanticProxy interaction, implements Singleton pattern.
 *
 * @author fbensman
 */
public class SPClient {

    private RDFClient rdfClient = null;
    private static SPClient instance = null;
    private String rootURL = "http://localhost:4567/semanticproxy/resources";
    private String searchURL = "http://localhost:4567/semanticproxy/search";
    private String wpsListURL = "http://localhost:4567/semanticproxy/resources/wpss";
    private String processListURL = "http://localhost:4567/semanticproxy/resources/processes";

    private SPClient() {
        rdfClient = new RDFClient();
    }

    /**
     * For singleton
     *
     * @return
     */
    public static SPClient getInstance() {
        if (instance == null) {
            instance = new SPClient();
        }
        return instance;
    }

    public String getRootURL() {
        return rootURL;
    }

    /**
     * Sets the URL of the root element of the SemanticProxy, usually this is
     * the network resource.
     *
     * @param rootURL
     */
    public void setRootURL(String rootURL) {
        this.rootURL = rootURL;
    }

    public String getSearchURL() {
        return searchURL;
    }

    public void setSearchURL(String searchURL) {
        this.searchURL = searchURL;
    }

    public String getWpsListURL() {
        return wpsListURL;
    }

    public void setWpsListURL(String wpsListURL) {
        this.wpsListURL = wpsListURL;
    }

    public String getProcessListURL() {
        return processListURL;
    }

    public void setProcessListURL(String processListURL) {
        this.processListURL = processListURL;
    }

    public RDFClient getRdfClient() {
        return rdfClient;
    }

    /**
     * Gets an RDFResource with Network-Wrapper class, uses the rootURL
     *
     * @return RDFResource with Network-Wrapper class
     * @throws Exception
     */
    public Network getNetwork() throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(new RDFID(rootURL));
        return Network.createWrapper(res);
    }

    /**
     * Gets an RDFResource with WPS-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with WPS-Wrapper class
     * @throws Exception
     */
    public WPS getWPS(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return WPS.createWrapper(res);
    }

    /**
     * Gets an RDFResource with Process-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Process-Wrapper class
     * @throws Exception
     */
    public Process getProcess(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Process.createWrapper(res);
    }

    /**
     * Gets an RDFResource with Input-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Input-Wrapper class
     * @throws Exception
     */
    public Input getInput(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Input.createWrapper(res);
    }

    /**
     * Gets an RDFResource with Output-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Output-Wrapper class
     * @throws Exception
     */
    public Output getOutput(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Output.createWrapper(res);
    }

    /**
     * Gets an RDFResource with ComplexData-Wrapper class, uses the specified
     * RDF ID
     *
     * @return RDFResource with ComplexData-Wrapper class
     * @throws Exception
     */
    public ComplexData getComplexData(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return ComplexData.createWrapper(res);
    }

    /**
     * Gets an RDFResource with LiteralData-Wrapper class, uses the specified
     * RDF ID
     *
     * @return RDFResource with LiteralData-Wrapper class
     * @throws Exception
     */
    public LiteralData getLiteralData(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return LiteralData.createWrapper(res);
    }

    /**
     * Gets an RDFResource with BoundingBoxData-Wrapper class, uses the
     * specified RDF ID
     *
     * @return RDFResource with BoundingBoxData-Wrapper class
     * @throws Exception
     */
    public BoundingBoxData getBoundingBoxData(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return BoundingBoxData.createWrapper(res);
    }

    /**
     * Invokes a keyword search for processes out SemanticProxy.
     *
     * @param keyword
     * @return Found processes
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     * @throws MalformedURLException
     * @throws RDFException
     */
    public Process[] searchProcessByKeyword(String keyword) throws BadRequestException, InternalSPException, CommunicationException, MalformedURLException, RDFException {
        RDFID[] rdfArr = rdfClient.getSearchResults(keyword, new URL(searchURL));
        Process[] processArr = new Process[rdfArr.length];
        for (int i = 0; i < rdfArr.length; i++) {
            Process proc = getProcess(rdfArr[i]);
            processArr[i] = proc;
        }
        return processArr;
    }

    /**
     * Posts the WPS to the SemanticProxy
     *
     * @param wps
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     * @throws MalformedURLException
     */
    public void postWPS(PostWPS wps) throws RDFException, BadRequestException, InternalSPException, CommunicationException, MalformedURLException {
        rdfClient.postRDF(new RDFResource[]{wps.toRDFResource()}, new URL(wpsListURL));
    }

    /**
     * Posts the process to the SemanticProxy
     *
     * @param process
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     * @throws MalformedURLException
     */
    public void postProcess(PostProcess process) throws RDFException, BadRequestException, InternalSPException, CommunicationException, MalformedURLException {
        ArrayList<RDFResource> list = new ArrayList<RDFResource>();

        list.add(process.toRDFResource());
        for (PostInput in : process.getInputs()) {
            list.add(in.toRDFResource());
            PostInAndOutputForm piaof = in.getPostInputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                list.add(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                list.add(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                list.add(r);
            }
        }
        for (PostOutput out : process.getOutputs()) {
            list.add(out.toRDFResource());
            PostInAndOutputForm piaof = out.getPostOutputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                list.add(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                list.add(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                list.add(r);
            }
        }

        rdfClient.postRDF(list.toArray(new RDFResource[list.size()]), new URL(processListURL));
    }

    /**
     * Deletes a WPS from the SemanticProxy
     *
     * @param rdfid
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     */
    public void deleteWPS(RDFID rdfid) throws BadRequestException, InternalSPException, CommunicationException {
        rdfClient.deleteResource(rdfid);
    }

    /**
     * Deletes a process from the SemanticProxy
     *
     * @param rdfid
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     */
    public void deleteProcess(RDFID rdfid) throws BadRequestException, InternalSPException, CommunicationException {
        rdfClient.deleteResource(rdfid);
    }

    /**
     * Updates the WPS on the SemanticProxy to a new form.
     *
     * @param wps
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     * @throws MalformedURLException
     */
    public void updateWPS(PostWPS wps) throws RDFException, BadRequestException, InternalSPException, CommunicationException, MalformedURLException {
        rdfClient.putRDF(new RDFResource[]{wps.toRDFResource()}, new URL(wpsListURL));
    }

    /**
     * Updates the process on the SemanticProxy to a new form.
     *
     * @param process
     * @throws RDFException
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException
     * @throws MalformedURLException
     */
    public void updateProcess(PostProcess process) throws RDFException, BadRequestException, InternalSPException, CommunicationException, MalformedURLException {
        ArrayList<RDFResource> list = new ArrayList<RDFResource>();

        list.add(process.toRDFResource());
        for (PostInput in : process.getInputs()) {
            list.add(in.toRDFResource());
            PostInAndOutputForm piaof = in.getPostInputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                list.add(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                list.add(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                list.add(r);
            }
        }
        for (PostOutput out : process.getOutputs()) {
            list.add(out.toRDFResource());
            PostInAndOutputForm piaof = out.getPostOutputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                list.add(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                list.add(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                list.add(r);
            }
        }

        rdfClient.putRDF(list.toArray(new RDFResource[list.size()]), new URL(process.getRdfId().rdfID));
    }
}

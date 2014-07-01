/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

import de.hsos.richwps.sp.client.rdf.RDFClient;
import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.rdf.RDFResource;

/**
 * Client for SemanticProxy interaction, implements Singleton pattern.
 *
 * @author fbensman
 */
public class SPClient {

    private RDFClient rdfClient = null;
    private static SPClient instance = null;
    private String rootURL = "http://localhost:4567/semanticproxy/resources";

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

    /**
     * Tests the connection to the root URL. The root URL is
     * "http://localhost:4567/semanticproxy/resources" if not specified
     * otherwise.
     *
     * @return True if connection is ok, false else
     */
    public boolean testConnection() {
        try {
            Network network = null;
            network = getNetwork();
            if (network == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

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

    public RDFClient getRdfClient() {
        return rdfClient;
    }

    /**
     * Gets an RDFResource with Network-Wrapper class, uses the rootURL
     *
     * @return RDFResource with Network-Wrapper class
     * @throws Exception
     */
    public Network getNetwork() throws Exception {
        RDFResource res = rdfClient.retrieveResource(new RDFID(rootURL));
        return Network.createWrapper(res);
    }

    /**
     * Gets an RDFResource with WPS-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with WPS-Wrapper class
     * @throws Exception
     */
    public WPS getWPS(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return WPS.createWrapper(res);
    }

    /**
     * Gets an RDFResource with Process-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Process-Wrapper class
     * @throws Exception
     */
    public Process getProcess(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Process.createWrapper(res);
    }
    
    
    
    /**
     * Gets an RDFResource with Input-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Input-Wrapper class
     * @throws Exception
     */
    public Input getInput(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Input.createWrapper(res);
    }
    
    
    /**
     * Gets an RDFResource with Output-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with Output-Wrapper class
     * @throws Exception
     */
    public Output getOutput(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Output.createWrapper(res);
    }
    
    
    /**
     * Gets an RDFResource with ComplexData-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with ComplexData-Wrapper class
     * @throws Exception
     */
    public ComplexData getComplexData(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return ComplexData.createWrapper(res);
    }
    
    /**
     * Gets an RDFResource with LiteralData-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with LiteralData-Wrapper class
     * @throws Exception
     */
    public LiteralData getLiteralData(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return LiteralData.createWrapper(res);
    }
    
    
    /**
     * Gets an RDFResource with BoundingBoxData-Wrapper class, uses the specified RDF ID
     *
     * @return RDFResource with BoundingBoxData-Wrapper class
     * @throws Exception
     */
    public BoundingBoxData getBoundingBoxData(RDFID rdfID) throws Exception {
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return BoundingBoxData.createWrapper(res);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

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
}

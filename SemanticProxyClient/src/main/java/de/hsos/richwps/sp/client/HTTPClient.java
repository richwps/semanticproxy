/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import java.net.URL;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientProperties;

/**
 * Client for HTTP level access
 *
 * @author fbensman
 */
public class HTTPClient {

    public HTTPClient() {
    }
    
    
    /**
     * Sets proxy configuration for this http client
     * @param httpHost
     * @param httpPort 
     */
    public void setProxyConfiguration(String httpHost, String httpPort){
        System.setProperty("http.proxyHost", httpHost);
        System.setProperty("http.proxyPort", httpPort);
    }
    

    /**
     * Gets an rdf resource from the SemanticProxy
     *
     * @param url The URL of the resource
     * @return The resource as a string
     * @throws Exception If sth goes wrong
     */
    public String getRawRDF(String url) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(url);

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml+rdf");

            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                if (response.getStatus() == 404) {
                    throw new BadRequestException("Resource: " + url + " not found.");
                }
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for GET " + url);
            }
            return response.readEntity(String.class);

        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + url + " failed. Original message: " + e.getMessage());
        }
    }

    
    /**
     * Executes a search request to the SemanticProxy and returns the raw result page
     * @param keyword Term to search
     * @param url SemanticProxy endpoint to address the query to
     * @return The raw result page
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public String getRawSearchResults(String keyword, URL url) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(url.toString());
            webTarget = webTarget.queryParam("keyword", keyword);
            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml");
            


            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                if (response.getStatus() == 400) {
                    throw new BadRequestException(response.readEntity(String.class));
                }
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for GET " + url);
            }
            return response.readEntity(String.class);

        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + url + " failed. Original message: " + e.getMessage());
        }

    }

    
    /**
     * Executes a POST with an XML+RDF document to the given URL
     * @param xmlrdf The XML document to post
     * @param url The URL to send the POST to
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public void postRDFDoc(String xmlrdf, URL url) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(url.toString());

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml");
            Response response = invocationBuilder.post(Entity.entity(xmlrdf, "application/xml+rdf"));

            if (response.getStatus() != 201) {
                if (response.getStatus() == 415) {
                    throw new BadRequestException("SemanticProxy does not support the content format.");
                }
                String errMsg = response.readEntity(String.class);
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for POST to " + url + " Error: " + errMsg);
            }
            return;
        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + url + " failed. Original message: " + e.getMessage());
        }

    }

    
    /**
     * Sends a delete request to a given resource/URL
     * @param url URL to delete / to address the delete request to
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public void delete(String url) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(url);

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml");
            Response response = invocationBuilder.delete();

            if (response.getStatus() != 200) {
                if (response.getStatus() == 404) {
                    throw new BadRequestException("Resource " + url + " not found.");
                }
                String errMsg = response.readEntity(String.class);
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for DELETE to " + url + " Error: " + errMsg);
            }
            return;
        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + url + " failed. Original message: " + e.getMessage());
        }
    }

    
    /**
     * Updates a resource or creates it if not present
     * @param xmlrdf RDF description of the resource to send
     * @param url Address of the endpoint to send to
     * @throws BadRequestException
     * @throws InternalSPException
     * @throws CommunicationException 
     */
    public void putRDFDoc(String xmlrdf, URL url) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(url.toString());

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml");
            Response response = invocationBuilder.put(Entity.entity(xmlrdf, "application/xml+rdf"));

            if (response.getStatus() != 200) {
                if (response.getStatus() == 415) {
                    throw new BadRequestException("SemanticProxy does not support the content format.");
                }
                String errMsg = response.readEntity(String.class);
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for PUT to " + url + " Error: " + errMsg);
            }
            return;
        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + url + " failed. Original message: " + e.getMessage());
        }

    }

    
    /**
     * Executes a GET request to the ID generator on the SemanticProxy
     * @param type Type of resource to request the ID for
     * @param idGeneratorURL Endpoint of the ID generator
     * @return Generated RDF ID
     */
    public String requestID(String type, URL idGeneratorURL) throws BadRequestException, InternalSPException, CommunicationException {
        try {
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT,    5000);
            WebTarget webTarget = client.target(idGeneratorURL.toString());
            webTarget = webTarget.queryParam("type", type);
            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml");
            
            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                if (response.getStatus() == 400) {
                    throw new BadRequestException(response.readEntity(String.class));
                }
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for GET " + idGeneratorURL);
            }
            return response.readEntity(String.class);

        } catch (BadRequestException e) {
            throw e;
        } catch (InternalSPException e) {
            throw e;
        } catch (Exception e) {
            throw new CommunicationException("Communication to SemanticProxy at " + idGeneratorURL + " failed. Original message: " + e.getMessage());
        }
    }
}

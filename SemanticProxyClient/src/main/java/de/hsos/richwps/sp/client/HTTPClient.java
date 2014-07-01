/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Client for HTTP level access
 *
 * @author fbensman
 */
public class HTTPClient {

    public HTTPClient() {
    }

    /**
     * Gets an rdf resource from the SemanticProxy
     *
     * @param url The URL of the resource
     * @return The resource as a string
     * @throws Exception If sth goes wrong
     */
    public String getRawRDF(String url) throws ResourceNotFoundException, InternalSPException, CommunicationException {
        try{
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url);

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml+rdf");

            Response response = invocationBuilder.get();
 
            if (response.getStatus() != 200) {
                if(response.getStatus() == 404 ){
                    throw new ResourceNotFoundException("Resource: "+ url +" not found.");
                }
                throw new InternalSPException("SemanticProxy returned " + response.getStatus() + " for GET" + url);
            }
            return response.readEntity(String.class);
            
        }catch(ResourceNotFoundException e){
            throw e;
        }
        catch(InternalSPException e){
            throw e;
        }
        catch(Exception e){
            throw new CommunicationException("Communication to SemanticProxy at "+ url +" failed. Original message: "+e.getMessage());
        }
    }
}

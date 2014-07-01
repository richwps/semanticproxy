/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
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
    public String getRawRDF(String url) throws Exception {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url);

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml+rdf");

            Response response = invocationBuilder.get();

            if (response.getStatus() != 200) {
                throw new Exception("Error, SemanticProxy returned " + response.getStatus() + " for " + url);
            }
            return response.readEntity(String.class);
        } catch (Exception e) {
            throw e;
        }
    }
}

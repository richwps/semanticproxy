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
 *
 * @author fbensman
 */
public class HTTPClient {
    

    
    public HTTPClient(){
  
    }
    
    public String getRawRDF(String uri) throws Exception {
        try{
            System.out.println("uri: "+uri);
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(uri);
            
            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml+rdf");
            
            Response response = invocationBuilder.get();
            
            if(response.getStatus() != 200 )
                throw new Exception("Error, SemanticProxy returned "+response.getStatus()+" for "+uri);
            return response.readEntity(String.class);
        }catch(Exception e){
            throw e;
        }
    }
    
}

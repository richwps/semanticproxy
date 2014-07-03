/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import java.net.URL;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * The access point for delete queries from the web
 * @author fbensman
 */
public class UpdateAccess {
    
    /**
     * Installs endpoints for resource updates
     */
    public UpdateAccess(URL processNamingURL, URL wpsNamingURL){
        
        /**
         * Register endpoint for process update
         */
         put(new Route(processNamingURL.getPath()+"/*") {
            @Override
            public Object handle(Request request, Response response) {
                    if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    String route = request.url();
                    try{
                        ContentChanger.updateProcess(body, route);
                        response.status(200);
                        return "Process updated";
                    }catch(Exception e){
                        response.status(500);
                        return "Error, "+e.getMessage();
                    }
                }
                else{
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }    
            }
        });
    
         
         /**
          * Register endpoint for wps update
          */
         put(new Route(wpsNamingURL.getPath()+"/*") {
            @Override
            public Object handle(Request request, Response response) {
                if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    String route = request.url();
                    try{
                        ContentChanger.updateWPS(body, route);
                        response.status(200);
                        return "WPS updated";
                    }catch(Exception e){
                        response.status(500);
                        return "Error, "+e.getMessage();
                    }
                }
                else{
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }    
                
                
                
            }
        });
    
    }
    
}

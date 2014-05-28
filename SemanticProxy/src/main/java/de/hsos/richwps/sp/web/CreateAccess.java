/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * The access point for the create queries from the web
 * @author fbensman
 */
public class CreateAccess {
    
    
    /**
     * Installs endpoints for http post
     */
    public CreateAccess(){
    
    
        /**
         * Register endpoint for process creation
         */
        post(new Route("/semanticproxy/resources/processes") {
            @Override
            public Object handle(Request request, Response response) {
                if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    try{
                        ContentChanger.pushProcessRDFintoDB(body);
                        response.status(201);
                        return "Process created";
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
         * Register endpoint for wps creation
         */
        post(new Route("/semanticproxy/resources/wps") {
            @Override
            public Object handle(Request request, Response response) {
                if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    try{
                        ContentChanger.pushWPSRDFintoDB(body);
                        response.status(201);
                        return "WPS created";
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

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
 * The access point for delete queries from the web
 * @author fbensman
 */
public class DeleteAccess {
    
    /**
     * Installs endpoints for the deletion of resources
     */
    public DeleteAccess(){
        
        /**
         * Register endpoint for process deletion
         */
         delete(new Route("/semanticproxy/resources/process/*") {
            @Override
            public Object handle(Request request, Response response) {
                    try{
                        if(ContentChanger.deleteProcess(request.pathInfo())){
                            response.status(200);
                            System.out.println(DBIO.getWholeDBContent().rDFXMLRepresentation());
                            return "Resource deleted";
                        }
                        else{
                            response.status(404);
                            return "Resource not found";
                        }
                    }catch(Exception e){
                        response.status(500);
                        return "Error, "+e.getMessage();
                    } 
            }
        });
    
         
         /**
          * Register endpoint for wps deletion
          */
         delete(new Route("/semanticproxy/resources/wps/*") {
            @Override
            public Object handle(Request request, Response response) {
                    try{
                        if(ContentChanger.deleteWPS(request.pathInfo())){
                            response.status(200);
                            System.out.println(DBIO.getWholeDBContent().rDFXMLRepresentation());
                            return "Resource deleted";
                        }
                        else{
                            response.status(404);
                            return "Resource not found";
                        }
                    }catch(Exception e){
                        response.status(500);
                        return "Error, "+e.getMessage();
                    } 
            }
        });
    
    }
}

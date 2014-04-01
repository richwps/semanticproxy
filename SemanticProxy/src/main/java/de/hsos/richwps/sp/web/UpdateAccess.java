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
 *
 * @author fbensman
 */
public class UpdateAccess {
    
    
    public UpdateAccess(){
        
         put(new Route("/semanticproxy/resources/process/*") {
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
    
         
         put(new Route("/semanticproxy/resources/wps/*") {
            @Override
            public Object handle(Request request, Response response) {
                if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    String route = request.pathInfo();
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

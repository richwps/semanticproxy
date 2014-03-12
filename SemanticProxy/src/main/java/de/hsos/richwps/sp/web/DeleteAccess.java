/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.ContentChanger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 *
 * @author fbensman
 */
public class DeleteAccess {
    
    public DeleteAccess(){
        
         delete(new Route("/semanticproxy/resources/process/*") {
            @Override
            public Object handle(Request request, Response response) {
                if(request.contentType().equalsIgnoreCase("application/xml+rdf")){
                    String body = request.body();
                    try{
                        ContentChanger.deleteTriples(body);
                        response.status(200);
                        return "Resources deleted";
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

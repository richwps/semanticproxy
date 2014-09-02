/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.ContentChanger;
import java.net.URL;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * The access point for delete queries from the web
 *
 * @author fbensman
 */
public class DeleteAccess {

    private static DeleteAccess instance = null;
    
    
    /**
     * Registeres the required routes and handlers for delete access
     * @param processNamingURL
     * @param wpsNamingURL 
     */
    public static void activate (final URL processNamingURL, final URL wpsNamingURL){
        if(instance == null){
            instance = new DeleteAccess(processNamingURL, wpsNamingURL);
        }
    }
    
    
    /**
     * Installs endpoints for the deletion of resources
     */
    private DeleteAccess(final URL processNamingURL, final URL wpsNamingURL) {

        /**
         * Register endpoint for process deletion
         */
        delete(new Route(processNamingURL.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(DeleteAccess.class).info("Delete process request to: "+request.url());
                    if (ContentChanger.deleteProcess(request.url())) {
                        Logger.getLogger(DeleteAccess.class).info("Process "+request.url() + " deleted");
                        response.status(200);
                        return "Resource deleted";
                    } else {
                        Logger.getLogger(DeleteAccess.class).error("Delete process request to: "+request.url()+" - Resource not found.");
                        response.status(404);
                        return "Resource not found";
                    }
                } catch (Exception e) {
                    Logger.getLogger(DeleteAccess.class).error("Delete process request to: "+request.url(),e);
                    response.status(500);
                    return  e.getMessage();
                }
            }
        });


        /**
         * Register endpoint for wps deletion
         */
        delete(new Route(wpsNamingURL.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(DeleteAccess.class).info("Delete WPS request to: "+request.url());
                    if (ContentChanger.deleteWPS(request.url())) {
                        Logger.getLogger(DeleteAccess.class).info("WPS "+request.url() + " deleted");
                        response.status(200);
                        return "Resource deleted";
                    } else {
                        Logger.getLogger(DeleteAccess.class).info("Delete WPS request to: "+request.url()+" - Resource not found.");
                        response.status(404);
                        return "Resource not found";
                    }
                } catch (Exception e) {
                    Logger.getLogger(DeleteAccess.class).info("Delete WPS request to: "+request.url(),e);
                    response.status(500);
                    return e.getMessage();
                }
            }
        });

    }
}

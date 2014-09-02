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
 * The access point for update queries from the web
 *
 * @author fbensman
 */
public class UpdateAccess {

    private static UpdateAccess instance = null;
    
    
    /**
     * Registeres the required routes and handlers for update access
     * @param processNamingURL
     * @param wpsNamingURL 
     */
    public static void activate(URL processNamingURL, URL wpsNamingURL){
        if(instance == null){
            instance = new UpdateAccess(processNamingURL, wpsNamingURL);
        }
    }
    
    
    /**
     * Installs endpoints for resource updates
     */
    private UpdateAccess(URL processNamingURL, URL wpsNamingURL) {

        /**
         * Register endpoint for process update
         */
        put(new Route(processNamingURL.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                Logger.getLogger(UpdateAccess.class).info("Update process request to: "+ request.url());
                if (request.contentType().equalsIgnoreCase("application/xml+rdf")) {
                    String body = request.body();
                    String route = request.url();
                    try {
                        Logger.getLogger(UpdateAccess.class).debug("Updated process "+ request.url() + "\n"+body);
                        ContentChanger.updateProcess(body, route);
                        Logger.getLogger(UpdateAccess.class).info("Updated process "+ request.url());
                        response.status(200);
                        return "Process updated";
                    } catch (Exception e) {
                        Logger.getLogger(UpdateAccess.class).error("Update process request",e);
                        response.status(500);
                        return  e.getMessage();
                    }
                } else {
                    Logger.getLogger(UpdateAccess.class).error("Update process request. - Format not supported, use application/xml+rdf");
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }
            }
        });


        /**
         * Register endpoint for wps update
         */
        put(new Route(wpsNamingURL.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                Logger.getLogger(UpdateAccess.class).info("Update WPS request to: "+ request.url());
                if (request.contentType().equalsIgnoreCase("application/xml+rdf")) {
                    String body = request.body();
                    String route = request.url();
                    try {
                        Logger.getLogger(UpdateAccess.class).debug("Update WPS request to: "+ request.url()+"\n"+body);
                        ContentChanger.updateWPS(body, route);
                        Logger.getLogger(UpdateAccess.class).info("WPS"+ request.url()+"updated");
                        response.status(200);
                        return "WPS updated";
                    } catch (Exception e) {
                        Logger.getLogger(UpdateAccess.class).error("Update WPS request",e);
                        response.status(500);
                        return  e.getMessage();
                    }
                } else {
                    Logger.getLogger(UpdateAccess.class).error("Update WPS request. - Format not supported, use application/xml+rdf");
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }

            }
        });

    }
}

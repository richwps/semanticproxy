/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.ContentChanger;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

/**
 * The access point for the create queries from the web
 *
 * @author fbensman
 */
public class CreateAccess {

    /**
     * Installs endpoints for http post
     */
    public CreateAccess(URL processListURL, URL wpsListURL) {


        /**
         * Register endpoint for process creation
         */
        post(new Route(processListURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                if (request.contentType().equalsIgnoreCase("application/xml+rdf")) {
                    String body = request.body();
                    Logger.getLogger(CreateAccess.class).info("Create process request");
                    Logger.getLogger(CreateAccess.class).debug("Create process request "+ body);
                    try {
                        ContentChanger.pushProcessRDFintoDB(body);
                        Logger.getLogger(CreateAccess.class).info("Process created");
                        response.status(201);
                        return "Process created";
                    } catch (Exception e) {
                        Logger.getLogger(CreateAccess.class).error(e);
                        response.status(500);
                        return  e.getMessage();
                    }
                } else {
                    Logger.getLogger(CreateAccess.class).error("Format not supported, use application/xml+rdf");
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }

            }
        });


        /**
         * Register endpoint for wps creation
         */
        post(new Route(wpsListURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                if (request.contentType().equalsIgnoreCase("application/xml+rdf")) {
                    String body = request.body();
                    Logger.getLogger(CreateAccess.class).info("Create wps request");
                    Logger.getLogger(CreateAccess.class).debug("Create wps request: "+body);
                    try {
                        ContentChanger.pushWPSRDFintoDB(body);
                        Logger.getLogger(CreateAccess.class).info("WPS created");
                        response.status(201);
                        return "WPS created";
                    } catch (Exception e) {
                        Logger.getLogger(CreateAccess.class).error(e);
                        response.status(500);
                        return e.getMessage();
                    }
                } else {
                    Logger.getLogger(CreateAccess.class).error("Format not supported, use application/xml+rdf");
                    response.status(415);
                    return "Format not supported, use application/xml+rdf";
                }

            }
        });


    }
}

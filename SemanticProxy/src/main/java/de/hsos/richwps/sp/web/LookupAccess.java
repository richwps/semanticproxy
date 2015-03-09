/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.LookupHandling;
import de.hsos.richwps.sp.restlogic.SearchHandling;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;

/**
 * This is the web interface for the lookup service. A query should look like
 * .../application/lookup?wps=http://www.example.com/wps&process=com.example.wps.processA
 * This interface returns a URL list of found process resources
 * @author fbensman
 */
public class LookupAccess {

    private static final String MIMETYPE_RDF = "application/xml";
    //private static final String MIMETYPE_RDF = "application/rdf+xml";
    private static final String MIMETYPE_HTML = "text/html";
    private static final String MIMETYPE_XML = "application/xml";
    private static final String MIMETYPE_TEXT = "text/pain";
    private static LookupAccess instance = null;

    /**
     * Registeres the required routes and handlers for search access
     *
     * @param lookupURL
     */
    public static void activate(URL lookupURL) {
        if (instance == null) {
            instance = new LookupAccess(lookupURL);
        }
    }

    /**
     * Registeres the required routes and handlers
     */
    private LookupAccess(URL lookupURL) {

        /**
         * Registers the route for the lookup service
         */
        get(
                new Route(lookupURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                String wps = (String) request.queryParams("wps");
                String process = (String) request.queryParams("process");
                Logger.getLogger(LookupAccess.class).info("Lookup process, wps = " + wps+" process = "+process);
                if (wps == null || wps.equals("")) {
                    Logger.getLogger(LookupAccess.class).error("Lookup process: Empty wps parameter");
                    response.status(400);
                    return "Empty wps parameter";
                }
                if (process == null || process.equals("")) {
                    Logger.getLogger(LookupAccess.class).error("Lookup process: Empty process parameter");
                    response.status(400);
                    return "Empty process parameter";
                } 
                if (wps.length() > 400) {
                    Logger.getLogger(LookupAccess.class).error("Lookup: wps parameter longer than 400 characters");
                    response.status(400);
                    return "WPS parameter longer than 400 characters";
                }
                if (process.length() > 400) {
                    Logger.getLogger(LookupAccess.class).error("Lookup: process parameter longer than 400 characters");
                    response.status(400);
                    return "Process parameter longer than 400 characters";
                }
                try {
                    URL wpsURL = null;
                    try{
                        wpsURL = new URL(wps);
                    }catch(MalformedURLException murle){
                        Logger.getLogger(LookupAccess.class).info("WPS parameter was not a URL: "+ wps);
                        response.status(400);
                        response.type(MIMETYPE_TEXT);
                        return wps+" is not a valid URL.";
                    }
                    SubjectList list = LookupHandling.processLookup(wpsURL, process);
                    Logger.getLogger(LookupAccess.class).info("Lookup found "+list.size()+" processes");
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return list.toXMLList();   
                } catch (Exception e) {
                    Logger.getLogger(LookupAccess.class).error("Search by keyword", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });
    }
}

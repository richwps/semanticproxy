/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.ContentGetter;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;
import org.apache.log4j.Logger;
import static spark.Spark.*;
import spark.*;

/**
 * The access point for the queries from the web
 *
 * @author fbensman
 */
public class BrowseAccess {

    private static final String MIMETYPE_RDF = "application/xml";
    //private static final String MIMETYPE_RDF = "application/rdf+xml";
    private static final String MIMETYPE_HTML = "text/html";
    private static final String MIMETYPE_XML = "application/xml";
    private static String linkHeader = null;
    private static BrowseAccess instance = null;

    /**
     * Registeres the required routes and handlers for browsing
     *
     * @param applicationURL
     * @param resourcesURL
     * @param vocabularyURL
     * @param networkURL
     * @param processListURL
     * @param wpsListURL
     * @param wfsListURL
     */
    public static void activate(URL applicationURL,
            URL resourcesURL,
            URL vocabularyURL,
            URL processListURL,
            URL wpsListURL,
            URL wfsListURL,
            URL searchURL,
            URL idgeneratorURL,
            URL lookupURL) {
        if (instance == null) {
            instance = new BrowseAccess(applicationURL, resourcesURL, vocabularyURL , processListURL, wpsListURL, wfsListURL, searchURL, idgeneratorURL, lookupURL);
        }
    }

    /**
     * Registeres the required routes an handlers
     */
    private BrowseAccess(URL applicationURL,
            URL resourcesURL,
            URL vocabularyURL,
            URL processListURL,
            URL wpsListURL,
            URL wfsListURL,
            URL searchURL,
            URL idgeneratorURL,
            URL lookupURL) {
        
        //init link header
        linkHeader = createLinkHeader(applicationURL, resourcesURL, vocabularyURL, processListURL, wpsListURL, wfsListURL, searchURL, idgeneratorURL, lookupURL);

        /**
         * Registers the route for root for user convenience
         */
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                response.type(MIMETYPE_HTML);
                String str = "<html>\n"
                        + "<head>\n"
                        + "<title>Sparc root</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "Nothing here, try <a href=\"semanticproxy\" type=\"text/html\">Semantic Proxy</a>"
                        + "</body>\n"
                        + "</html>";
                return str;
            }
        });




        /**
         * Registers the route for user information
         */
        get(new Route(applicationURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                response.type(MIMETYPE_HTML);
                response.header("Link", linkHeader);
               
                String str = "<html>\n"
                        + "<head>\n"
                        + "<title>SemanticProxy</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<h1> SemanticProxy\n</h1>"
                        + "An RDF directory service for RichWPS"
                        + "</body>\n"
                        + "</html>";
                return str;
            }
        });




        /**
         * Registers the route for vocabulary query
         */
        get(new Route(vocabularyURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(BrowseAccess.class).info("Get vocabulary request");
                    String str = Vocabulary.getRDF_XML_Representation();
                    response.status(200);
                    response.type(MIMETYPE_RDF);
                    return str;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get vocabulary request", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });




        /**
         * Registers the route for root
         */
        get(new Route(resourcesURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {

                try {
                    Logger.getLogger(BrowseAccess.class).info("Get resources/network request");
                    String rdf = ContentGetter.getRDFFor(request.url());
                    if (rdf == null) {
                        Logger.getLogger(BrowseAccess.class).error("Get resources/network request - Resource not found");
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type(MIMETYPE_RDF); //normally +rdf, but download in chrome is annoying
                    return rdf;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get resources/network request", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });




        /**
         * Registers the route for process list
         */
        get(new Route(processListURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(BrowseAccess.class).info("Get process list request.");
                    String str = ContentGetter.getAllProcesses();
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return str;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get process list request", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });


        /**
         * Registers the route for wps list
         */
        get(new Route(wpsListURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(BrowseAccess.class).info("Get wps list request.");
                    String str = ContentGetter.getAllWPS();
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return str;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get wps list request", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });



        /**
         * Registers the route for wfs list
         */
        get(new Route(wfsListURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(BrowseAccess.class).info("Get wfs list request.");
                    String str = ContentGetter.getAllWFS();
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return str;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get wfs list request", e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });



        /**
         * Registers the route for resourcesURL
         */
        get(new Route(resourcesURL.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Logger.getLogger(BrowseAccess.class).info("Get resource: " + request.url());
                    String rdf = ContentGetter.getRDFFor(request.url());
                    if (rdf == null) {
                        Logger.getLogger(BrowseAccess.class).error("Resource not found: " + request.url());
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type(MIMETYPE_RDF); //normally +rdf, but download in chrome is annoying
                    return rdf;
                } catch (Exception e) {
                    Logger.getLogger(BrowseAccess.class).error("Get resource: " + request.url(), e);
                    response.status(500);
                    return e.getMessage();
                }

            }
        });

    }

 
    private String createLinkHeader(URL applicationURL,
            URL resourcesURL,
            URL vocabularyURL,
            URL processListURL,
            URL wpsListURL,
            URL wfsListURL,
            URL searchURL,
            URL idGeneratorURL,
            URL lookupURL) {
        String h = "";
        h+="<"+applicationURL.toString()+">; rel=\"application\",";
        h+="<"+resourcesURL.toString()+">; rel=\"resources\",";
        h+="<"+vocabularyURL.toString()+">; rel=\"vocabulary\",";
        h+="<"+processListURL.toString()+">; rel=\"processlist\",";
        h+="<"+wpsListURL.toString()+">; rel=\"wpslist\",";
        h+="<"+wfsListURL.toString()+">; rel=\"wfslist\",";
        h+="<"+searchURL.toString()+">; rel=\"search\",";
        h+="<"+idGeneratorURL.toString()+">; rel=\"idgenerator\",";
        h+="<"+lookupURL.toString()+">; rel=\"lookup\"";
        
        return h;
    }
}

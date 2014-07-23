/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.RouteMapper;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;
import java.util.Set;
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
    
    
    /**
     * Registeres the required routes an handlers
     */
    public BrowseAccess(URL applicationURL,
            URL resources,
            URL vocabularyURL,
            URL networkURL,
            URL processListURL,
            URL wpsList) {





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
                String str = "<html>\n"
                        + "<head>\n"
                        + "<title>Semantic Proxy</title>\n"
                        + "</head>\n"
                        + "<body>\n"
                        + "<h1> Semantic Proxy\n</h1>"
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
                    //String str = VocabReader.readPlainText();
                    String str = Vocabulary.getRDF_XML_Representation();
                    response.status(200);
                    response.type(MIMETYPE_RDF); 
                    return str;
                } catch (Exception e) {
                    System.out.println("Error, " + e.getMessage());
                    response.status(500);
                    return "Error. " + e.getMessage();
                }

            }
        });




        /**
         * Registers the route for network
         */
        get(new Route(networkURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {

                try {

                    String rdf = RouteMapper.getRDFFor(request.url());
                    if (rdf == null) {
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type(MIMETYPE_RDF); //normally +rdf, but download in chrome is annoying
                    System.out.println("\n response: " + response.raw().toString());
                    return rdf;
                } catch (Exception e) {
                    System.out.println("Error, " + e.getMessage());
                    response.status(500);
                    return "Error. " + e.getMessage();
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
                    String str = RouteMapper.getAllProcesses();
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return str;
                } catch (Exception e) {
                    System.out.println("Error, " + e.getMessage());
                    response.status(500);
                    return "Error. " + e.getMessage();
                }

            }
        });


        /**
         * Registers the route for wps list
         */
        get(new Route(wpsList.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String str = RouteMapper.getAllWPS();
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return str;
                } catch (Exception e) {
                    System.out.println("Error, " + e.getMessage());
                    response.status(500);
                    return "Error. " + e.getMessage();
                }

            }
        });


        /**
         * Registers the route for resources
         */
        get(new Route(resources.getPath() + "/*") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String rdf = RouteMapper.getRDFFor(request.url());
                    if (rdf == null) {
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type(MIMETYPE_RDF); //normally +rdf, but download in chrome is annoying
                    return rdf;
                } catch (Exception e) {
                    System.out.println("Error, " + e.getMessage());
                    response.status(500);
                    return "Error. " + e.getMessage();
                }

            }
        });


    }
}

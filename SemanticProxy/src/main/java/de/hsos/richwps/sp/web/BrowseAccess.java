/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.RouteMapper;
import de.hsos.richwps.sp.restlogic.VocabReader;
import de.hsos.richwps.sp.types.RDFDocument;
import static spark.Spark.*;
import spark.*;

/**
 * The access point for the queries from the web
 *
 * @author fbensman
 */
public class BrowseAccess {

    /**
     * Registeres the required routes an handlers
     */
    public BrowseAccess() {


        /**
         * Registers the route for root for user convenience
         */
        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
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
        get(new Route("/semanticproxy") {
            @Override
            public Object handle(Request request, Response response) {
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
        get(new Route("/semanticproxy/resources/vocab") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String str = VocabReader.readPlainText();
                    response.status(200);
                    response.type("application/xml"); //normally +rdf, but download in chrome is annoying
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
        get(new Route("/semanticproxy/resources") {
            @Override
            public Object handle(Request request, Response response) {
                 try {
                    String rdf =  RouteMapper.getRDFFor(request.pathInfo());
                    if(rdf == null){
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type("application/xml"); //normally +rdf, but download in chrome is annoying
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
        get(new Route("/semanticproxy/resources/processes") {
            @Override
            public Object handle(Request request, Response response) {
                 try {
                    String str = RouteMapper.getAllProcesses();
                    response.status(200);
                    response.type("application/xml"); 
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
        get(new Route("/semanticproxy/resources/wps") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    String str = RouteMapper.getAllWPS();
                    response.status(200);
                    response.type("application/xml");
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
        get(new Route("/semanticproxy/resources/*") {
            @Override
            public Object handle(Request request, Response response) {
                 try {
                     String rdf =  RouteMapper.getRDFFor(request.pathInfo());
                    if(rdf == null){
                        response.status(404);
                        return "Resource not found";
                    }
                    response.status(200);
                    response.type("application/xml"); //normally +rdf, but download in chrome is annoying
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

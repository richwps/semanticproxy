/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.IDGenerator;
import de.hsos.richwps.sp.types.EIDType;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.URL;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;

/**
 *
 * @author fbensman
 */
public class IDGeneratorAccess {

    private static final String MIMETYPE_RDF = "application/xml";
    //private static final String MIMETYPE_RDF = "application/rdf+xml";
    private static final String MIMETYPE_HTML = "text/html";
    private static final String MIMETYPE_XML = "application/xml";
    private static IDGeneratorAccess instance = null;
    
    private URL generatorEndpoint = null;

    /**
     * Registeres the required routes and handlers for ID generation access
     *
     * @param generatorEndpoint
     */
    public static void activate(URL generatorEndpoint) {
        if (instance == null) {
            instance = new IDGeneratorAccess(generatorEndpoint);
        }
    }

    /**
     * Registeres the required routes an handlers
     */
    private IDGeneratorAccess(URL generatorEndpoint) {
        this.generatorEndpoint = generatorEndpoint;

      
        /**
         * Registers the route for the id generator
         */
        get(
                new Route(generatorEndpoint.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                String keyword = (String) request.queryParams("type");
                Logger.getLogger(IDGeneratorAccess.class).info("Generate ID request for type, type = "+keyword);
                if (keyword == null) {
                    Logger.getLogger(IDGeneratorAccess.class).error("Generate ID request for type: Empty query");
                    response.status(400);
                    return "Empty generate ID query.";
                }
                try {
                    SubjectList list = null;
                    try{
                        list = IDGenerator.getInstance().generateID(EIDType.valueOf(keyword.toUpperCase()));
                    }catch(IllegalArgumentException iae){
                        Logger.getLogger(IDGeneratorAccess.class).error("Generate ID request for type: Empty query");
                        response.status(400);
                        return "Invalid generate ID query.";
                    }
                    Logger.getLogger(IDGeneratorAccess.class).info("Generated ID: "+list.get(0).toString());
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return list.toXMLList();
                } catch (Exception e) {
                    Logger.getLogger(IDGeneratorAccess.class).error("Generate ID request",e);
                    response.status(500);
                    return  e.getMessage();
                }
            }
        });
    }
}

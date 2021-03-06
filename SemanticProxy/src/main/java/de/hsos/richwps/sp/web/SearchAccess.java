/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.SearchHandling;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.URL;
import org.apache.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;

/**
 * This is the web interface for search queries.
 * A query shoud look like: .../application/search?s=keyword
 * This interface returns a URL list of found process resources
 * @author fbensman
 */
public class SearchAccess {

    private static final String MIMETYPE_RDF = "application/xml";
    //private static final String MIMETYPE_RDF = "application/rdf+xml";
    private static final String MIMETYPE_HTML = "text/html";
    private static final String MIMETYPE_XML = "application/xml";

    private static SearchAccess instance = null;
    
    
    /**
     * Registeres the required routes and handlers for search access
     * @param searchURL 
     */
    public static void activate(URL searchURL){
        if(instance == null){
            instance = new SearchAccess(searchURL);
        }
    }
    
    
    /**
     * Registeres the required routes and handlers
     */
    private SearchAccess(URL searchURL) {

        /**
         * Registers the route for search access
         */
        get(
                new Route(searchURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                String keyword = (String) request.queryParams("keyword");
                Logger.getLogger(SearchAccess.class).info("Search by keyword, keyword = "+keyword);
                if (keyword == null) {
                    Logger.getLogger(SearchAccess.class).error("Search by keyword: Empty search query");
                    response.status(400);
                    return "Empty search query";
                }
                else if (keyword.length() > 300) {
                    Logger.getLogger(SearchAccess.class).error("Search by keyword: Query longer than 300 characters");
                    response.status(400);
                    return "Query longer than 300 characters";
                }
                try {
                    String[] keywords = keyword.split("\\s+");
                    if(keywords.length == 0 )
                            keywords = new String[] {keyword};
                    SubjectList list = SearchHandling.processKeywordSearch(keywords);
                    Logger.getLogger(SearchAccess.class).info("Found "+list.size() + " matches");
                    response.status(200);
                    response.type(MIMETYPE_XML);
                    return list.toXMLList();
                } catch (Exception e) {
                    Logger.getLogger(SearchAccess.class).error("Search by keyword",e);
                    response.status(500);
                    return  e.getMessage();
                }

            }
        });
    }
}

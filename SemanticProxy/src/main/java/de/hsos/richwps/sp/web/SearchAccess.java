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
 *
 * @author fbensman
 */
public class SearchAccess {

    private static final String MIMETYPE_RDF = "application/xml";
    //private static final String MIMETYPE_RDF = "application/rdf+xml";
    private static final String MIMETYPE_HTML = "text/html";
    private static final String MIMETYPE_XML = "application/xml";

    /**
     * Registeres the required routes an handlers
     */
    public SearchAccess(URL searchURL) {

        /**
         * Registers the route for root for user convenience
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
                    return "Empty search query.";
                }
                try {
                    SubjectList list = SearchHandling.processKeywordSearch(keyword);
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

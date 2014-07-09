/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.web;

import de.hsos.richwps.sp.restlogic.SearchHandling;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.URL;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;

/**
 *
 * @author fbensman
 */
public class SearchAccess {

    /**
     * Registeres the required routes an handlers
     */
    public SearchAccess(URL searchURL) {


        /**
         * Registers the route for root for user convenience
         */
        get(new Route(searchURL.getPath()) {
            @Override
            public Object handle(Request request, Response response) {
                String keyword = (String) request.queryParams("keyword");
                if (keyword == null) {
                    response.status(400);
                    return "Empty search query.";
                }
                try {
                    SubjectList list = SearchHandling.processKeywordSearch(keyword);
                    response.status(200);
                    return list.toXMLList();
                } catch (Exception e) {
                    response.status(500);
                    return "Internal server error: " + e.getMessage();
                }

            }
        });
    }
}

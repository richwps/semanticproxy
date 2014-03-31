/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.RDFDescription;
import de.hsos.richwps.sp.types.RDFDocument;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Translates resource requests and executes the queries for data retrieval from
 * db
 *
 * @author fbensman
 */
public class RouteMapper {

    /**
     * Translates the stub route to a full valid route and retrieves the
     * corresponding triples from the db
     *
     * @param route the route stub for the resource retrieval
     * @return RDFDocument describing the resource
     * @throws Exception When the URI is malformed or the db cannot be accessed
     */
    public static RDFDocument getRDFFor(String route) throws Exception {

        //cut off fragment from uri - jetty already removes fragments
        String refinedRoute = null;
        if (route.contains("#")) {
            int idx = route.lastIndexOf("#");
            refinedRoute = route.substring(0, idx);
            System.out.println(refinedRoute);
        } else {
            refinedRoute = route;
        }
        refinedRoute = URIConfiguration.HOST_URI + refinedRoute;
        URI resource = null;
        try {
            resource = new URI(refinedRoute);
        } catch (URISyntaxException e) {
            throw new Exception("Cannot parse URI " + refinedRoute + ", " + e.getMessage());
        }
        RDFDescription desc = DBIO.getResourceDescription(resource);
        RDFDocument doc = new RDFDocument();
        doc.addDescription(desc);
        return doc;
    }
    
    
    /**
     * Returns all processes in db
     * @return XML list of process uris
     * @throws Exception When the the db cannot be connected
     */
    public static String getAllProcesses() throws Exception{
        
        URI processTypeURI = new URI(Vocabulary.ProcessClass);
        SubjectList list = DBIO.getAllSubjectsForType(processTypeURI);
        return list.toXMLList();
    }
    
    
    /**
     * Returns all wps in db
     * @return XML list of wps uris
     * @throws Exception When the the db cannot be connected
     */
    public static String getAllWPS() throws Exception{
        
        URI wpsTypeURI = new URI(Vocabulary.WPSClass);
        SubjectList list = DBIO.getAllSubjectsForType(wpsTypeURI);
        return list.toXMLList();
    }
    
}

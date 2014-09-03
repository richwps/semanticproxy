/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.repository.RepositoryException;

/**
 * Translates resource requests and executes the queries for data retrieval from
 * db
 *
 * @author fbensman
 */
public class ContentGetter {

    /**
     * Translates the stub route to a full valid route and retrieves the
     * corresponding triples from the db
     *
     * @param route the route stub for the resource retrieval
     * @return RDFDocument describing the resource
     * @throws Exception When the URI is malformed or the db cannot be accessed
     */
    public static String getRDFFor(String route) throws MalformedURLException, RDFException, RepositoryException {

        //cut off fragment from uri - jetty already removes fragments
        String refinedRoute = null;
        if (route.contains("#")) {
            int idx = route.lastIndexOf("#");
            refinedRoute = route.substring(0, idx);
        } else {
            refinedRoute = route;
        }
        URL resource = null;
        try {
            resource = new URL(refinedRoute);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Cannot parse URI " + refinedRoute + ", " + e.getMessage());
        }
        try {
            return DBIO.getResourceDescription(resource);
        } catch (RDFException re) {
            throw new RDFException("Cannot get RDF for resource " + route + ".", re);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot RDF for resource " + route + ".", re);
        }
    }

    /**
     * Returns all processes in db
     *
     * @return XML list of process uris
     * @throws Exception When the the db cannot be connected
     */
    public static String getAllProcesses() throws MalformedURLException, RepositoryException, RDFException {

        URL processTypeURI = new URL(Vocabulary.ProcessClass);
        SubjectList list;
        try {
            list = DBIO.getAllSubjectsForType(processTypeURI);
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot get all processes.", ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot get all processes.", ex);
        } catch (RDFException ex) {
            throw new RDFException("Cannot get all processes.", ex);
        }
        return list.toXMLList();
    }

    /**
     * Returns all wps in db
     *
     * @return XML list of wps uris
     * @throws Exception When the the db cannot be connected
     */
    public static String getAllWPS() throws MalformedURLException, RepositoryException, RDFException {
        URL wpsTypeURL = null;
        SubjectList list = null;
        try {
            wpsTypeURL = new URL(Vocabulary.WPSClass);
            list = DBIO.getAllSubjectsForType(wpsTypeURL);
        } catch (MalformedURLException ex) {
            throw new MalformedURLException("Cannot get all processes.");
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot get all processes.", ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot get all processes.", ex);
        } catch (RDFException ex) {
            throw new RDFException("Cannot get all processes.", ex);
        }
        return list.toXMLList();
    }

    /**
     * Determines whether a process exists in the DB
     * @param processRoute
     * @return True if process exists, else false
     */
    public static boolean processExists(String processRoute) throws RepositoryException, RDFException, MalformedURLException {
        try {
            URL processURL = new URL(processRoute);
            SubjectList subjList = DBIO.getAllSubjectsForType(new URL(Vocabulary.ProcessClass));
            for(URL subj : subjList){
                if(subj.equals(processURL))
                    return true;
            }
            return false;
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot determine whether process ."+processRoute+" exists. "+ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot determine whether process ."+processRoute+" exists. "+ex);
        } catch (RDFException ex) {
            throw new RDFException("Cannot determine whether process ."+processRoute+" exists. "+ex);
        } catch (MalformedURLException ex) {
            throw new MalformedURLException("Cannot determine whether process ."+processRoute+" exists. "+ex);
        }
    }
    
  
     /**
     * Determines whether a wps exists in the DB
     * @param wpsRoute
     * @return True if WPS exists, else false
     */
    public static boolean wpsExists(String wpsRoute) throws RepositoryException, RDFException, MalformedURLException {
        try {
            URL wpsURL = new URL(wpsRoute);
            SubjectList subjList = DBIO.getAllSubjectsForType(new URL(Vocabulary.WPSClass));
            for(URL subj : subjList){
                if(subj.equals(wpsURL))
                    return true;
            }
            return false;
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot determine whether WPS "+wpsRoute+" exists. "+ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot determine whether WPS "+wpsRoute+" exists. "+ex);
        } catch (RDFException ex) {
            throw new RDFException("Cannot determine whether WPS "+wpsRoute+" exists. "+ex);
        } catch (MalformedURLException ex) {
            throw new MalformedURLException("Cannot determine whether WPS "+wpsRoute+" exists. "+ex);
        }
    }
    
    
}

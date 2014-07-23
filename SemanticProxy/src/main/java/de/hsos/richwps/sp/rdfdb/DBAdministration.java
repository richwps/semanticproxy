/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import java.io.File;
import java.net.URL;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

/**
 * Contains methods for basic db administration like init, close clear
 *
 * @author fbensman
 */
public class DBAdministration {

    private static Repository repository = null;
    private static URL resourceURL = null;
    
    
    /**
     * Connects to an RDF-DB, if DB not exists it is created.
     * @param rdfRepositoryDir Directory in which to store the RDF DB and log files.
     * @param baseResourceURL Base URL for all RDF resources for comparison with following insertions
     * @throws RepositoryException If the repository cannot be opened
     */
    public static void init(File rdfRepositoryDir, URL baseResourceURL) throws RepositoryException {
        if (repository == null) {
            repository = new SailRepository(new MemoryStore(rdfRepositoryDir));
            try {
                repository.initialize();
            } catch (RepositoryException e) {
                throw new RepositoryException("Cannot connect to sesame RDF-DB in " + rdfRepositoryDir.getAbsolutePath() +"",e);
            }
        }
        else
        {
            throw new IllegalStateException("Cannot connect to sesame RDF-DB. A repository is already open in " + repository.getDataDir().toString());
        }
        resourceURL = baseResourceURL;
    }

    
    /**
     * Closes the connection to the db
     * @throws IllegalStateException If the db has not been opened by SP
     * @throws RepositoryException  If there is something wrong with the db
     */
    public static void close() throws IllegalStateException, RepositoryException {
        if (repository == null) {
            throw new IllegalStateException("Cannot close sesame RDF-DB, not connected.");
        }
        try {
            repository.shutDown();
            repository = null;
        } catch (RepositoryException e) {
            throw new RepositoryException("Cannot shut down sesame RDF-DB, "+e.getMessage());
        }
    }

    /**
     * Deletes the whole db content, the file remains
     *
     * @throws Exception
     */
    public static void clear() throws IllegalStateException, RepositoryException {
        if (repository == null) {
            throw new IllegalStateException("Cannot clear sesame RDF-DB, not connected.");
        }
        try {
            RepositoryConnection con = repository.getConnection();
            Resource[] dummyResources = new Resource[0];
            con.clear(dummyResources);
            con.close();
        } catch (RepositoryException e) {
            throw new RepositoryException("Cannot clear sesame RDF-DB",e);
        }
    }
    
    
    /**
     * Static access to the RDF repository object
     * @return 
     */
    public static Repository getRepository(){
        return repository;
    }
    
    
    /**
     * Static access to the RDF repository object
     * @return 
     */
    public static URL getResourceURL(){
        return resourceURL;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import java.io.File;
import org.openrdf.model.Resource;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.sail.memory.MemoryStore;

/**
 * Contains methods for basic db administration like connect, close clear
 *
 * @author fbensman
 */
public class DBAdministration {

    private static Repository repository = null;
    
    
    /**
     * Connects to an RDF-DB, if DB not exists it is created.
     *
     * @throws Exception if a repository is already open
     */
    public static void connect(File rdfRepositoryDir) throws Exception {
        if (repository == null) {
            repository = new SailRepository(new MemoryStore(rdfRepositoryDir));
            try {
                repository.initialize();
            } catch (RepositoryException e) {
                throw new Exception("Cannot connect to sesame RDF-DB in " + rdfRepositoryDir.getAbsolutePath() + ", initialize failed.");
            }
        }
        else
        {
            throw new Exception("Cannot connect to sesame RDF-DB. A repository is still open in " + repository.getDataDir().toString());
        }
    }

    /**
     * Closes the connection to the db
     *
     * @throws Exception
     */
    public static void close() throws Exception {
        if (repository == null) {
            throw new Exception("Cannot close sesame RDF-DB, not connected.");
        }
        try {
            repository.shutDown();
            repository = null;
        } catch (RepositoryException e) {
            throw new Exception("Cannot shut down sesame RDF-DB, "+e.getMessage());
        }
    }

    /**
     * Deletes the whole db content, the file remains
     *
     * @throws Exception
     */
    public static void clear() throws Exception {
        if (repository == null) {
            throw new Exception("Cannot clear sesame RDF-DB, not connected.");
        }
        try {
            RepositoryConnection con = repository.getConnection();
            Resource[] dummyResources = new Resource[0];
            con.clear(dummyResources);
            con.close();
        } catch (RepositoryException e) {
            throw new Exception("Cannot clear sesame RDF-DB, unable to retrieve a connection");
        }
    }
    
    
    /**
     * Static access to the RDF repository object
     * @return 
     */
    public static Repository getRepository(){
        return repository;
    }
}

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

    /**
     * Connects to an RDF-DB, if DB not existing
     *
     * @throws Exception
     */
    public static void connect() throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            File dataDir = new File(SesameProperties.DEFAULT_REPOSITORY_DIR);
            repo = new SailRepository(new MemoryStore(dataDir));
            try {
                repo.initialize();
            } catch (RepositoryException e) {
                throw new Exception("Cannot connect to sesame RDF-DB in " + dataDir.getAbsolutePath() + ", initialize failed.");
            }
            SesameProperties.getInstance().setRepository(repo);
        }



    }

    /**
     * Closes the connection to the db
     *
     * @throws Exception
     */
    public static void close() throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot close sesame RDF-DB, not connected.");
        }
        try {
            repo.shutDown();
            SesameProperties.getInstance().setRepository(null);
        } catch (RepositoryException e) {
            throw new Exception("Cannot clear sesame RDF-DB, not connected.");
        }
    }

    /**
     * Deletes the whole db content, the file remains
     *
     * @throws Exception
     */
    public static void clear() throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot clear sesame RDF-DB, not connected.");
        }
        try {
            RepositoryConnection con = repo.getConnection();
            Resource[] dummyResources = new Resource[0];
            con.clear(dummyResources);
            con.close();
        } catch (RepositoryException e) {
            throw new Exception("Cannot clear sesame RDF-DB, unable to retrieve a connection");
        }
    }
}

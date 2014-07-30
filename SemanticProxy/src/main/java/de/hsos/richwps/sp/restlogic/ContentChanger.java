/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

/**
 *
 * @author fbensman
 */
public class ContentChanger {

    /**
     * Pushs process rdf into db
     *
     * @param rawRDF
     * @throws Exception
     */
    public static void pushProcessRDFintoDB(String rawRDF) throws RDFException, IOException, RepositoryException, Exception {

        ArrayList<Statement> statList = null;
        ValidationResult result = null;
        try {
            statList = decomposeIntoStatements(rawRDF);
            result = Validator.checkForInsertProcess(statList);
        } catch (RDFException re) {
            throw new RDFException("Cannot insert process into db, decomposition error.", re);
        } catch (IOException ioe) {
            throw new IOException("Cannot insert process into db, decomposition error.", ioe);
        } catch (Exception e) {
            throw new Exception("Cannot insert process into db.", e);
        }
        try {
            if (result.result) {
                DBIO.loadRDFXMLStringIntoDB(rawRDF);

                //create inverse link to reference from wps to this process
                Statement[] stats = Validator.getStatementsByPredicate(Vocabulary.WPS, statList);
                Resource subject = new URIImpl(stats[0].getObject().stringValue());
                org.openrdf.model.URI predicate = new URIImpl(Vocabulary.Process);
                org.openrdf.model.URI object = new URIImpl(stats[0].getSubject().stringValue());
                Statement stmt = new StatementImpl(subject, predicate, object);
                DBIO.insertStatement(stmt);
            } else {
                throw new Exception("Cannot push process rdf into db, data malformed: " + result.message);
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot insert process into db.", re);

        }

    }

    /**
     * Deletes a process from the db
     *
     * @param route An rdf resource identifiere for the process
     * @return True if success
     * @throws Exception When sth is wrong with the db
     */
    public static boolean deleteProcess(String route) throws MalformedURLException, RepositoryException, RDFException, Exception {
        URL process = new URL(route);
        boolean b = false;
        try {
            b = DBIO.subjectExists(process);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete process " + route + ", unable to dermine if it exists.", re);
        } catch (RDFException re) {
            throw new RDFException("Cannot delete process " + route + ", unable to dermine if it exists.", re);
        }

        if (b) {
            try {
                DBDelete.deleteProcess(route);
                return true;
            } catch (RepositoryException re) {
                throw new RepositoryException("Cannot delete process " + route + ".", re);
            } catch (Exception e) {
                throw new Exception("Cannot delete process " + route + ".", e);
            }
        } else {
            return false;
        }
    }

    /**
     * Deletes a wps from the db
     *
     * @param route An rdf resource identifiere for the wps
     * @return True if success
     * @throws Exception When sth is wrong with the db
     */
    public static boolean deleteWPS(String route) throws MalformedURLException, RepositoryException, RDFException, Exception {
        URL wps = new URL(route);

        boolean b = false;
        try {
            b = DBIO.subjectExists(wps);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete wps " + route + ", unable to dermine if it exists.", re);
        } catch (RDFException re) {
            throw new RDFException("Cannot delete wps " + route + ", unable to dermine if it exists.", re);
        }

        if (b) {
            try {
                DBDelete.deleteWPS(route);
                return true;
            } catch (RepositoryException re) {
                throw new RepositoryException("Cannot delete wps " + route + ".", re);
            } catch (Exception e) {
                throw new Exception("Cannot delete wps " + route + ".", e);
            }
        } else {
            return false;
        }

    }

    /**
     * pushes raw rdf into db
     *
     * @param rawRDF
     * @throws Exception
     */
    public static void pushWPSRDFintoDB(String rawRDF) throws RDFException, IOException, RepositoryException, URISyntaxException, Exception {
        ArrayList<Statement> statList = null;
        ValidationResult result = null;
        try {
            statList = decomposeIntoStatements(rawRDF);
            result = Validator.checkForInsertWPS(statList);
        } catch (RDFException re) {
            throw new RDFException("Cannot insert wps into db, decomposition error.", re);
        } catch (IOException ioe) {
            throw new IOException("Cannot insert wps into db, decomposition error.", ioe);
        } catch (Exception e) {
            throw new Exception("Cannot insert wps into db, decomposition error.", e);
        }
        try {
            if (result.result) {
                DBIO.loadRDFXMLStringIntoDB(rawRDF);
                //Create link from network to new wps
                Statement[] stats = Validator.getStatementsByPredicate(Vocabulary.Type, statList);
                Resource subject = new URIImpl(DBAdministration.getResourceURL().toString());
                org.openrdf.model.URI predicate = new URIImpl(Vocabulary.WPS);
                org.openrdf.model.URI object = new URIImpl(stats[0].getSubject().stringValue());
                Statement stmt = new StatementImpl(subject, predicate, object);
                DBIO.insertStatement(stmt);
            } else {
                throw new Exception("Cannot push wps rdf into db, data malformed: " + result.message);
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot insert wps into db, decomposition error.", re);
        } catch (RDFException rde) {
            throw new RDFException("Cannot insert wps into db, decomposition error.", rde);
        } catch (IOException ioe) {
            throw new IOException("Cannot insert wps into db, decomposition error.", ioe);
        } catch (URISyntaxException use) {
            throw new URISyntaxException("Cannot insert wps into db, decomposition error.", use.getMessage());
        }
    }

    /**
     * Decompoeses an rdf string into separate statements
     *
     * @param rdfXml
     * @return
     * @throws Exception
     */
    private static ArrayList<Statement> decomposeIntoStatements(String rdfXml) throws IOException, RDFException, Exception {
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try {
            rdfParser.parse(new StringReader(rdfXml), DBAdministration.getResourceURL().toString());
        } catch (IOException ioe) {
            throw new IOException("Cannot decompose into statement, parsing failed.", ioe);
        } catch (RDFParseException rpe) {
            throw new RDFException("Cannot decompose into statement, parsing failed.", rpe);
        } catch (RDFHandlerException rhe) {
            throw new RDFException("Cannot decompose into statement, parsing failed.", rhe);
        } catch (Exception e) {
            throw new Exception("Cannot decompose into statement, parsing failed.", e);
        }
        return inputList;
    }

    /**
     * Validates and updates a wps
     *
     * @param rawRDF The rdf descripton of the wps
     * @param route The wps to update
     * @throws Exception
     */
    public static void updateWPS(String rawRDF, String route) throws RepositoryException, RDFException, IOException, URISyntaxException, Exception {

        boolean b = false;
        try {
            b = DBIO.subjectExists(new URL(route));
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot update wps " + route + ". Unable to determine if it exists", re);
        } catch (MalformedURLException mue) {
            throw new RepositoryException("Cannot update wps " + route + ". Unable to determine if it exists", mue);
        } catch (RDFException re) {
            throw new RepositoryException("Cannot update wps " + route + ". Unable to determine if it exists", re);
        }

        ArrayList<Statement> statList = null;
        ValidationResult result = null;
        if (b) {
            try {
                statList = decomposeIntoStatements(rawRDF);
                result = Validator.checkForUpdateWPS(statList);
            } catch (RDFException re) {
                throw new RDFException("Cannot update wps " + route + ", decomposition error.", re);
            } catch (IOException ioe) {
                throw new IOException("Cannot update wps " + route + ", decomposition error.", ioe);
            } catch (Exception e) {
                throw new Exception("Cannot update wps " + route + ".", e);
            }

            if (result.result) {
                try {
                    Statement[] stats = Validator.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.WPSClass, statList);
                    URI subject = new URI(stats[0].getSubject().stringValue());
                    DBDelete.deleteWPSForUpdate(subject);
                } catch (RepositoryException re) {
                    throw new RepositoryException("Cannot update wps " + route + ", unable to delete older wps", re);
                } catch (URISyntaxException re) {
                    throw new URISyntaxException("Cannot update wps " + route + ", unable to delete older wps", re.getMessage());
                } catch (Exception e) {
                    throw new Exception("Cannot update wps " + route + ", unable to delete older wps", e);
                }

                try {
                    DBIO.loadRDFXMLStringIntoDB(rawRDF);
                } catch (RepositoryException re) {
                    throw new RepositoryException("Cannot update wps " + route + ", unable to insert new wps", re);
                } catch (RDFException re) {
                    throw new RDFException("Cannot update wps " + route + ", unable to insert new wps", re);
                } catch (IOException ioe) {
                    throw new RDFException("Cannot update wps " + route + ", unable to insert new wps", ioe);
                }
            } else {
                throw new Exception(result.message);
            }
        } else {
            throw new Exception("No such WPS");
        }

    }

    
    
    /**
     * Validates and updates a process
     * @param rawRDF
     * @param route
     * @throws RepositoryException
     * @throws RDFException
     * @throws IOException
     * @throws URISyntaxException
     * @throws Exception 
     */
    public static void updateProcess(String rawRDF, String route) throws RepositoryException, RDFException, IOException, URISyntaxException, Exception {

        boolean b = false;
        try {
            b = DBIO.subjectExists(new URL(route));
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot update process " + route + ". Unable to determine if it exists", re);
        } catch (MalformedURLException mue) {
            throw new RepositoryException("Cannot update process " + route + ". Unable to determine if it exists", mue);
        } catch (RDFException re) {
            throw new RepositoryException("Cannot update process " + route + ". Unable to determine if it exists", re);
        }

        ArrayList<Statement> statList = null;
        ValidationResult result = null;

        if (b) {
            try {
                statList = decomposeIntoStatements(rawRDF);
                result = Validator.checkForUpdateProcess(statList);
            } catch (RDFException re) {
                throw new RDFException("Cannot update process " + route + ", decomposition error.", re);
            } catch (IOException ioe) {
                throw new IOException("Cannot update process " + route + ", decomposition error.", ioe);
            } catch (Exception e) {
                throw new Exception("Cannot update process " + route + ".", e);
            }


            Statement[] stats = null;
            if (result.result) {
                try {
                    stats = Validator.getStatementsByPredicateAndObject(Vocabulary.Type, Vocabulary.WPSClass, statList);
                    DBDelete.deleteProcess(route);
                } catch (RepositoryException re) {
                    throw new RepositoryException("Cannot update process " + route + ", unable to delete older process", re);
                } catch (URISyntaxException re) {
                    throw new URISyntaxException("Cannot update process " + route + ", unable to delete older process", re.getMessage());
                } catch (Exception e) {
                    throw new Exception("Cannot update process " + route + ", unable to delete older process", e);
                }

                try {
                    DBIO.loadRDFXMLStringIntoDB(rawRDF);
                } catch (RepositoryException re) {
                    throw new RepositoryException("Cannot update process " + route + ", unable to insert new process", re);
                } catch (RDFException re) {
                    throw new RDFException("Cannot update process " + route + ", unable to insert new process", re);
                } catch (IOException ioe) {
                    throw new RDFException("Cannot update process " + route + ", unable to insert new process", ioe);
                }

                //create inverse link to reference from wps to this process
                try {
                    stats = Validator.getStatementsByPredicate(Vocabulary.WPS, statList);
                    Resource subject = new URIImpl(stats[0].getObject().stringValue());
                    org.openrdf.model.URI predicate = new URIImpl(Vocabulary.Process);
                    org.openrdf.model.URI object = new URIImpl(stats[0].getSubject().stringValue());
                    Statement stmt = new StatementImpl(subject, predicate, object);
                    DBIO.insertStatement(stmt);
                } catch (RepositoryException re) {
                    throw new RepositoryException("Cannot update process " + route + ", unable to create link from parent wps to process.", re);
                }catch (RDFException re) {
                    throw new RDFException("Cannot update process " + route + ", unable to create link from parent wps to process.", re);
                }
            } else {
                throw new Exception(result.message);
            }
        } else {
            throw new Exception("No such process");
        }
    }

    
    /**
     * Inserts network statements that serve as root node
     * @param owner
     * @param domain
     * @throws Exception 
     */
    public static void insertNetwork(String owner, String domain) throws Exception {
        Resource networkSubject = new URIImpl(DBAdministration.getResourceURL().toString());

        org.openrdf.model.URI typePred = new URIImpl(Vocabulary.Type);
        org.openrdf.model.URI networkClassObj = new URIImpl(Vocabulary.NetworkClass);
        DBIO.insertStatement(new StatementImpl(networkSubject, typePred, networkClassObj));

        org.openrdf.model.URI ownerPred = new URIImpl(Vocabulary.Owner);
        Literal ownerObj = new LiteralImpl(owner);
        DBIO.insertStatement(new StatementImpl(networkSubject, ownerPred, ownerObj));

        org.openrdf.model.URI domainPred = new URIImpl(Vocabulary.Domain);
        Literal domainObj = new LiteralImpl(domain);
        DBIO.insertStatement(new StatementImpl(networkSubject, domainPred, domainObj));
    }
}

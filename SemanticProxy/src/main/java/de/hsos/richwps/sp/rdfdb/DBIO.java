/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.Vocabulary;

import de.hsos.richwps.sp.types.SubjectList;
import de.hsos.richwps.sp.types.Triple;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.UnsupportedQueryLanguageException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;

/**
 * Contains methods for db queries
 *
 * @author fbensman
 */
public class DBIO {

    /**
     * Loads an xml/rdf file into the db
     *
     * @param file
     * @throws IllegalStateException When the DB is in a false state due to
     * application use.
     * @throws RepositoryException When the repository cannot be accessed
     * @throws RDFException When the RDF data in the file is malformed
     * @throws IOException When the file cannot be opened
     */
    public static void loadRDFXMLFile(File file) throws IllegalStateException, RepositoryException, RDFException, IOException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            con.add(file, DBAdministration.getResourceURL().toString(), RDFFormat.RDFXML, resArr);

        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException rpe) {
            throw new RDFException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, ", rpe);
        } catch (UnsupportedRDFormatException urfe) {
            throw new RDFException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, ", urfe);
        } catch (IOException ioe) {
            throw new IOException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, ", ioe);
        } finally {
            con.close();
        }
    }

    /**
     * Loads an xml/rdf string into the db
     *
     * @param str
     * @throws IllegalStateException
     * @throws RepositoryException
     * @throws RDFException
     * @throws IOException
     */
    public static void loadRDFXMLStringIntoDB(String str) throws IllegalStateException, RepositoryException, RDFException, IOException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            con.add(new StringReader(str), DBAdministration.getResourceURL().toString(), RDFFormat.RDFXML, resArr);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot insert rdf string into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException rpe) {
            throw new RDFException("Cannot insert rdf string into sesame RDF-DB, ", rpe);
        } catch (UnsupportedRDFormatException urfe) {
            throw new RDFException("Cannot insert rdf string into sesame RDF-DB, ", urfe);
        } catch (IOException ioe) {
            throw new IOException("Cannot insert rdf string into sesame RDF-DB, ", ioe);
        } finally {
            con.close();
        }
    }

    /**
     * Returns the size of the db
     *
     * @return
     * @throws IllegalStateException
     * @throws RepositoryException
     */
    public static long size() throws IllegalStateException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot retrieve size of sesame RDF-DB, not connected."); 
        }
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            long size = con.size();
            return size;
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot retrieve size of sesame RDF-DB, unknown connection error.", re);
        } finally {
            con.close();
        }
    }

    /**
     * Gets an RDF description for a resource URI
     *
     * @param resource The resource to describe as URI
     * @return A string containing the description as RDF document
     * @throws Exception When the db is not accessable or the resource is
     * malformed
     */
    public static String getResourceDescription(URL resource) throws RDFException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot get resource description for " + resource + ", not connected.");
        }

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            Resource subject = new URIImpl(resource.toString());
            //query all statement about the resource
            RepositoryResult<Statement> result = con.getStatements(subject, null, null, true);
            ArrayList<Statement> list = new ArrayList<>();
            //put statements into a list an feed 'em to an rdf writer
            try {
                while (result.hasNext()) {
                    list.add(result.next());
                }
                result.close();
                if (list.isEmpty()) {
                    return null;
                }
                StringWriter sw = new StringWriter();
                RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, sw);
                try {
                    writer.startRDF();
                    for (int i = 0; i < list.size(); i++) {
                        writer.handleStatement(list.get(i));
                    }
                    writer.endRDF();
                    return sw.toString();
                } catch (RDFHandlerException rhe) {
                    throw new RDFException("Cannot get resource description for " + resource, rhe);
                }
            } finally {
                result.close();
            }

        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get resource description for " + resource, re);
        } finally {
            con.close();
        }
    }

    /**
     * Returns all statements with the subject and predicate.
     *
     * @param subject
     * @param predicate
     * @return
     * @throws Exception
     */
    public static Statement[] getStatementsForSubjAndPred(URL subject, URL predicate) throws IllegalStateException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot get resource description for " + subject.toString() + " and " + predicate.toString() + ", not connected.");
        }

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            Resource sesamSubject = new URIImpl(subject.toString());
            org.openrdf.model.URI sesamPredicate = new URIImpl(predicate.toString());
            //query all statement about the resource
            RepositoryResult<Statement> result = con.getStatements(sesamSubject, sesamPredicate, null, true);

            ArrayList<Statement> list = new ArrayList<>();
            //put statements into a list

            while (result.hasNext()) {
                list.add(result.next());
            }
            result.close();

            return list.toArray(new Statement[list.size()]);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get resource description for " + subject.toString() + " and " + predicate.toString() + ".", re);
        }


    }

    



    

    /**
     * Determines whether a string is a URL with http protocol type, host, and
     * path
     *
     * @param str The URL to check
     * @return True if str is a sufficient URL, false if not
     */
    public static boolean isRDFConformURL(String str) {
        try {
            URL url = new URL(str);
            String pro = url.getProtocol();
            String host = url.getHost();
            String path = url.getPath();
            if (pro.equals("http")) {
                if (host != null && !host.equals("")) {
                    if (path != null && !path.equals("")) {
                        return true;
                    }
                }
            }
            return false;
        } catch (MalformedURLException mue) {
            return false;
        }

    }

    
    
    /**
     * Queries all subject of the given type uri from the db
     * @param type
     * @return
     * @throws IllegalStateException
     * @throws MalformedURLException
     * @throws RepositoryException
     * @throws RDFException 
     */
    public static SubjectList getAllSubjectsForType(URL type) throws IllegalStateException, MalformedURLException, RepositoryException, RDFException{
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot get all subjects for type" + type + ", not connected.");
        }
        if (!Vocabulary.isBasicType(type.toString())) {
            throw new IllegalArgumentException("Cannot get all subjects for type" + type + ", parameter is not a type.");
        }
        String rdfType = Vocabulary.Type;
        //define query
        String queryString = "SELECT ?s WHERE { ?s <" + rdfType + "> <" + type + "> } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //execute query
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString, DBAdministration.getResourceURL().toString());
            result = tupleQuery.evaluate();

            SubjectList subjectList = new SubjectList();
            try {
                //collect results
                while (result.hasNext()) {
                    BindingSet bindingSet = (BindingSet) result.next();
                    Value valueOfS = bindingSet.getValue("s");
                    URL subject = new URL(valueOfS.stringValue());
                    subjectList.add(subject);
                }
                return subjectList;
            } catch (MalformedURLException mue) {
                throw new MalformedURLException("Cannot get all subjects for type" + type + ".");
            } finally {
                result.close();
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get all subjects for type" + type + ", unknown connection error.",re);
        } catch(MalformedQueryException mqe){
            throw new RDFException("Cannot get all subjects for type" + type + ", unknown connection error.",mqe);
        } catch(QueryEvaluationException mqe){
            throw new RDFException("Cannot get all subjects for type" + type + ", unknown connection error.",mqe);
        }finally {
            con.close();
        }
    }
    
    
    

    /**
     * Queries all subject of the given resource from the db
     * @param subject
     * @return
     * @throws IllegalStateException
     * @throws RepositoryException
     * @throws RDFException 
     */
    public static boolean subjectExists(URL subject) throws IllegalStateException, RepositoryException, RDFException  {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot check if subject exists, not connected.");
        }
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            Resource process = (Resource) new URIImpl(subject.toString());
            org.openrdf.model.URI hasType = new URIImpl(Vocabulary.Type);
            Resource[] res = new Resource[0];
            //decicive call
            boolean has = con.hasStatement(process, hasType, null, false, res);

            return has;
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot check if subject " + subject + " exists, unknown connection error.",re);
        } catch (UnsupportedQueryLanguageException uqle) {
            throw new RDFException("Cannot check if subject " + subject + " exists.",uqle);
        } finally {
            con.close();
        }
    }

    /**
     * Inserts a triple into db
     *
     * @param triple
     * @throws Exception
     */
    public static void insertTriple(Triple triple) throws IllegalStateException, RepositoryException, RDFException{
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot insert triple into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //triple to statement
            Resource subject = new URIImpl(triple.getSubject().toString());
            org.openrdf.model.URI predicate = new URIImpl(triple.getPredicate().toString());
            Value obj = null;
            if (triple.getObjectAsLiteral() == null) {
                obj = new URIImpl(triple.getObjectAsResource().toString());
            } else {
                obj = new LiteralImpl(triple.getObjectAsLiteral());
            }
            Statement st = new StatementImpl(subject, predicate, obj);
            //add stmt to db
            con.add(st, resArr);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.",re);
        } catch (UnsupportedRDFormatException urfe) {
            throw new RDFException("Cannot load rdf/xml string into sesame RDF-DB.",urfe);
        } finally {
            con.close();
        }

    }

    /**
     * Inserts an RDF statement into db
     *
     * @param triple
     * @throws Exception
     */
    public static void insertStatement(Statement stmt) throws Exception {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot insert Statement into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = repo.getConnection();
        try {
            con.add(stmt, resArr);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.",re);
        } catch (UnsupportedRDFormatException urfe) {
            throw new RDFException("Cannot load rdf/xml string into sesame RDF-DB.",urfe);
        } finally {
            con.close();
        }

    }

    /**
     * Validates wether a certain string is a literal or a resource
     *
     * @param str
     * @return
     */
    public static boolean isLiteral(String str) {
        if (DBIO.isRDFConformURL(str)) {
            if (str.startsWith("\"") && str.endsWith("\"")) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}

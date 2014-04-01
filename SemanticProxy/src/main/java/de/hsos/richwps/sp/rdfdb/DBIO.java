/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.URIConfiguration;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.types.RDFDescription;
import de.hsos.richwps.sp.types.RDFDocument;
import de.hsos.richwps.sp.types.SubjectList;
import de.hsos.richwps.sp.types.Triple;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
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
     * @param file the file
     * @throws Exception When the file is malformed or not accessable
     */
    public static void loadRDFXMLFile(File file) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            con.add(file, URIConfiguration.RESOURCES_URI, RDFFormat.RDFXML, resArr);

        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Loads an xml/rdf string into the db
     *
     * @param file the file
     * @throws Exception When the string is malformede
     */
    public static void loadRDFXMLStringIntoDB(String str) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            con.add(new StringReader(str), URIConfiguration.RESOURCES_URI, RDFFormat.RDFXML, resArr);
        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Returns the size of the db
     *
     * @return Size in triples
     * @throws Exception When the db cannot be accessed
     */
    public static long size() throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot retrieve size of sesame RDF-DB, not connected.");
        }
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            long size = con.size();
            return size;
        } catch (RepositoryException e) {
            throw new Exception("Cannot retrieve size of sesame RDF-DB, unknown connection error.");
        } finally {
            con.close();
        }
    }

    /**
     * Gets an RDF description for a resource URI
     *
     * @param resource The resource to describe as URI
     * @return A collection of all triples describing the resource
     * @throws Exception When the db is not accessable or the resource is
     * malformed
     */
    public static RDFDescription getResourceDescription(URI resource) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot get resource description for " + resource + ", not connected.");
        }

        //define query
        String queryString = "SELECT ?p ?y WHERE { <" + resource + "> ?p ?y } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //execute query
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString, URIConfiguration.RESOURCES_URI);
            result = tupleQuery.evaluate();


            RDFDescription retVal = new RDFDescription(resource);
            //collect query result
            try {
                while (result.hasNext()) {
                    BindingSet bindingSet = (BindingSet) result.next();
                    Value valueOfP = bindingSet.getValue("p");
                    Value valueOfY = bindingSet.getValue("y");
                    URI predicate = new URI(valueOfP.stringValue());
                    Triple triple = null;
                    if (isRDFConformURL(valueOfY.stringValue())) {
                        URI objectval = new URI(valueOfY.stringValue());
                        triple = new Triple(resource, predicate, objectval);
                    } else {
                        triple = new Triple(resource, predicate, valueOfY.stringValue());
                    }
                    retVal.addTriple(triple);
                }
                return retVal;
            } catch (URISyntaxException e) {
                throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
            } catch (Exception e) {
                throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
            } finally {
                result.close();
            }

        } catch (RepositoryException e) {
            throw new Exception("Cannot get resource description for " + resource + ", unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (MalformedQueryException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (QueryEvaluationException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
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
    public static String getResourceDescriptionV2(URI resource) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot get resource description for " + resource + ", not connected.");
        }

        RepositoryConnection con = repo.getConnection();
        try {
            con = repo.getConnection();
            Resource subject = new URIImpl(resource.toString());
            //query all statement about the resource
            RepositoryResult<Statement> result = con.getStatements(subject, null, null, true);
            ArrayList<Statement> list = new ArrayList<Statement>();
            //put statements into a list an feed 'em to an rdf writer
            try {
                while (result.hasNext()) {
                    list.add(result.next());
                }
                result.close();
                if(list.size()==0)
                    return null;
                StringWriter sw = new StringWriter();
                RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, sw);
                try {
                    writer.startRDF();
                    for (int i=0; i<list.size();i++) {
                        writer.handleStatement(list.get(i));
                    }
                    writer.endRDF();
                    return sw.toString();
                } catch (RDFHandlerException e) {
                    throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
                }
            } catch (URISyntaxException e) {
                throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
            } catch (Exception e) {
                throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
            } finally {
                result.close();
            }

        } catch (RepositoryException e) {
            throw new Exception("Cannot get resource description for " + resource + ", unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (MalformedQueryException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (QueryEvaluationException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Returns the whole db in an RDFDocument
     *
     * @return RDF document of the db
     * @throws Exception When the db is not accessable
     */
    public static RDFDocument getWholeDBContent() throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot get whole DB content, not connected.");
        }

        //create query that matches every statement in db
        String queryString = "SELECT ?s ?p ?y WHERE { ?s ?p ?y } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //execute query
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString, URIConfiguration.RESOURCES_URI);
            result = tupleQuery.evaluate();

            RDFDocument retVal = new RDFDocument();
            try {
                //collect results
                while (result.hasNext()) {
                    BindingSet bindingSet = (BindingSet) result.next();
                    Value valueOfS = bindingSet.getValue("s");
                    Value valueOfP = bindingSet.getValue("p");
                    Value valueOfY = bindingSet.getValue("y");
                    URI subject = new URI(valueOfS.stringValue());
                    URI predicate = new URI(valueOfP.stringValue());
                    Triple triple = null;
                    if (isRDFConformURL(valueOfY.stringValue())) {
                        URI objectval = new URI(valueOfY.stringValue());
                        triple = new Triple(subject, predicate, objectval);
                    } else {
                        triple = new Triple(subject, predicate, valueOfY.stringValue());
                    }

                    if (!retVal.acceptsTriple(triple)) {
                        RDFDescription desc = new RDFDescription(triple.getSubject());
                        retVal.addDescription(desc);
                    }
                    retVal.addTriple(triple);
                }
                return retVal;
            } catch (URISyntaxException e) {
                throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
            } catch (Exception e) {
                throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
            } finally {
                result.close();
            }
        } catch (RepositoryException e) {
            throw new Exception("Cannot get db content, unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } catch (MalformedQueryException e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } catch (QueryEvaluationException e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } finally {
            con.close();
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
     *
     * @param type URI of type to look for
     * @return List of Subject URIs
     * @throws Exception When the db is not accessable
     */
    public static SubjectList getAllSubjectsForType(URI type) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot get all subjects for type" + type + ", not connected.");
        }
        if(!Vocabulary.isBasicType(type.toString()))
            throw new Exception("Cannot get all subjects for type" + type + ", parameter is not a type.");
        String rdfType = Vocabulary.Type;
        //define query
        String queryString = "SELECT ?s WHERE { ?s <" + rdfType + "> <" + type + "> } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //execute query
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString, URIConfiguration.RESOURCES_URI);
            result = tupleQuery.evaluate();

            SubjectList subjectList = new SubjectList();
            try {
                //collect results
                while (result.hasNext()) {
                    BindingSet bindingSet = (BindingSet) result.next();
                    Value valueOfS = bindingSet.getValue("s");
                    URI subject = new URI(valueOfS.stringValue());
                    subjectList.add(subject);
                }
                return subjectList;
            } catch (URISyntaxException e) {
                throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
            } catch (Exception e) {
                throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
            } finally {
                result.close();
            }
        } catch (RepositoryException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } catch (MalformedQueryException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } catch (QueryEvaluationException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Queries all subject of the given resource from the db
     *
     * @param type URI of type to look for
     * @return List of Subject URIs
     * @throws Exception When the db is not accessable
     */
    public static boolean subjectExists(URI subject) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot check if subject exists, not connected.");
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
        } catch (RepositoryException e) {
            throw new Exception("Cannot check if subject " + subject + " exists, unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot check if subject " + subject + " exists, " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot check if subject " + subject + " exists, " + e.toString() + " " + e.getMessage());
        } finally {
            con.close();
        }
    }

    
    /**
     * Inserts a triple into db
     * @param triple
     * @throws Exception 
     */
    public static void insertTriple(Triple triple) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot insert triple into sesame RDF-DB, not connected.");
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
        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.");
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
        } finally {
            con.close();
        }

    }

    
    /**
     * Validates wether a certain string is a literal or a resource
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

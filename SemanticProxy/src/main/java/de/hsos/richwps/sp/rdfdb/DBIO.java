/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.URIConfiguration;
import de.hsos.richwps.sp.types.RDFDescription;
import de.hsos.richwps.sp.types.RDFDocument;
import de.hsos.richwps.sp.types.SubjectList;
import de.hsos.richwps.sp.types.Triple;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.openrdf.model.Resource;
import org.openrdf.model.Value;
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
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
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

        try {
            RepositoryConnection con = repo.getConnection();
            con.add(file, URIConfiguration.RESOURCES_URI, RDFFormat.RDFXML, resArr);
            con.close();
        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, " + e.getMessage());
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
        try {
            RepositoryConnection con = repo.getConnection();
            
            con.add(new StringReader(str), URIConfiguration.RESOURCES_URI, RDFFormat.RDFXML, resArr);
            con.close();
        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
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
        try {
            RepositoryConnection con = repo.getConnection();
            long size = con.size();
            con.close();
            return size;
        } catch (RepositoryException e) {
            throw new Exception("Cannot retrieve size of sesame RDF-DB, unknown connection error.");
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

        String queryString = "SELECT ?p ?y WHERE { <" + resource + "> ?p ?y } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            result = tupleQuery.evaluate();
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
        }

        RDFDescription retVal = new RDFDescription(resource);
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
        } catch (URISyntaxException e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Cannot get resource description for " + resource + ", " + e.toString() + " " + e.getMessage());
        } finally {
            result.close();
            con.close();
        }
        return retVal;
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

        String queryString = "SELECT ?s ?p ?y WHERE { ?s ?p ?y } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            result = tupleQuery.evaluate();
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
        }


        RDFDocument retVal = new RDFDocument();
        try {
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
        } catch (URISyntaxException e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Cannot get db content, " + e.toString() + " " + e.getMessage());
        } finally {
            result.close();
            con.close();
        }
        return retVal;
    }

    /**
     * Determines whether a string is a URL with http protocol type, host, and
     * path
     *
     * @param str The URL to check
     * @return True if str is a sufficient URL, false if not
     */
    private static boolean isRDFConformURL(String str) {
        try {
            URL url = new URL(str);
            String pro = url.getProtocol();
            String host = url.getHost();
            String path = url.getPath();
            if (pro.equals("http")){
                if(host != null && !host.equals("")){
                    if(path != null && !path.equals("")){
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
     * Queries all subject of the given resource from the db
     * @param type URI of type to look for
     * @return List of Subject URIs
     * @throws Exception When the db is not accessable
     */
    public static SubjectList getAllSubjectsForType(URI type) throws Exception {
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot get all subjects for type" + type + ", not connected.");
        }
        String rdfType = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
        String queryString = "SELECT ?s WHERE { ?s <" + rdfType + "> <"+ type+"> } ";
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            result = tupleQuery.evaluate();
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
        }

        SubjectList subjectList = new SubjectList();
        try {
            while (result.hasNext()) {
                BindingSet bindingSet = (BindingSet) result.next();
                Value valueOfS = bindingSet.getValue("s");
                URI subject = new URI( valueOfS.stringValue() );
                subjectList.add(subject);
            }
        } catch (URISyntaxException e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } catch (Exception e) {
            throw new Exception("Cannot get all subjects for type" + type + ", " + e.toString() + " " + e.getMessage());
        } finally {
            result.close();
            con.close();
        }
        return subjectList;
    }
    
}

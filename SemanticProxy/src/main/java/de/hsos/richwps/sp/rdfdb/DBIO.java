/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.Vocabulary;

import de.hsos.richwps.sp.types.SubjectList;
import info.aduna.iteration.Iterations;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.openrdf.model.Literal;
import org.openrdf.model.Model;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;
import org.openrdf.model.vocabulary.XMLSchema;
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
    public static void insertRDFXMLFileIntoDB(File file) throws IllegalStateException, RepositoryException, RDFException, IOException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            con.begin();
            con.add(file, DBAdministration.getResourceURL().toString(), RDFFormat.RDFXML, resArr);
            con.commit();
        } catch (RepositoryException re) {
            con.rollback();
            throw new RepositoryException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException rpe) {
            con.rollback();
            throw new RDFException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, ", rpe);
        } catch (UnsupportedRDFormatException urfe) {
            con.rollback();
            throw new RDFException("Cannot load rdf/xml file " + file.getName() + " into sesame RDF-DB, ", urfe);
        } catch (IOException ioe) {
            con.rollback();
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
    public static void insertRDFXMLStringIntoDB(String str) throws IllegalStateException, RepositoryException, RDFException, IOException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot load rdf/xml file into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        con = repo.getConnection();
        try { 
            con.begin();
            con.add(new StringReader(str), DBAdministration.getResourceURL().toString(), RDFFormat.RDFXML, resArr);
            con.commit();
        } catch (RepositoryException re) {
            con.rollback();
            throw new RepositoryException("Cannot insert rdf string into sesame RDF-DB, not connected or not writable.");
        } catch (RDFParseException rpe) {
            con.rollback();
            throw new RDFException("Cannot insert rdf string into sesame RDF-DB, ", rpe);
        } catch (UnsupportedRDFormatException urfe) {
            con.rollback();
            throw new RDFException("Cannot insert rdf string into sesame RDF-DB, ", urfe);
        } catch (IOException ioe) {
            con.rollback();
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
            //write statements to rdf doc string
            try {  
                Model model = Iterations.addAll(result, new LinkedHashModel());
                
                if(model.isEmpty())
                    return null;
                
                model.setNamespace("rdf", RDF.NAMESPACE);
                model.setNamespace("rdfs", RDFS.NAMESPACE);
                model.setNamespace("xsd", XMLSchema.NAMESPACE);
                model.setNamespace("rwps", DBAdministration.getVocabularyURL()+"#");
                StringWriter sw = new StringWriter();             
                try {
                    Rio.write(model, sw, RDFFormat.RDFXML);
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
     * Returns all statements with the predicate and literal object.
     *
     * @param predicate
     * @param object
     * @return
     * @throws Exception
     */
    public static Statement[] getStatementsForPredAndLiteralObj(URL predicate, String object) throws IllegalStateException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot get statements for " + predicate.toString() + " and " + object.toString() + ", not connected.");
        }

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            org.openrdf.model.URI sesamPredicate = new URIImpl(predicate.toString());
            Literal sesamLiteralObject = new LiteralImpl(object);
            
            //query all statement about the resource
            RepositoryResult<Statement> result = con.getStatements(null, sesamPredicate, sesamLiteralObject, true);

            ArrayList<Statement> list = new ArrayList<>();
            //put statements into a list

            while (result.hasNext()) {
                list.add(result.next());
            }
            result.close();

            return list.toArray(new Statement[list.size()]);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get resource description for " + predicate.toString() + "and "+object+".", re);
        }


    }
    
    
    
    /**
     * Returns all statements with the predicate and resource object.
     *
     * @param predicate
     * @param object
     * @return
     * @throws Exception
     */
    public static Statement[] getStatementsForPredAndResourceObj(URL predicate, URL object) throws IllegalStateException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot get statements for " + predicate.toString() + " and " + object.toString() + ", not connected.");
        }

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            org.openrdf.model.URI sesamPredicate = new URIImpl(predicate.toString());
            org.openrdf.model.URI sesamResourceObject = new URIImpl(object.toString());
            
            //query all statement about the resource
            RepositoryResult<Statement> result = con.getStatements(null, sesamPredicate, sesamResourceObject, true);

            ArrayList<Statement> list = new ArrayList<>();
            //put statements into a list

            while (result.hasNext()) {
                list.add(result.next());
            }
            result.close();

            return list.toArray(new Statement[list.size()]);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get resource description for " + predicate.toString() + "and "+object+".", re);
        }


    }
    
    
    
    /**
     * Checks whether a statement for the given parameters exists in DB
     * @param subject
     * @param predicate
     * @param object
     * @return true if statement exist, otherwise false
     * @throws IllegalStateException
     * @throws RepositoryException 
     */
    public static boolean statementExists(URL subject, URL predicate, String object) throws IllegalStateException, RepositoryException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot check if statement exists " +subject.toString()+", "+ predicate.toString() + ", " + object.toString() + ", not connected.");
        }

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            org.openrdf.model.Resource sesamSubject = new URIImpl(subject.toString());
            org.openrdf.model.URI sesamPredicate = new URIImpl(predicate.toString());
            Literal sesamObject = new LiteralImpl(object);
            
            boolean hasStmt = con.hasStatement(sesamSubject, sesamPredicate, sesamObject, false);
            
            return hasStmt;
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get resource description for " + predicate.toString() + "and "+object+".", re);
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
            throw new IllegalStateException("Cannot get all subjects for type " + type + ", not connected.");
        }
        if (!Vocabulary.isType(type.toString())) {
            throw new IllegalArgumentException("Cannot get all subjects for type " + type + ", parameter is not a type.");
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
                throw new MalformedURLException("Cannot get all subjects for type " + type + ".");
            } finally {
                result.close();
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot get all subjects for type " + type + ", unknown connection error.",re);
        } catch(MalformedQueryException mqe){
            throw new RDFException("Cannot get all subjects for type " + type + ", unknown connection error.",mqe);
        } catch(QueryEvaluationException mqe){
            throw new RDFException("Cannot get all subjects for type " + type + ", unknown connection error.",mqe);
        }finally {
            con.close();
        }
    }
    
    
    

    /**
     * Determines whether a subject is in the DB by querying its type-statement
     * @param subject
     * @return True if a type-statement was found for this resource
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
     * Inserts an RDF statement into db
     *
     * @param triple
     * @throws Exception
     */
    public static void insertStatement(Statement stmt) throws IllegalStateException, RepositoryException, RDFException {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot insert Statement into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0]; 
        RepositoryConnection con = repo.getConnection();
        try {
            con.begin();
            con.add(stmt, resArr);
            con.commit();
        } catch (RepositoryException re) {
            con.rollback();
            throw new RepositoryException("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable. ",re);
        } catch (UnsupportedRDFormatException urfe) {
            con.rollback();
            throw new RDFException("Cannot load rdf/xml string into sesame RDF-DB. ",urfe);
        } finally {
            con.close();
        }

    }

   
}

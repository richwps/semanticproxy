/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.UnsupportedQueryLanguageException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 *
 * @author fbensman
 */
public class DBDelete {

    public static void deleteProcess(String processRoute) throws Exception{
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot delete process from sesame RDF-DB, not connected.");
        }
        
        Resource process = (Resource)new URIImpl(processRoute);
        URI hasProcess = new URIImpl(Vocabulary.Process);
        Resource[] res = new Resource[0];
        
        TupleQueryResult result = null;
        RepositoryConnection con = null;
        try {
//            String queryString = "PREFIX rwps:      <"+Vocabulary.VOC+">\n\n"
//                    + "SELECT ?o WHERE { <"+URIConfiguration.RESOURCES_URI+"/wps/wpsA> <"+Vocabulary.Process+"> ?o }";
//            con = repo.getConnection();
//
//            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString, URIConfiguration.RESOURCES_URI);
//            result = tupleQuery.evaluate();
//
//            try {
//                while (result.hasNext()) {
//                    BindingSet bindingSet = (BindingSet) result.next();
//                    Value valueOfS = bindingSet.getValue("o");
//                    System.out.println(valueOfS);
//                }
//            } catch (UnsupportedRDFormatException e) {
//                throw new Exception("Cannot delete process from sesame RDF-DB, " + e.getMessage());
//            }
            con = repo.getConnection();
            con.remove(null,hasProcess,(Value)process);
          
            URI isProvidedBy = new URIImpl(Vocabulary.WPS);
            con.remove(process,isProvidedBy, null);
            delete(process,con);
        } catch (RepositoryException e) {
            throw new Exception("Cannot get all subjects for type" + Vocabulary.ProcessClass + ", unknown connection error.");
        } catch (IllegalArgumentException e) {
            throw new Exception("Cannot get all subjects for type" + Vocabulary.ProcessClass + ", " + e.toString() + " " + e.getMessage());
        } catch (MalformedQueryException e) {
            throw new Exception("Cannot get all subjects for type" + Vocabulary.ProcessClass + ", " + e.toString() + " " + e.getMessage());
        } catch (UnsupportedQueryLanguageException e) {
            throw new Exception("Cannot get all subjects for type" + Vocabulary.ProcessClass + ", " + e.toString() + " " + e.getMessage());
        } catch (QueryEvaluationException e) {
            throw new Exception("Cannot get all subjects for type" + Vocabulary.ProcessClass + ", " + e.toString() + " " + e.getMessage());
        }  
         finally{
            con.close();
        }
   
    }
    
    
    private static void delete(Resource resource, RepositoryConnection con)throws Exception{
        
        //delete everything that points from process and its children
        RepositoryResult result = con.getStatements(resource, null, null, false);
        while(result.hasNext()){
            Statement st= (Statement)result.next();
            if(DBIO.isRDFConformURL(st.getObject().stringValue())){
                Resource res = (Resource)new URIImpl(st.getObject().stringValue());
                delete(res,con);
            } 
        }
        result.close();
        
        //delete everything that points to process and it children
//        result = con.getStatements(null, null, resource, false);
//        while(result.hasNext()){
//            Statement st= (Statement)result.next();
//            Resource res = (Resource)new URIImpl(st.getSubject().stringValue());
//            delete(res,con);
//        }
//        result.close();
        
        con.remove(resource,null,null);
    }
    
}

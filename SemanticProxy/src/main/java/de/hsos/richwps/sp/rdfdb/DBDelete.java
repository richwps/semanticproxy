/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import static de.hsos.richwps.sp.rdfdb.DBIO.isLiteral;
import de.hsos.richwps.sp.restlogic.URIConfiguration;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.util.ArrayList;
import java.util.Stack;
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
import org.openrdf.rio.UnsupportedRDFormatException;

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
            con.close();
            delete(process);
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
    
    
    private static void delete(Resource root)throws Exception{
        
        //connect to db
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot delete process from sesame RDF-DB, not connected.");
        }
        RepositoryConnection con = null;
        
        try{
            con = repo.getConnection();
        
            //create and init stack
            Stack<Statement> stack = new Stack<Statement>();
            RepositoryResult result = con.getStatements(root, null, null, false);
            //add children to stack
            while(result.hasNext()){
                stack.push((Statement)result.next());
            }
            result.close();

            //depth-first search
            while( !stack.isEmpty() ){
               //pop top
                Statement tmp =  stack.pop(); 

                //get children of tmp
                if( !DBIO.isLiteral(tmp.getObject().stringValue())){
                    Resource resource = (Resource)new URIImpl(tmp.getObject().stringValue());
                    result = con.getStatements(resource, null, null, false);

                    //add children to stack
                    while(result.hasNext()){
                        stack.push((Statement)result.next());
                    }
                    result.close();
                }

                //remove tmp
                con.remove(tmp);
            }
        }finally{
            con.close();
        }
    }

    
    
    
    
    public static void deleteWPS(String wpsRoute) throws Exception{
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot delete process from sesame RDF-DB, not connected.");
        }
        
        Resource wps = (Resource)new URIImpl(wpsRoute);
        RepositoryConnection con = null;
        
        try {
            con = repo.getConnection();
            URI subject = new URIImpl(URIConfiguration.RESOURCES_URI);
            URI predicate = new URIImpl(Vocabulary.WPS);
            URI object = new URIImpl(wpsRoute);
            con.remove(subject, predicate, object);
            con.close();
            
            delete(wps);
            
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
    
    
    public static void deleteWPSForUpdate(java.net.URI wps) throws Exception {
        
        //delete endpoint (literal)
        //delete various other statements
        Repository repo = SesameProperties.getInstance().getRepository();
        if (repo == null) {
            throw new Exception("Cannot insert triple into sesame RDF-DB, not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con =null;
        try {
            con = repo.getConnection();
            Resource subject = new URIImpl(wps.toString());
            RepositoryResult<Statement> result =  con.getStatements(subject, null, null,false,resArr);
            ArrayList<Statement> list = new ArrayList<Statement>();
            while(result.hasNext()){
                list.add(result.next());
            }
            result.close();
            con.close();
            for(int i=0;i<list.size(); i++){
                Statement st = list.get(i);
                String pred = st.getPredicate().stringValue();
                if( !pred.equals(Vocabulary.Process) && !pred.equals(Vocabulary.Type)){
                    //wenn object literal dann direkt weglöschen
                    if(isLiteral(st.getObject().stringValue())){
                        con = repo.getConnection();
                        con.remove(st);
                        con.close();
                    }
                    else{
                        Resource r = new URIImpl(st.getObject().stringValue());
                        delete(r);
                    }
                    
                }
            } 
        } catch (RepositoryException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, not connected or not writable.");
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot load rdf/xml string into sesame RDF-DB, " + e.getMessage());
        } finally{
            con.close(); 
        }
    }
    
    
    
}

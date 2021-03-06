/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 *
 * @author fbensman
 */
public class DBDelete {

    /**
     * Deletes a process recursively and disconnects it from its parent wps
     *
     * @param processURL The RDF resource identifier
     * @throws Exception When there is a problem with the db
     */
    public static void deleteProcess(URL processURL) throws RepositoryException, IllegalStateException, Exception  {
        //get db
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete process from sesame RDF-DB, not connected.");
        }

        Resource process = (Resource) new URIImpl(processURL.toString());
        URI hasProcess = new URIImpl(Vocabulary.Process);

        RepositoryConnection con = null;
        con = repo.getConnection();
        try {
            con.begin();
            //remove link from wps to process
            con.remove(null, hasProcess, (Value) process);

            URI isProvidedBy = new URIImpl(Vocabulary.WPS);
            //remove link from process to wps
            con.remove(process, isProvidedBy, null);
            con.commit();
            con.close();
        }catch(RepositoryException e){
            con.rollback();
            throw new RepositoryException("Cannot remove links between process " + processURL + " and its WPS");
        }
        try{
            //delete other resources recursively
            recursiveDelete(process);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete process " + process.stringValue() + " recursively.", re);
        } catch(IllegalStateException ise){
            throw new IllegalStateException("Cannot delete process " + process.stringValue() + " recursively.", ise);
        } catch(Exception e){
            throw new Exception("Cannot delete process " + process.stringValue() + " recursively.", e);
        }
        finally {
            con.close();
        }

    }

    /**
     * Deletes recursive resurces
     * @param root The resource to recursiveDelete
     * @throws Exception 
     */
    private static void recursiveDelete(Resource root) throws IllegalStateException, RepositoryException, Exception {

        //connect to db
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete resource from sesame RDF-DB, not connected.");
        }
        RepositoryConnection con = null;
        con = repo.getConnection();
        Stack<Statement> stack = null;
        try {
            //create and init stack
            stack = new Stack<Statement>();
            RepositoryResult result = con.getStatements(root, null, null, false);
            //add root's children to stack
            while (result.hasNext()) {
                stack.push((Statement) result.next());
            }
            result.close();
        }catch(RepositoryException re){
            throw new RepositoryException("Cannot add children of " + root.stringValue() + " to stack.",re);
        }catch(Exception e){
            throw new Exception("Cannot close result set of db query.",e);
        }
        
        Statement top = null;
        try{
            //depth-first search
            //iterate over stack
            con.begin();
            while (!stack.isEmpty()) {
                //pop top
                top = stack.pop();
                //get children of top
                if ( ! (top.getObject() instanceof Literal) ) {
                    Resource resource = (Resource) new URIImpl(top.getObject().stringValue());
                    RepositoryResult result = con.getStatements(resource, null, null, false);

                    //add children to stack
                    while (result.hasNext()) {
                        stack.push((Statement) result.next());
                    }
                    result.close();
                }

                //remove top
                con.remove(top);
            }
            con.commit();
        }catch(RepositoryException re){
            con.rollback();
            throw new RepositoryException("Cannot remove top node "+ top.toString() +" from db.",re);
        }catch(Exception e){
            con.rollback();
            throw new Exception("Cannot close result set of db query.",e);
        } finally {
            con.close();
        }
    }

    
    /**
     * Deletes a WPS and connected resources even processes
     * @param wpsURL RDF resource of the WPS
     * @throws Exception When sth. goes wrong with the db
     */
    public static void deleteWPS(URL wpsURL) throws RepositoryException, IllegalStateException, Exception  {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete process from sesame RDF-DB, not connected.");
        }

        Resource wps = (Resource) new URIImpl(wpsURL.toString());
        RepositoryConnection con = null;
        con = repo.getConnection();
        try {
            
            URI subject = new URIImpl(DBAdministration.getResourceURL().toString());
            URI predicate = new URIImpl(Vocabulary.WPS);
            URI object = new URIImpl(wpsURL.toString());
            //removes link from network to wps
            con.begin();
            con.remove(subject, predicate, object);
            con.commit();
            con.close();
        }catch(RepositoryException e){
            throw new RepositoryException("Cannot remove links between wps " + wpsURL + " and the network",e);
        }
        try{
            //removes the wps and connnected resources (also processes)
            recursiveDelete(wps);
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete wps " + wps.stringValue() + " recursively.", re);
        } catch(IllegalStateException ise){
            throw new IllegalStateException("Cannot delete wps " + wps.stringValue() + " recursively.", ise);
        } catch(Exception e){
            throw new Exception("Cannot delete wps " + wps.stringValue() + " recursively.", e);
        } finally {
            con.close();
        }

    }

    
    /**
     * Deletes a WPS for update, by keeping the processes and the type
     * @param wpsURL WPS to recursiveDelete
     * @throws Exception Error with db
     */
    public static void deleteWPS4Update(java.net.URL wpsURL) throws RepositoryException, IllegalStateException, Exception {
        //get the db
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete wps "+wpsURL.toString()+ "for update , not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        con = repo.getConnection();
        try {           
            Resource subject = new URIImpl(wpsURL.toString());
            //read all statements about wpsURL and put 'em into a list
            RepositoryResult<Statement> result = con.getStatements(subject, null, null, false, resArr);
            ArrayList<Statement> list = new ArrayList<Statement>();
            while (result.hasNext()) {
                list.add(result.next());
            }
            result.close();
            con.close();
            //walk through list...
            //...delete literals, delete resources recursively
            for (int i = 0; i < list.size(); i++) {
                Statement st = list.get(i);
                String pred = st.getPredicate().stringValue();
                
                if (!pred.equals(Vocabulary.Process) && !pred.equals(Vocabulary.Type)) {
                    //if object is literal then remove directly
                    if ( st.getObject() instanceof Literal ) { 
                        con = repo.getConnection();
                        con.begin();
                        con.remove(st);
                        con.commit();
                        con.close();
                    } else {
                        Resource r = new URIImpl(st.getObject().stringValue());
                        recursiveDelete(r);
                    }

                }
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete wps "+wpsURL.toString()+ "for update." ,re);
        } catch(IllegalStateException ise){
            throw new IllegalStateException("Cannot delete wps "+wpsURL.toString()+ "for update." ,ise);
        }catch(Exception e){
            throw new Exception("Cannot delete wps "+wpsURL.toString()+ "for update." ,e);
        } finally {
            con.close();
        }
    }
}

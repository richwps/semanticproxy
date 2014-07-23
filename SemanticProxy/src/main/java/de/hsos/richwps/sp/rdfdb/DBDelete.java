/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import static de.hsos.richwps.sp.rdfdb.DBIO.isLiteral;
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

    /**
     * Deletes a process recursively and disconnects it from its parent wps
     *
     * @param processRoute The RDF resource identifier
     * @throws Exception When there is a problem with the db
     */
    public static void deleteProcess(String processRoute) throws RepositoryException, IllegalStateException, Exception  {
        //get db
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete process from sesame RDF-DB, not connected.");
        }

        Resource process = (Resource) new URIImpl(processRoute);
        URI hasProcess = new URIImpl(Vocabulary.Process);

        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            //remove link from wps to process
            con.remove(null, hasProcess, (Value) process);

            URI isProvidedBy = new URIImpl(Vocabulary.WPS);
            //remove link from process to wps
            con.remove(process, isProvidedBy, null);
            con.close();
        }catch(RepositoryException e){
            throw new RepositoryException("Cannot remove links between process " + processRoute + " and its WPS");
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

        Stack<Statement> stack = null;
        try {
            con = repo.getConnection();

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
            while (!stack.isEmpty()) {
                //pop top
                top = stack.pop();
                //get children of top
                if (!DBIO.isLiteral(top.getObject().stringValue())) {
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
        }catch(RepositoryException re){
            throw new RepositoryException("Cannot remove top node "+ top.toString() +" from db.",re);
        }catch(Exception e){
            throw new Exception("Cannot close result set of db query.",e);
        } finally {
            con.close();
        }
    }

    
    /**
     * Deletes a WPS and connected resources even processes
     * @param wpsRoute RDF resource of the WPS
     * @throws Exception When sth. goes wrong with the db
     */
    public static void deleteWPS(String wpsRoute) throws RepositoryException, IllegalStateException, Exception  {
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete process from sesame RDF-DB, not connected.");
        }

        Resource wps = (Resource) new URIImpl(wpsRoute);
        RepositoryConnection con = null;

        try {
            con = repo.getConnection();
            URI subject = new URIImpl(DBAdministration.getResourceURL().toString());
            URI predicate = new URIImpl(Vocabulary.WPS);
            URI object = new URIImpl(wpsRoute);
            //removes link from network to wps
            con.remove(subject, predicate, object);
            con.close();
        }catch(RepositoryException e){
            throw new RepositoryException("Cannot remove links between wps " + wpsRoute + " and the network",e);
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
     * @param wps WPS to recursiveDelete
     * @throws Exception Error with db
     */
    public static void deleteWPSForUpdate(java.net.URI wps) throws RepositoryException, IllegalStateException, Exception {
        //get the db
        Repository repo = DBAdministration.getRepository();
        if (repo == null) {
            throw new IllegalStateException("Cannot delete wps "+wps.toString()+ "for update , not connected.");
        }
        Resource[] resArr = new Resource[0];
        RepositoryConnection con = null;
        try {
            con = repo.getConnection();
            Resource subject = new URIImpl(wps.toString());
            //read all statements about wps and put 'em into a list
            RepositoryResult<Statement> result = con.getStatements(subject, null, null, false, resArr);
            ArrayList<Statement> list = new ArrayList<Statement>();
            while (result.hasNext()) {
                list.add(result.next());
            }
            result.close();
            con.close();
            //delete literals, recursiveDelete resources recursively
            for (int i = 0; i < list.size(); i++) {
                Statement st = list.get(i);
                String pred = st.getPredicate().stringValue();
                if (!pred.equals(Vocabulary.Process) && !pred.equals(Vocabulary.Type)) {
                    //wenn object literal dann direkt wegloeschen
                    if (isLiteral(st.getObject().stringValue())) { //TODO: use instance of
                        con = repo.getConnection();
                        con.remove(st);
                        con.close();
                    } else {
                        Resource r = new URIImpl(st.getObject().stringValue());
                        recursiveDelete(r);
                    }

                }
            }
        } catch (RepositoryException re) {
            throw new RepositoryException("Cannot delete wps "+wps.toString()+ "for update." ,re);
        } catch(IllegalStateException ise){
            throw new IllegalStateException("Cannot delete wps "+wps.toString()+ "for update." ,ise);
        }catch(Exception e){
            throw new Exception("Cannot delete wps "+wps.toString()+ "for update." ,e);
        } finally {
            con.close();
        }
    }
}

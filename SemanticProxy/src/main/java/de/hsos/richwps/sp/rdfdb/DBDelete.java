/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import de.hsos.richwps.sp.restlogic.Vocabulary;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;
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
        try {//TODO: weitermachen
            RepositoryConnection con = repo.getConnection();
            Resource process = (Resource)new URIImpl(processRoute);
            URI hasProcess = new URIImpl(Vocabulary.Process);
            Resource[] res = new Resource[0];
            con.remove(null,hasProcess,process,res);
            URI isProvidedBy = new URIImpl(Vocabulary.WPS);
            con.remove(process,isProvidedBy, null);
            delete(process,con);
        } catch (RepositoryException e) {
            throw new Exception("Cannot delete process from sesame RDF-DB, not connected or not writable.");
        } catch (UnsupportedRDFormatException e) {
            throw new Exception("Cannot delete process from sesame RDF-DB, " + e.getMessage());
        }
    }
    
    private static void delete(Resource resource, RepositoryConnection con)throws Exception{
        RepositoryResult result = con.getStatements(resource, null, null, true);
        while(result.hasNext()){
            Statement st= (Statement)result.next();
            if(DBIO.isRDFConformURL(st.getObject().stringValue())){
                Resource res = (Resource)new URIImpl(st.getObject().stringValue());
                delete(res,con);
            } 
        }
        con.remove(resource,null,null);
    }
    
}

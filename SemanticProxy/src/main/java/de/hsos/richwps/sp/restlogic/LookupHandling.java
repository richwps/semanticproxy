/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import de.hsos.richwps.sp.types.RankedProcess;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class LookupHandling {

    /**
     * Finds a processIdentifier resource identifier for a given WPS endpoint
     * and a processIdentifier identifier
     *
     * @param keyword
     * @return List with the processIdentifier RDF ID - one
     * @throws Exception
     */
    public static SubjectList processLookup(URL wpsEndpoint, String processIdentifier) throws MalformedURLException, RepositoryException, RDFException {
        try {

            //-- algorithm --
            //
            //find all *-type-wpsclass statements in DB and store in list
            //for every found statement in list 
            //  if a statement with wpsid as subject, endpoint as predicate and the given wpsEndpoint can be found in DB
            //    get subject from statement -> correct wpsid found
            //if no subject was found
            //  return empty result -> wpsid is not present in DB
            //
            //find all wpssubject-process-* statements in DB and store in list
            //for every found statement in list
            //  if a statement with processid as subject, identifier as predicate and the given processIdentifier can be found in DB
            //    get subject from statement -> correct processIdentifier id found
            //return empty result -> processIdentifier id is not present in DB


            //find all statements with type-predicate and wpsclass-object
            Statement[] wpsArr = DBIO.getStatementsForPredAndResourceObj(new URL(Vocabulary.Type), new URL(Vocabulary.WPSClass));

            //in list find statement mit endpoint == wpsEndpoint parameter
            Resource wpsSubject = null;
            for (int i = 0; i < wpsArr.length; i++) {
                Statement stmt = wpsArr[i];
                URL curWPSSubject = new URL(stmt.getSubject().stringValue());
                URL endpointPredicate = new URL(Vocabulary.Endpoint);
                Statement[] endpointStmtArr = DBIO.getStatementsForSubjAndPred(curWPSSubject, endpointPredicate);

                if (endpointStmtArr[0].getObject().stringValue().equals(wpsEndpoint.toString())) {
                    wpsSubject = endpointStmtArr[0].getSubject();
                    break;
                }
            }
            //break nesting
            //return empty list if no wpsEndpoint with the given endpoint was found
            if (wpsSubject == null) {
                return new SubjectList();
            }

            //find all statements for the found wps id as subject, the process predicate and the processIdentifier id
            Statement[] processStmts = DBIO.getStatementsForSubjAndPred(new URL(wpsSubject.stringValue()), new URL(Vocabulary.Process));

            //in results find statements with identifier == processIdentifier parameter
            Resource processSubject = null;
            for (int i = 0; i < processStmts.length; i++) {
                Statement stmt = processStmts[i];
                URL curProcessSubject = new URL(stmt.getObject().stringValue());
                URL identifierPredicate = new URL(Vocabulary.Identifier);
                //if a statement with processid as subject, identifier as predicate and the given processIdentifier can be found in DB
                if (DBIO.statementExists(curProcessSubject, identifierPredicate, processIdentifier)) {
                    SubjectList subjectList = new SubjectList();
                    subjectList.add(curProcessSubject);
                    return subjectList;
                }
            }//return empty list if no processIdentifier with the given identifier was found
            return new SubjectList();


        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot search for keyword.", ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot search for keyword.", ex);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import java.util.ArrayList;
import org.openrdf.model.Statement;

/**
 * Utils for validation
 * @author fbensman
 */
public class ValidationUtils {
    
    /**
     * Checks wether an object is in a list of statements
     *
     * @param subject RDF resource identifier of object
     * @param list The list to look through
     * @return True if list contains the object
     */
    public static boolean containsObject(String object, ArrayList<Statement> list) {
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getObject().stringValue().equals(object)) {
                return true;
            }
        }
        return false;
    }
    
    
     /**
     * Checks wether a predicate is in a list of statements
     *
     * @param subject RDF resource identifier of predicate
     * @param list The list to look through
     * @return True if list contains the predicate
     */
    private static boolean containsPredicate(String predicate, ArrayList<Statement> list) {
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getPredicate().stringValue().equals(predicate)) {
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * Checks wether a subject is in a list of statements
     *
     * @param subject RDF resource identifier of subject
     * @param list The list to look through
     * @return True if list contains the subject
     */
    private static boolean containsSubject(String subject, ArrayList<Statement> list) {
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getSubject().stringValue().equals(subject)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    /**
     * Returns statements with matching subject
     * @param subject
     * @param list
     * @return 
     */
    public static Statement[] getStatementsBySubject(String subject, ArrayList<Statement> list) {
        ArrayList<Statement> retList = new ArrayList<Statement>();
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getSubject().stringValue().equals(subject)) {
                retList.add(st);
            }
        }
        return retList.toArray(new Statement[retList.size()]);
    }
    
    
    
    /**
     * Returns statements with matching object
     * @param object
     * @param list
     * @return 
     */
    public static Statement[] getStatementsByObject(String object, ArrayList<Statement> list) {
        ArrayList<Statement> retList = new ArrayList<Statement>();
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getObject().stringValue().equals(object)) {
                retList.add(st);
            }
        }
        return retList.toArray(new Statement[retList.size()]);
    }
    
    
    /**
     * Returns statements with matching predicate
     * @param predicate
     * @param list
     * @return 
     */
    public static Statement[] getStatementsByPredicate(String predicate, ArrayList<Statement> list) {
        ArrayList<Statement> retList = new ArrayList<Statement>();
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getPredicate().stringValue().equals(predicate)) {
                retList.add(st);
            }
        }
        return retList.toArray(new Statement[retList.size()]);
    }
    
    
    
    /**
     * Returns statements with matching predicate and object
     *
     * @param predicate
     * @param object
     * @param list
     * @return
     */
    public static Statement[] getStatementsByPredicateAndObject(String predicate, String object, ArrayList<Statement> list) {
        ArrayList<Statement> retList = new ArrayList<Statement>();
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getObject().stringValue().equals(object) && st.getPredicate().stringValue().equals(predicate)) {
                retList.add(st);
            }
        }
        return retList.toArray(new Statement[retList.size()]);
    }

    
    /**
     * Returns statements with matching subject and predicate
     * @param subject
     * @param predicate
     * @param list
     * @return 
     */
    public static Statement[] getStatementsBySubjectAndPredicate(String subject, String predicate, ArrayList<Statement> list) {
        ArrayList<Statement> retList = new ArrayList<Statement>();
        for (int i = 0; i < list.size(); i++) {
            Statement st = list.get(i);
            if (st.getSubject().stringValue().equals(subject) && st.getPredicate().stringValue().equals(predicate)) {
                retList.add(st);
            }
        }
        return retList.toArray(new Statement[retList.size()]);
    }
    
    
}

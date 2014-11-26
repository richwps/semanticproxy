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
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.Statement;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class SearchHandling {

    /**
     * Executes a keyword search for processes. Searches for exact match of
     * keyword in identifier, title and abstract attributes
     *
     * @param keyword
     * @return List of process RDF IDs ordert descending by count of hits
     * @throws Exception
     */
    public static SubjectList processKeywordSearch(String[] keywords) throws MalformedURLException, RepositoryException, RDFException, Exception {
        try {
            final URL processClass = new URL(Vocabulary.ProcessClass);

            SubjectList completeList = DBIO.getAllSubjectsForType(processClass);
            ArrayList<RankedProcess> resultList = new ArrayList<RankedProcess>();
            for (int i = 0; i < completeList.size(); i++) {

                boolean found = false;
                int match = 0;
                for (String keyword : keywords) {

                    int m = countOccurenceInProcess(completeList.get(i), keyword);

                    if (m > 0) {
                        found = true;
                        match += m;
                    } else {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    RankedProcess rPro = new RankedProcess(completeList.get(i), match);
                    resultList.add(rPro);
                }
            }
            Collections.sort(resultList);
            SubjectList retList = new SubjectList();
            for (int i = 0; i < resultList.size(); i++) {
                retList.add(resultList.get(i).process);
            }
            return retList;
        } catch (IllegalStateException ex) {
            throw new IllegalStateException("Cannot search for keyword.", ex);
        } catch (RepositoryException ex) {
            throw new RepositoryException("Cannot search for keyword.", ex);
        } catch (RDFException ex) {
            throw new RDFException("Cannot search for keyword.", ex);
        }
    }

    /**
     * Verifies whether a string contains only alphanumerical characters.
     *
     * @param word
     * @return
     */
    private static boolean isAlphaNum(String word) {
        return word.matches("[A-Za-z0-9\\s]+");
    }

    /**
     * Counts the occurences of findStr in str
     *
     * @param str string to examine
     * @param findStr string to find
     * @return number of occurences
     */
    private static int countOccurence(String str, String findStr) {
        int lastIndex = 0;
        int count = 0;

        String s = str.toLowerCase();
        String f = findStr.toLowerCase();

        while (lastIndex != -1) {

            lastIndex = s.indexOf(f, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += f.length();
            }
        }
        return count;
    }

    /**
     * Retrieves identifier, title and abstract of a process and countes
     * the occurence of the keyword in it
     *
     * @param proc
     * @param keyword
     * @return
     * @throws MalformedURLException
     * @throws RepositoryException
     * @throws RDFException
     * @throws Exception
     */
    private static int countOccurenceInProcess(URL proc, String keyword) throws MalformedURLException, RepositoryException, RDFException, Exception {

        final URL processIdentifier = new URL(Vocabulary.Identifier);
        final URL processTitle = new URL(Vocabulary.Title);
        final URL processAbstract = new URL(Vocabulary.Abstract);

        int match = 0;

        //search in identifier
        Statement[] stats = DBIO.getStatementsForSubjAndPred(proc, processIdentifier);
        if (stats.length != 1) {
            throw new Exception("Malformed resource " + proc.toString());
        }
        String str = stats[0].getObject().stringValue();
        match += countOccurence(str, keyword);

        //search in title
        stats = DBIO.getStatementsForSubjAndPred(proc, processTitle);
        if (stats.length != 1) {
            throw new Exception("Malformed resource " + proc.toString());
        }
        str = stats[0].getObject().stringValue();
        match += countOccurence(str, keyword);

        //search in abstract
        stats = DBIO.getStatementsForSubjAndPred(proc, processAbstract);
        if (stats.length > 1) {
            throw new Exception("Malformed resource " + proc.toString());
        } else if (stats.length == 1) {
            str = stats[0].getObject().stringValue();
            match += countOccurence(str, keyword);
        }
        return match;
    }
}

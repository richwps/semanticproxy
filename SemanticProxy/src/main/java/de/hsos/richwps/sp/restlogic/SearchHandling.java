/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.RankedProcess;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import org.openrdf.model.Statement;

/**
 *
 * @author fbensman
 */
public class SearchHandling {
    
    
    
    
    
    public static SubjectList processKeywordSearch(String keyword) throws Exception{
        if( !isAlphaNum(keyword)){
            throw new Exception("Keyword not alphanummeric.");
        }
        
        URL processClass = null;
        URL processIdentifier = null;
        URL processTitle = null;
        URL processAbstract = null;
        try{
            processClass = new URL(Vocabulary.ProcessClass);
            processIdentifier = new URL(Vocabulary.Identifier);
            processTitle = new URL(Vocabulary.Title);
            processAbstract = new URL (Vocabulary.Abstract);
        }catch(MalformedURLException e){
            throw new Exception("Malformed vocabulary");
        }
        
        SubjectList completeList =  DBIO.getAllSubjectsForType(processClass);
        ArrayList<RankedProcess> resultList = new ArrayList<RankedProcess>();
        for(int i=0; i<completeList.size(); i++){
            int match = 0;
            
            //search in identifier
            Statement[] stats = DBIO.getStatementsForSubjAndPred(completeList.get(i), processIdentifier);
            if(stats.length != 1){
                throw new Exception("Malformed resource "+completeList.get(i).toString());
            }
            String str = stats[0].getObject().stringValue(); 
            if(str.toLowerCase().contains(keyword.toLowerCase())){
                match++;
            }
            
            //search in title
            stats = DBIO.getStatementsForSubjAndPred(completeList.get(i), processTitle);
            if(stats.length != 1){
                throw new Exception("Malformed resource "+completeList.get(i).toString());
            }
            str = stats[0].getObject().stringValue();
            if(str.toLowerCase().contains(keyword.toLowerCase())){
                match++;
            }
            
            //search in abstract
            stats = DBIO.getStatementsForSubjAndPred(completeList.get(i), processAbstract);
            if(stats.length != 1){
                throw new Exception("Malformed resource "+completeList.get(i).toString());
            }
            str = stats[0].getObject().stringValue();
            if(str.toLowerCase().contains(keyword.toLowerCase())){
                match++;
            }
            if(match >0){
                RankedProcess rPro = new RankedProcess(completeList.get(i), match);
                resultList.add(rPro);
            }
            
        }
        Collections.sort(resultList);
        SubjectList retList = new SubjectList();
        for(int i=0; i<resultList.size();i++){
            retList.add(resultList.get(i).process);
        }
        return retList;
    }
    
    
    private static boolean isAlphaNum(String word){
        return word.matches("[A-Za-z0-9]+");
    }
    
}

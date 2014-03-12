/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URI;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class SubjectList extends ArrayList<URI>{
    
    public String toXMLList(){
        String tab = "    ";
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        str+="\n";
        str+="\n<subjectlist>";
        for(int i=0; i<size();i++){
            str+="\n"+tab+"<subject>"+get(i)+"</subject>";
        }
        str+="\n</subjectlist>\n";
        return str;
    }
}

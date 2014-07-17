/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class SubjectList extends ArrayList<URL> {

    /**
     * Creates a SubjectList from xml code
     *
     * @param xml
     * @return
     * @throws MalformedURLException
     */
    public static SubjectList fromXML(String xml) throws MalformedURLException {
        int lastIdx = 0;
        SubjectList list = new SubjectList();
        while (xml.contains("<subject>")) {
            int sIdx = xml.indexOf("<subject>", lastIdx);
            int eIdx = xml.indexOf("</subject>", lastIdx);
            String urlStr = xml.substring(sIdx + "<subject>".length(), eIdx);
            lastIdx = eIdx + "</subject>".length();
            URL url = new URL(urlStr);
            list.add(url);
        }
        return list;
    }

    /**
     * Returns an XML representation of the object
     *
     * @return
     */
    public String toXMLList() {
        String tab = "    ";
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        str += "\n";
        str += "\n<subjectlist>";
        for (int i = 0; i < size(); i++) {
            str += "\n" + tab + "<subject>" + get(i) + "</subject>";
        }
        str += "\n</subjectlist>\n";
        return str;
    }
}

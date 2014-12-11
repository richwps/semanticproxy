/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fbensman
 */
public class LinkHeader {

    private Map<String, String> map = null;

    private LinkHeader() {
    }

    /**
     * Creates a LinkHeader from a raw string. Raw migth look like this: Link:
     * <http://localhost:4567/semanticproxy>;
     * rel="application",<http://localhost:4567/semanticproxy/resources>;
     * rel="resources",<http://localhost:4567/semanticproxy/resources/vocab>;
     * rel="vocabulary",<http://localhost:4567/semanticproxy/resources/processes>;
     * rel="processlist",<http://localhost:4567/semanticproxy/resources/wpss>;
     * rel="wpslist",<http://localhost:4567/semanticproxy/resources/wfss>;
     * rel="wfslist",<http://localhost:4567/semanticproxy/idgenerator>;
     * rel="idgenerator"
     *
     * @param raw
     * @return
     */
    public static LinkHeader createFromRawString(String raw) throws IllegalArgumentException{
        try {
            Map<String, String> map = new HashMap<String, String>();

            String[] links = raw.split(",");
            for (String link : links) {
                String[] arr = link.split(";");
                String url = arr[0].trim();
                String rel = arr[1].trim();
                url = url.replaceAll("[<>]", "");
                rel = rel.replaceAll("rel=", "");
                rel = rel.replaceAll("\"", "");
                map.put(rel, url);
            }
            LinkHeader lh = new LinkHeader();
            lh.setMap(map);
            return lh;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when reading raw link header",e);
        } 
    }

    private void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String get(String rel) {
        return map.get(rel);
    }
}

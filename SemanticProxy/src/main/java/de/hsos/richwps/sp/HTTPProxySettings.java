/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import java.net.URL;

/**
 * Stores host and port information for a http proxy
 * @author fbensman
 */
public class HTTPProxySettings {
    
    private String host = null;
    private int port = -1;
    
    
    public HTTPProxySettings(String host, int port){
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
    
    
    @Override
    public String toString(){
        return "host="+host+", port="+port; 
    }
    
    
}

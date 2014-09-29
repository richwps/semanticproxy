/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports;

import java.net.URL;

/**
 * Interface to adapt to data sources for importing RDF descriptions
 * @author fbensman
 */
public interface IWPSImportSource {
    
    /**
     * Gets the RDF description for the next available WPS
     * @return RDF description of the WPS; null if there is no more WPS
     * @throws ImportException  
     */
    public String getNextWPS() throws ImportException;
    
    /**
     * Gets the RDF description for the next available process
     * @return RDF description of the process; null if there is no more process
     * @throws ImportException  
     */
    public String getNextProcess() throws ImportException;
    
    /**
     * Resets streams
     */
    public void reset();
    
    
    /**
     * Describes the data source
     * @return Info about data source
     */
    public String getInfo();
    
    
}

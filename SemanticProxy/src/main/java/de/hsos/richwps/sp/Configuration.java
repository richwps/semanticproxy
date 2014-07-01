/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.rwps.semanticproxy.config.ConfigurationDocument;
import de.hsos.rwps.semanticproxy.config.HTTPEndpoints;
import de.hsos.rwps.semanticproxy.config.PreloadFiles;
import de.hsos.rwps.semanticproxy.config.RDFNamingEndpoints;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
//import de.hsos.rwps.semanticproxy.config.*;
import java.io.FileWriter;

/**
 *
 * @author fbensman
 */
public class Configuration {
    
    private File rdfMemoryDir = null;
    private boolean startClean = true;
    private ArrayList<File> preloadRDF = null;
   
    //http endpoints
    private URL hostURL = null;
    private URL resourcesURL = null;
    private URL applicationURL = null;
    private URL vocabularyURL = null;
    private URL networkURL = null;
    private URL wpsListURL = null;
    private URL processListURL = null;
    
    //naming endpoints
    private URL wpsNamingEndpoint = null;
    private URL processNamingEndpoint = null;
    private URL inputNamingEndpoint  = null;
    private URL outputNamingEndpoint = null;
    
    
    
    
    private static Configuration instance = null;
    
    public Configuration (){
        preloadRDF = new ArrayList<File>();
    }
    
    
    
    public static Configuration getInstance(){
        if(instance == null)
            instance = new Configuration();
        return instance;
    }
    
    
    public boolean loadDefault() throws Exception{
        preloadRDF = new ArrayList<File>();
        File networkRDF = new File("." + File.separator + "RDF" + File.separator +"rwpsnetwork.rdf");
        File wpsRDF = new File("." + File.separator + "RDF" + File.separator + "rwpswps.rdf");
        File processRDF = new File("." + File.separator + "RDF" + File.separator + "rwpsprocess.rdf");
        preloadRDF.add(networkRDF);
        preloadRDF.add(wpsRDF);
        preloadRDF.add(processRDF);
        rdfMemoryDir = new File(".");
        startClean = true;


        //http endpoints
        hostURL = new URL("http://localhost:4567");
        applicationURL = new URL(hostURL.toString()+"/semanticproxy");
        resourcesURL = new URL(applicationURL.toString()+"/resources");
        vocabularyURL = new URL(resourcesURL.toString()+"/vocab");
        networkURL = resourcesURL;
        wpsListURL = new URL(resourcesURL.toString()+"/wpss");
        processListURL = new URL(resourcesURL.toString()+"/processes");


        //naming endpoints
        wpsNamingEndpoint = new URL(resourcesURL.toString()+"/wps");
        processNamingEndpoint = new URL(resourcesURL.toString()+"/process");
        inputNamingEndpoint = new URL(resourcesURL.toString()+"/input");
        outputNamingEndpoint = new URL(resourcesURL.toString()+"/output");
        
        return true;
    }
    
    
    public boolean load(File file) throws Exception{
        
        ConfigurationDocument configDoc = null;
        try{
            configDoc = ConfigurationDocument.Factory.parse(file);
        }catch(Exception e){
            return false;
        }
            
        ConfigurationDocument.Configuration config = configDoc.getConfiguration();
        String tmpRDFDir = config.getRDFDirectory();
        boolean tmpStartClean = config.getStartClean();
        PreloadFiles tmpFileList =  config.getPreloadFiles();
        HTTPEndpoints  tmpHTTPEndpoints = config.getHTTPEndpoints();
        RDFNamingEndpoints tmpRDFNamingEndpoints = config.getRDFNamingEndpoints();
        
        
      
        rdfMemoryDir = new File(tmpRDFDir);
        startClean = tmpStartClean;
        for(int i=0; i< tmpFileList.sizeOfFileArray();i++){
            preloadRDF.add(new File(tmpFileList.getFileArray(i)));
        }

        //HTTP endpoints
        hostURL = new URL(tmpHTTPEndpoints.getHost());
        applicationURL = new URL(hostURL.toString()+"/"+tmpHTTPEndpoints.getApplication());
        resourcesURL = new URL(applicationURL.toString()+"/"+tmpHTTPEndpoints.getResources());
        vocabularyURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getVocabulary());
        networkURL = resourcesURL;
        wpsListURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getWPSList());
        processListURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getProcessList());

        //naming endpoints
        wpsNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getWPSNaming());
        processNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getProcessNaming());
        inputNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getInputNaming());
        outputNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getOutputNaming());

        
        return true;
    }

    
    public void writeDefaultConfiguration() throws Exception{
        loadDefault();
        ConfigurationDocument doc = ConfigurationDocument.Factory.newInstance();
        ConfigurationDocument.Configuration config = doc.addNewConfiguration();
        config.setRDFDirectory(".");
        config.setStartClean(startClean);
        PreloadFiles tmpPreloadFiles =  config.addNewPreloadFiles();
        for(int i=0; i< preloadRDF.size(); i++){
            tmpPreloadFiles.addFile("."+File.separator+"RDF"+File.separator+preloadRDF.get(i).getName());
        }
        HTTPEndpoints tmpHttpEndpoints = config.addNewHTTPEndpoints();
        tmpHttpEndpoints.setHost(hostURL.toString());
        tmpHttpEndpoints.setApplication(diffSegments(hostURL.toString(), applicationURL.toString()));
        tmpHttpEndpoints.setResources(diffSegments(applicationURL.toString(),resourcesURL.toString()));
        tmpHttpEndpoints.setVocabulary(diffSegments(resourcesURL.toString(), vocabularyURL.toString()));
        tmpHttpEndpoints.setWPSList(diffSegments(resourcesURL.toString(),wpsListURL.toString()));
        tmpHttpEndpoints.setProcessList(diffSegments(resourcesURL.toString(),processListURL.toString()));
        
        RDFNamingEndpoints tmpRDFNamingEndpoints = config.addNewRDFNamingEndpoints();
        tmpRDFNamingEndpoints.setInputNaming(diffSegments(resourcesURL.toString(),inputNamingEndpoint.toString()));
        tmpRDFNamingEndpoints.setOutputNaming(diffSegments(resourcesURL.toString(),outputNamingEndpoint.toString()));
        tmpRDFNamingEndpoints.setWPSNaming(diffSegments(resourcesURL.toString(),wpsNamingEndpoint.toString()));
        tmpRDFNamingEndpoints.setProcessNaming(diffSegments(resourcesURL.toString(),processNamingEndpoint.toString()));
        
        try (FileWriter writer = new FileWriter("./config.xml")) {
            writer.write(doc.toString());
        }
    }

  


    
    
    /**
     * Compares tow URL strings and returns the trailing segments that the second url has in addition
     * @param urlShort
     * @param urlLong
     * @return 
     */
    private static String diffSegments(String urlShort, String urlLong){
        if(urlLong.startsWith(urlShort)){
            String tmp = urlLong.replace(urlShort, "");
            if(tmp.length()>=1){
                if(tmp.startsWith("/")){
                    tmp = tmp.substring(1);
                }
                return tmp;
            }
            else {
                return null;
            }
        }
        else{
            return null;
        }
    }
    
    public URL getHostURL() {
        return hostURL;
    }

    public URL getResourcesURL() {
        return resourcesURL;
    }

    public ArrayList<File> getPreloadRDF() {
        return preloadRDF;
    }

    public File getRdfMemoryDir() {
        return rdfMemoryDir;
    }

    public boolean isStartClean() {
        return startClean;
    }

   

    public URL getWpsNamingURL() {
        return wpsNamingEndpoint;
    }

    public URL getProcessNamingURL() {
        return processNamingEndpoint;
    }

    public URL getInputNamingEndpoint() {
        return inputNamingEndpoint;
    }

    public URL getOutputNamingEndpoint() {
        return outputNamingEndpoint;
    }

    public URL getApplicationURL() {
        return applicationURL;
    }

    public URL getVocabularyURL() {
        return vocabularyURL;
    }

    public URL getNetworkURL() {
        return networkURL;
    }

    public URL getWpsListURL() {
        return wpsListURL;
    }

    public URL getProcessListURL() {
        return processListURL;
    }

  
    
    
    
    
    
    
    
}

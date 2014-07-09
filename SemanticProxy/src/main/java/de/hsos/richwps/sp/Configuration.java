/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;


import de.hsos.richwps.sp.config.ConfigurationDocument;
import de.hsos.richwps.sp.config.HTTPEndpoints;
import de.hsos.richwps.sp.config.PreloadFiles;
import de.hsos.richwps.sp.config.RDFNamingEndpoints;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.io.FileWriter;

/**
 * Loads and stores a configuration
 * @author fbensman
 */
public class Configuration {
    
    private File configSource = null;
    private boolean loaded = false;
    
    private File rdfMemoryDir = null;
    private boolean startClean = true;
    private String domain = null;
    private String owner = null;
    private ArrayList<File> processRDFFiles = null;
    private ArrayList<File> wpsRDFFiles = null;
   
    //http endpoints
    private URL hostURL = null;
    private URL resourcesURL = null;
    private URL applicationURL = null;
    private URL vocabularyURL = null;
    private URL networkURL = null;
    private URL wpsListURL = null;
    private URL processListURL = null;
    private URL searchURL = null;
    
    //naming endpoints
    private URL wpsNamingEndpoint = null;
    private URL processNamingEndpoint = null;
    private URL inputNamingEndpoint  = null;
    private URL outputNamingEndpoint = null;
    
    
    
    
    
    
    public Configuration (){
        wpsRDFFiles = new ArrayList<>();
        processRDFFiles = new ArrayList<>();
    }
    
    
    

    /**
     * Initializes itself with a default configuration
     * @return
     * @throws Exception 
     */
    public boolean loadDefault() throws Exception{
        configSource = null;
        loaded = true;
        wpsRDFFiles = new ArrayList<>();
        processRDFFiles = new ArrayList<>();
        File wpsFileLKN1 = new File("RDF" + File.separator + "LKN"+File.separator+"WPSMacrophyte.rdf");
        File proFileLKN1 = new File("RDF" + File.separator + "LKN"+File.separator+"ProcessSelectReportingArea.rdf");
        File proFileLKN2 = new File("RDF" + File.separator + "LKN"+File.separator+"ProcessSelectMSRLD5.rdf");
        File proFileLKN3 = new File("RDF" + File.separator + "LKN"+File.separator+"ProcessIntersect.rdf");
        File proFileLKN4 = new File("RDF" + File.separator + "LKN"+File.separator+"ProcessSelectTopography.rdf");
        File proFileLKN5 = new File("RDF" + File.separator + "LKN"+File.separator+"ProcessCharacteristics.rdf");
        
        File wpsFileBAW1 = new File("RDF" + File.separator + "BAW"+File.separator+"WPSModelValidation.rdf");
        File proFileBAW1 = new File("RDF" + File.separator + "BAW"+File.separator+"ProcessReadData.rdf");
        File proFileBAW2 = new File("RDF" + File.separator + "BAW"+File.separator+"ProcessHarmonize.rdf");
        File proFileBAW3 = new File("RDF" + File.separator + "BAW"+File.separator+"ProcessCompare.rdf");
        File proFileBAW4 = new File("RDF" + File.separator + "BAW"+File.separator+"ProcessFormat.rdf");
        
        wpsRDFFiles.add(wpsFileLKN1);
        wpsRDFFiles.add(wpsFileBAW1);
        processRDFFiles.add(proFileLKN1);
        processRDFFiles.add(proFileLKN2);
        processRDFFiles.add(proFileLKN3);
        processRDFFiles.add(proFileLKN4);
        processRDFFiles.add(proFileLKN5);
        processRDFFiles.add(proFileBAW1);
        processRDFFiles.add(proFileBAW2);
        processRDFFiles.add(proFileBAW3);
        processRDFFiles.add(proFileBAW4);
        
        
        rdfMemoryDir = new File(".");
        startClean = true;
        owner = "HS-OS";
        domain = "RichWPS test domain";

        //http endpoints
        hostURL = new URL("http://localhost:4567");
        applicationURL = new URL(hostURL.toString()+"/semanticproxy");
        resourcesURL = new URL(applicationURL.toString()+"/resources");
        vocabularyURL = new URL(resourcesURL.toString()+"/vocab");
        networkURL = resourcesURL;
        wpsListURL = new URL(resourcesURL.toString()+"/wpss");
        processListURL = new URL(resourcesURL.toString()+"/processes");
        searchURL = new URL(applicationURL.toString()+"/search");

        //naming endpoints
        wpsNamingEndpoint = new URL(resourcesURL.toString()+"/wps");
        processNamingEndpoint = new URL(resourcesURL.toString()+"/process");
        inputNamingEndpoint = new URL(resourcesURL.toString()+"/input");
        outputNamingEndpoint = new URL(resourcesURL.toString()+"/output");
        
        return true;
    }
    
    
    /**
     * Loads configuration from the specified file.
     * @param file
     * @return
     * @throws Exception 
     */
    public boolean load(File file) throws Exception{
        configSource = file;
        
        ConfigurationDocument configDoc = null;
        try{
            configDoc = ConfigurationDocument.Factory.parse(file);
        }catch(Exception e){
            return false;
        }
            
        ConfigurationDocument.Configuration config = configDoc.getConfiguration();
        String tmpRDFDir = config.getRDFDirectory();
        boolean tmpStartClean = config.getStartClean();
        String tmpOwner = config.getOwner();
        String tmpDomain = config.getDomain();
        PreloadFiles tmpFileList =  config.getPreloadFiles();
        HTTPEndpoints  tmpHTTPEndpoints = config.getHTTPEndpoints();
        RDFNamingEndpoints tmpRDFNamingEndpoints = config.getRDFNamingEndpoints();
        
        
      
        rdfMemoryDir = new File(tmpRDFDir);
        startClean = tmpStartClean;
        owner = tmpOwner;
        domain = tmpDomain;
        
        wpsRDFFiles.clear();
        for(int i=0; i<tmpFileList.sizeOfWPSArray();i++){
            wpsRDFFiles.add(new File(tmpFileList.getWPSArray(i)));
        }
        
        processRDFFiles.clear();
        for(int i=0; i<tmpFileList.sizeOfProcessArray();i++){
            processRDFFiles.add(new File(tmpFileList.getProcessArray(i)));
        }
        

        //HTTP endpoints
        hostURL = new URL(tmpHTTPEndpoints.getHost());
        applicationURL = new URL(hostURL.toString()+"/"+tmpHTTPEndpoints.getApplication());
        resourcesURL = new URL(applicationURL.toString()+"/"+tmpHTTPEndpoints.getResources());
        vocabularyURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getVocabulary());
        networkURL = resourcesURL;
        wpsListURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getWPSList());
        processListURL = new URL(resourcesURL.toString()+"/"+tmpHTTPEndpoints.getProcessList());
        searchURL = new URL(applicationURL.toString()+"/"+tmpHTTPEndpoints.getSearch());

        //naming endpoints
        wpsNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getWPSNaming());
        processNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getProcessNaming());
        inputNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getInputNaming());
        outputNamingEndpoint = new URL(resourcesURL.toString()+"/"+tmpRDFNamingEndpoints.getOutputNaming());
        loaded = true;
        return true;
    }

    
    /**
     * Writes a default configuration to cwd
     * @throws Exception 
     */
    public void writeDefaultConfiguration() throws Exception{
        loadDefault();
        ConfigurationDocument doc = ConfigurationDocument.Factory.newInstance();
        ConfigurationDocument.Configuration config = doc.addNewConfiguration();
        
        config.setRDFDirectory(".");
        config.setStartClean(startClean);
        config.setOwner(owner);
        config.setDomain(domain);
        PreloadFiles tmpPreloadFiles =  config.addNewPreloadFiles();
        for(int i=0; i<wpsRDFFiles.size();i++){
            tmpPreloadFiles.addWPS(wpsRDFFiles.get(i).getAbsolutePath());
        }
        for(int i=0; i<processRDFFiles.size();i++){
            tmpPreloadFiles.addProcess(processRDFFiles.get(i).getAbsolutePath());
        }
        
        HTTPEndpoints tmpHttpEndpoints = config.addNewHTTPEndpoints();
        tmpHttpEndpoints.setHost(hostURL.toString());
        tmpHttpEndpoints.setApplication(diffSegments(hostURL.toString(), applicationURL.toString()));
        tmpHttpEndpoints.setResources(diffSegments(applicationURL.toString(),resourcesURL.toString()));
        tmpHttpEndpoints.setVocabulary(diffSegments(resourcesURL.toString(), vocabularyURL.toString()));
        tmpHttpEndpoints.setWPSList(diffSegments(resourcesURL.toString(),wpsListURL.toString()));
        tmpHttpEndpoints.setProcessList(diffSegments(resourcesURL.toString(),processListURL.toString()));
        tmpHttpEndpoints.setSearch(diffSegments(applicationURL.toString(), searchURL.toString()));
        
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

    public URL getSearchURL() {
        return searchURL;
    }

    public URL getWpsNamingEndpoint() {
        return wpsNamingEndpoint;
    }

    public URL getProcessNamingEndpoint() {
        return processNamingEndpoint;
    }

    public String getDomain() {
        return domain;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<File> getProcessRDFFiles() {
        return processRDFFiles;
    }

    public ArrayList<File> getWpsRDFFiles() {
        return wpsRDFFiles;
    }

    
    
    @Override
    public String toString(){
        if(! loaded)
            return "No configuration loaded";
        
        String ret = "Configuration:\n";
        if(configSource == null)
            ret += "Config file:      default\n";
        else
            ret += "Config file:      "+configSource.getAbsolutePath()+"\n";
        ret += "RDF store:        "+rdfMemoryDir.getAbsolutePath()+"\n";
        ret += "Start clean:      "+startClean+"\n";
        ret += "Network domain:   "+domain+"\n";
        ret += "Network owner:    "+owner+"\n";
        ret += "WPS:\n";
        for(int i=0; i<wpsRDFFiles.size();i++){
            ret+="                  "+wpsRDFFiles.get(i).getAbsolutePath()+"\n";
        }
        ret += "Process:\n";
        for(int i=0; i<processRDFFiles.size();i++){
            ret+="                  "+processRDFFiles.get(i).getAbsolutePath()+"\n";
        }
        ret += "Host URL:        "+hostURL.toString()+"\n";
        ret += "Resources URL:   "+resourcesURL.toString()+"\n";
        ret += "Application URL: "+applicationURL.toString()+"\n";
        ret += "Vocabulary URL:  "+vocabularyURL.toString()+"\n";
        ret += "Network URL:     "+networkURL.toString()+"\n";
        ret += "wpsList URL:     "+wpsListURL.toString()+"\n";
        ret += "processList URL: "+processListURL.toString()+"\n";
        ret += "search URL:      "+searchURL.toString()+"\n";
        ret += "WPS naming ep:   "+wpsNamingEndpoint.toString()+"\n";
        ret += "Proc naming ep:  "+processNamingEndpoint.toString()+"\n";
        ret += "Input naming ep: "+inputNamingEndpoint.toString()+"\n";
        ret += "Output naming ep:"+outputNamingEndpoint.toString();
    
        return ret;

   
    
   
        
        
    }
    
    
    
    
    
    
    
}

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
import java.io.IOException;
import org.apache.xmlbeans.XmlException;

/**
 * Loads and stores a configuration
 *
 * @author fbensman
 */
public class Configuration {

    private File configSource = null;
    private boolean loaded = false;
    private File rdfMemoryDir = null;
    private boolean startClean = true;
    private String domain = null;
    private String owner = null;
    private int port = -1;
    private ArrayList<File> processRDFFiles = null;
    private ArrayList<File> wpsRDFFiles = null;
    private String replaceableHost = null;
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
    private URL inputNamingEndpoint = null;
    private URL outputNamingEndpoint = null;
    
    
    // ----------   Default values --------------
    private String defaultConfigFileName = null;
    
    //Default file names
    private String defaultWPSMacrophyte = null;
    private String defaultProcessSelectReportingArea = null;
    private String defaultProcessSelectMSRLD5 = null;
    private String defaultProcessIntersect = null;
    private String defaultProcessSelectTopography = null;
    private String defaultProcessCharacteristics = null;
    private String defaultWPSModelValidation = null;
    private String defaultProcessReadData = null;
    private String defaultProcessHarmonize = null;
    private String defaultProcessCompare = null;
    private String defaultProcessFormat = null;
    //default administration
    private String defaultRDFDir = null;
    private boolean defaultStartClean = false;
    private String defaultOwner = null;
    private String defaultDomain = null;
    private int defaultPort = -1;
    //default endpoints
    private String defaultHostURL = null;
    private String defaultResourcesURL = null;
    private String defaultApplicationURL = null;
    private String defaultVocabularyURL = null;
    private String defaultWpsListURL = null;
    private String defaultProcessListURL = null;
    private String defaultSearchURL = null;
    //default naming endpoints
    private String defaultWpsNamingEndpoint = null;
    private String defaultProcessNamingEndpoint = null;
    private String defaultInputNamingEndpoint = null;
    private String defaultOutputNamingEndpoint = null;
    

    public Configuration() {
        wpsRDFFiles = new ArrayList<>();
        processRDFFiles = new ArrayList<>();
        buildDefaults();
        
    }

    
    private void buildDefaults(){
        defaultConfigFileName = "./config.xml";
        
        //filenames
        defaultWPSMacrophyte = "RDF" + File.separator + "LKN" + File.separator + "WPSMacrophyte.rdf";
        defaultProcessSelectReportingArea = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectReportingArea.rdf";
        defaultProcessSelectMSRLD5 = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectMSRLD5.rdf";
        defaultProcessIntersect = "RDF" + File.separator + "LKN" + File.separator + "ProcessIntersect.rdf";
        defaultProcessSelectTopography = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectTopography.rdf";
        defaultProcessCharacteristics = "RDF" + File.separator + "LKN" + File.separator + "ProcessCharacteristics.rdf";

        defaultWPSModelValidation = "RDF" + File.separator + "BAW" + File.separator + "WPSModelValidation.rdf";
        defaultProcessReadData = "RDF" + File.separator + "BAW" + File.separator + "ProcessReadData.rdf";
        defaultProcessHarmonize = "RDF" + File.separator + "BAW" + File.separator + "ProcessHarmonize.rdf";
        defaultProcessCompare = "RDF" + File.separator + "BAW" + File.separator + "ProcessCompare.rdf";
        defaultProcessFormat = "RDF" + File.separator + "BAW" + File.separator + "ProcessFormat.rdf";
        //admin
        defaultRDFDir = ".";
        defaultStartClean = true;
        defaultOwner = "HS-OS";
        defaultDomain = "RichWPS test domain";
        defaultPort = 4567;
        //default endpoints
        defaultHostURL = "http://localhost:4567";
        defaultResourcesURL = "resources";
        defaultApplicationURL = "semanticproxy";
        defaultVocabularyURL = "vocab";
        defaultWpsListURL = "wpss";
        defaultProcessListURL = "processes";
        defaultSearchURL = "search";
        //default naming endpoints
        defaultWpsNamingEndpoint = "wps";
        defaultProcessNamingEndpoint = "process";
        defaultInputNamingEndpoint = "input";
        defaultOutputNamingEndpoint = "output";
        
    }
    

    /**
     * Loads configuration from the specified file.
     *
     * @param file
     * @return
     * @throws Exception
     */
    public void load(File file) throws XmlException, IOException {
        //parse xml file
        configSource = file;
        ConfigurationDocument configDoc = null;
        try{
            configDoc = ConfigurationDocument.Factory.parse(file);
        }catch(XmlException x){
            throw new XmlException("Cannot parse config file "+file.getAbsolutePath()+".", x);
        }catch(IOException io){
            throw new IOException("Cannot read config file "+file.getAbsolutePath()+".", io);
        }
       
        //read contents
        ConfigurationDocument.Configuration config = configDoc.getConfiguration();
        String tmpRDFDir = config.getRDFDirectory();
        boolean tmpStartClean = config.getStartClean();
        String tmpOwner = config.getOwner();
        String tmpDomain = config.getDomain();
        int tmpPort = config.getPort();
        PreloadFiles tmpFileList = config.getPreloadFiles();
        String tmpReplaceableHost = tmpFileList.getReplaceableHost();
        HTTPEndpoints tmpHTTPEndpoints = config.getHTTPEndpoints();
        RDFNamingEndpoints tmpRDFNamingEndpoints = config.getRDFNamingEndpoints();

        rdfMemoryDir = new File(tmpRDFDir);
        startClean = tmpStartClean;
        owner = tmpOwner;
        domain = tmpDomain;
        port = tmpPort;
        if(tmpReplaceableHost != null && ! tmpReplaceableHost.equals(""))
            replaceableHost = tmpReplaceableHost;
        else 
            replaceableHost = null;

        wpsRDFFiles.clear();
        for (int i = 0; i < tmpFileList.sizeOfWPSArray(); i++) {
            wpsRDFFiles.add(new File(tmpFileList.getWPSArray(i)));
        }

        processRDFFiles.clear();
        for (int i = 0; i < tmpFileList.sizeOfProcessArray(); i++) {
            processRDFFiles.add(new File(tmpFileList.getProcessArray(i)));
        }

        //HTTP endpoints
        hostURL = new URL(tmpHTTPEndpoints.getHost());
        applicationURL = new URL(hostURL.toString() + "/" + tmpHTTPEndpoints.getApplication());
        resourcesURL = new URL(applicationURL.toString() + "/" + tmpHTTPEndpoints.getResources());
        vocabularyURL = new URL(resourcesURL.toString() + "/" + tmpHTTPEndpoints.getVocabulary());
        networkURL = resourcesURL;
        wpsListURL = new URL(resourcesURL.toString() + "/" + tmpHTTPEndpoints.getWPSList());
        processListURL = new URL(resourcesURL.toString() + "/" + tmpHTTPEndpoints.getProcessList());
        searchURL = new URL(applicationURL.toString() + "/" + tmpHTTPEndpoints.getSearch());

        //naming endpoints
        wpsNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getWPSNaming());
        processNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getProcessNaming());
        inputNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getInputNaming());
        outputNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getOutputNaming());
        loaded = true;
        
    }

    
    /**
     * Writes a default configuration to cwd
     *
     * @throws Exception
     */
    public void writeDefaultConfiguration() throws IOException {
        ConfigurationDocument doc = ConfigurationDocument.Factory.newInstance();
        ConfigurationDocument.Configuration config = doc.addNewConfiguration();

        config.setRDFDirectory(defaultRDFDir);
        config.setStartClean(defaultStartClean);
        config.setOwner(defaultOwner);
        config.setDomain(defaultDomain);
        config.setPort(defaultPort);
        
        PreloadFiles tmpPreloadFiles = config.addNewPreloadFiles();
        tmpPreloadFiles.addWPS(defaultWPSMacrophyte);
        tmpPreloadFiles.addWPS(defaultWPSModelValidation);
        tmpPreloadFiles.addProcess(defaultProcessSelectReportingArea);
        tmpPreloadFiles.addProcess(defaultProcessIntersect);
        tmpPreloadFiles.addProcess(defaultProcessSelectTopography);
        tmpPreloadFiles.addProcess(defaultProcessSelectMSRLD5);
        tmpPreloadFiles.addProcess(defaultProcessCharacteristics);
        tmpPreloadFiles.addProcess(defaultProcessReadData);
        tmpPreloadFiles.addProcess(defaultProcessHarmonize);
        tmpPreloadFiles.addProcess(defaultProcessCompare);
        tmpPreloadFiles.addProcess(defaultProcessFormat);

        HTTPEndpoints tmpHttpEndpoints = config.addNewHTTPEndpoints();
        tmpHttpEndpoints.setHost(defaultHostURL);
        tmpHttpEndpoints.setApplication(defaultApplicationURL);
        tmpHttpEndpoints.setResources(defaultResourcesURL);
        tmpHttpEndpoints.setVocabulary(defaultVocabularyURL);
        tmpHttpEndpoints.setWPSList(defaultWpsListURL);
        tmpHttpEndpoints.setProcessList(defaultProcessListURL);
        tmpHttpEndpoints.setSearch(defaultSearchURL);

        RDFNamingEndpoints tmpRDFNamingEndpoints = config.addNewRDFNamingEndpoints();
        tmpRDFNamingEndpoints.setInputNaming(defaultInputNamingEndpoint);
        tmpRDFNamingEndpoints.setOutputNaming(defaultOutputNamingEndpoint);
        tmpRDFNamingEndpoints.setWPSNaming(defaultWpsNamingEndpoint);
        tmpRDFNamingEndpoints.setProcessNaming(defaultProcessNamingEndpoint);
        try{
            FileWriter writer = new FileWriter(defaultConfigFileName);
            writer.write(doc.toString());
            writer.close();
        }catch(IOException io){
            throw new IOException("Cannot write default config to "+defaultConfigFileName,io);
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

    public boolean isLoaded() {
        return loaded;
    }

    public String getReplaceableHost() {
        return replaceableHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
    

    
    
    @Override
    public String toString() {
        if (!loaded) {
            return "No configuration loaded";
        }

        String ret = "Configuration:\n";
        if (configSource == null) {
            ret += "Config file:      default\n";
        } else {
            ret += "Config file:      " + configSource.getAbsolutePath() + "\n";
        }
        ret += "RDF store:        " + rdfMemoryDir.getAbsolutePath() + "\n";
        ret += "Start clean:      " + startClean + "\n";
        ret += "Network domain:   " + domain + "\n";
        ret += "Network owner:    " + owner + "\n";
        ret += "Port:             " + port + "\n";
        ret += "WPS:\n";
        for (int i = 0; i < wpsRDFFiles.size(); i++) {
            ret += "                  " + wpsRDFFiles.get(i).getAbsolutePath() + "\n";
        }
        ret += "Process:\n";
        for (int i = 0; i < processRDFFiles.size(); i++) {
            ret += "                  " + processRDFFiles.get(i).getAbsolutePath() + "\n";
        }
        ret += "Host URL:        " + hostURL.toString() + "\n";
        ret += "Resources URL:   " + resourcesURL.toString() + "\n";
        ret += "Application URL: " + applicationURL.toString() + "\n";
        ret += "Vocabulary URL:  " + vocabularyURL.toString() + "\n";
        ret += "Network URL:     " + networkURL.toString() + "\n";
        ret += "wpsList URL:     " + wpsListURL.toString() + "\n";
        ret += "processList URL: " + processListURL.toString() + "\n";
        ret += "search URL:      " + searchURL.toString() + "\n";
        ret += "WPS naming ep:   " + wpsNamingEndpoint.toString() + "\n";
        ret += "Proc naming ep:  " + processNamingEndpoint.toString() + "\n";
        ret += "Input naming ep: " + inputNamingEndpoint.toString() + "\n";
        ret += "Output naming ep:" + outputNamingEndpoint.toString();

        return ret;






    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.config.ConfigurationDocument;
import de.hsos.richwps.sp.config.HTTPEndpoints;
import de.hsos.richwps.sp.config.DataSources;
import de.hsos.richwps.sp.config.RDFNaming;
import de.hsos.richwps.sp.config.ResourceType;
import de.hsos.richwps.sp.config.WPSServer;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

/**
 * Loads and stores a configuration
 *
 * @author fbensman
 */
public class Configuration {
    private static final int E_WPSVALUE = 1;
    private static final int E_PROCESVALUE = 2;

    private File configSource = null;
    private boolean loaded = false;
    private File rdfMemoryDir = null;
    private boolean startClean = true;
    private String domain = null;
    private String owner = null;
    private int port = -1;
    private ArrayList<InputFile> inputFiles = null;
    private ArrayList<URL> wpsServers = null;
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
    private URL literalNamingEndpoint = null;
    private URL complexNamingEndpoint = null;
    private URL boundingBoxNamingEndpoint = null;
    
    
    // ----------   Default values --------------
    
    //Default file names
    private String defaultWPSMacrophyte = null;
    private String defaultProcessSelectReportingArea = null;
    private String defaultProcessSelectMSRLD5 = null;
    private String defaultProcessIntersect = null;
    private String defaultProcessSelectTopography = null;
    private String defaultProcessCharacteristics = null;
    private String defaultWPSModelValidation = null;
    private String defaultProcessCompare = null;
    private String defaultProcessFormat = null;
    private String defaultProcessHarmonize = null;
 
    
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
    private String defaultLiteralNamingEndpoint = null;
    private String defaultComplexNamingEndpoint = null;
    private String defaultBoundingBoxNamingEndpoint = null;
    

    public Configuration() {
        inputFiles = new ArrayList<>();
        wpsServers = new ArrayList<>();
        buildDefaults();
        
    }

    
    private void buildDefaults(){
        
        //filenames
        defaultWPSMacrophyte = "RDF" + File.separator + "LKN" + File.separator + "WPSMacrophyte.rdf";
        defaultProcessSelectReportingArea = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectReportingArea.rdf";
        defaultProcessSelectMSRLD5 = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectMSRLD5.rdf";
        defaultProcessIntersect = "RDF" + File.separator + "LKN" + File.separator + "ProcessIntersect.rdf";
        defaultProcessSelectTopography = "RDF" + File.separator + "LKN" + File.separator + "ProcessSelectTopography.rdf";
        defaultProcessCharacteristics = "RDF" + File.separator + "LKN" + File.separator + "ProcessCharacteristics.rdf";

        defaultWPSModelValidation = "RDF" + File.separator + "BAW" + File.separator + "WPSModelValidation.rdf";
        defaultProcessCompare = "RDF" + File.separator + "BAW" + File.separator + "ProcessCompare.rdf";
        defaultProcessFormat = "RDF" + File.separator + "BAW" + File.separator + "ProcessFormat.rdf";
        defaultProcessHarmonize = "RDF" + File.separator + "BAW" + File.separator + "ProcessHarmonize.rdf";           
        
        
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
        defaultLiteralNamingEndpoint = "literal";
        defaultComplexNamingEndpoint = "complex";
        defaultBoundingBoxNamingEndpoint = "boundingbox";
        
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
        DataSources dataSources = config.getDataSources();
        HTTPEndpoints tmpHTTPEndpoints = config.getHTTPEndpoints();
        RDFNaming tmpRDFNamingEndpoints = config.getRDFNaming();

        rdfMemoryDir = new File(tmpRDFDir);
        startClean = tmpStartClean;
        owner = tmpOwner;
        domain = tmpDomain;
        port = tmpPort;
       
        inputFiles.clear();
        for (de.hsos.richwps.sp.config.File f : dataSources.getFileArray()) {
            InputFile inputFile = new InputFile();
            String path = f.getPath();
            path = path.replace("/", File.separator);
            path = path.replace("\\", File.separator);
            inputFile.setFile(new File (path));
            ResourceType.Enum t = f.getType();
            if(t.intValue() == E_WPSVALUE){
                inputFile.setTyp(InputFile.Typ.WPS);
            }
            else{
                inputFile.setTyp(InputFile.Typ.Process);
            }
            
            if(f.isSetReplaceableHost()){
                inputFile.setReplacableHost(f.getReplaceableHost());
            }
            inputFiles.add(inputFile);
        }
        
        wpsServers.clear();
        for(WPSServer s : dataSources.getWPSServerArray()){
            wpsServers.add(new URL(s.getTargetURL()));
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
        literalNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getLiteralNaming());
        complexNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getComplexNaming());
        boundingBoxNamingEndpoint = new URL(resourcesURL.toString() + "/" + tmpRDFNamingEndpoints.getBoundingBoxNaming());
        loaded = true;
        
    }

    
    /**
     * Writes a default configuration to cwd, current object remains unchanged
     *
     * @throws Exception
     */
    public void writeDefaultConfiguration(File file) throws IOException {
        ConfigurationDocument doc = ConfigurationDocument.Factory.newInstance();
        ConfigurationDocument.Configuration config = doc.addNewConfiguration();
        XmlCursor cursor = doc.newCursor();
        cursor.toFirstChild();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String formattedDate = formatter.format(new Date());
        cursor.insertComment("Configuration file for RichWPS SemanticProxy - auto-generated " + formattedDate);
        cursor.dispose();


        config.setRDFDirectory(defaultRDFDir);
        config.setStartClean(defaultStartClean);
        config.setOwner(defaultOwner);
        config.setDomain(defaultDomain);
        config.setPort(defaultPort);
        
        DataSources dataSources = config.addNewDataSources();
        de.hsos.richwps.sp.config.File f = dataSources.addNewFile();
        f.setPath(defaultWPSMacrophyte);
        f.setType(ResourceType.Enum.forInt(E_WPSVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultWPSModelValidation);
        f.setType(ResourceType.Enum.forInt(E_WPSVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessSelectReportingArea);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessIntersect);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessSelectTopography);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessSelectMSRLD5);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessCharacteristics);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
     
        f = dataSources.addNewFile();
        f.setPath(defaultProcessHarmonize);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessCompare);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();
        
        f = dataSources.addNewFile();
        f.setPath(defaultProcessFormat);
        f.setType(ResourceType.Enum.forInt(E_PROCESVALUE));
        if(f.isSetReplaceableHost())
            f.unsetReplaceableHost();

        HTTPEndpoints tmpHttpEndpoints = config.addNewHTTPEndpoints();
        tmpHttpEndpoints.setHost(defaultHostURL);
        tmpHttpEndpoints.setApplication(defaultApplicationURL);
        tmpHttpEndpoints.setResources(defaultResourcesURL);
        tmpHttpEndpoints.setVocabulary(defaultVocabularyURL);
        tmpHttpEndpoints.setWPSList(defaultWpsListURL);
        tmpHttpEndpoints.setProcessList(defaultProcessListURL);
        tmpHttpEndpoints.setSearch(defaultSearchURL);

        RDFNaming tmpRDFNamingEndpoints = config.addNewRDFNaming();
        tmpRDFNamingEndpoints.setInputNaming(defaultInputNamingEndpoint);
        tmpRDFNamingEndpoints.setOutputNaming(defaultOutputNamingEndpoint);
        tmpRDFNamingEndpoints.setWPSNaming(defaultWpsNamingEndpoint);
        tmpRDFNamingEndpoints.setProcessNaming(defaultProcessNamingEndpoint);
        tmpRDFNamingEndpoints.setLiteralNaming(defaultLiteralNamingEndpoint);
        tmpRDFNamingEndpoints.setComplexNaming(defaultComplexNamingEndpoint);
        tmpRDFNamingEndpoints.setBoundingBoxNaming(defaultBoundingBoxNamingEndpoint);
        try{
            FileWriter writer = new FileWriter(file);
            writer.write(doc.toString());
            writer.close();
        }catch(IOException io){
            throw new IOException("Cannot write default config to "+file.getAbsolutePath(),io);
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

    

    public boolean isLoaded() {
        return loaded;
    }



    public int getPort() {
        return port;
    }

    public URL getLiteralNamingEndpoint() {
        return literalNamingEndpoint;
    }

    public URL getComplexNamingEndpoint() {
        return complexNamingEndpoint;
    }

    public URL getBoundingBoxNamingEndpoint() {
        return boundingBoxNamingEndpoint;
    }

    public ArrayList<InputFile> getInputFiles() {
        return inputFiles;
    }

    public ArrayList<URL> getWpsServers() {
        return wpsServers;
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
        ret += "InputFiles:\n";
        //TODO: insert input files and wps server configs
        for(InputFile f : inputFiles)
            ret += " File:       "+f.toString() + "\n";
        ret += "WPSservers:\n";
        for(URL wps : wpsServers)
            ret += " Server:     "+wps.toString() + "\n";
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
        ret += "Output naming ep:" + outputNamingEndpoint.toString() + "\n";
        ret += "Liter naming ep: " + literalNamingEndpoint.toString() + "\n";
        ret += "Compl naming ep: " + complexNamingEndpoint.toString() + "\n";
        ret += "BBox naming ep:  " + boundingBoxNamingEndpoint.toString();

        return ret;






    }
}

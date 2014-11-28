package de.hsos.richwps.sp;

import de.hsos.richwps.sp.imports.IWFSImportSource;
import de.hsos.richwps.sp.imports.wpsfileimporter.WPSFileImporter;
import de.hsos.richwps.sp.imports.IWPSImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import de.hsos.richwps.sp.imports.wfsfileimporter.WFSFileImporter;
import de.hsos.richwps.sp.imports.wpsharvester.WPSHarvester;
import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.IDGenerator;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.web.BrowseAccess;
import de.hsos.richwps.sp.web.CreateAccess;
import de.hsos.richwps.sp.web.DeleteAccess;
import de.hsos.richwps.sp.web.IDGeneratorAccess;
import de.hsos.richwps.sp.web.SearchAccess;
import de.hsos.richwps.sp.web.UpdateAccess;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xmlbeans.XmlException;

/**
 * Home of the main routine
 *
 */
public class App {

    /**
     * Main routine, args are not regarded. This class connects to the db
     * and sets up the web frontent, also initial data is loaded.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("SemanticProxy is starting..."); 

        //configure logging
        PropertyConfigurator.configure("log4j.properties");
        Logger.getLogger(App.class).info("SemanticProxy is starting...");

        //Load configuration
        Configuration config = loadConfiguration();
        System.out.println("*** Used configuration");
        System.out.println(config.toString());
        System.out.println("***");


        //Initialize vacabulary
        try {
            Vocabulary.init(config.getVocabularyURL());
        } catch (Exception e) {
            Logger.getLogger(App.class).error("Exception during initialization of vocabulary", e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }

        // Configure ID generator
        IDGenerator.configure( config.getWpsNamingEndpoint(),
                config.getProcessNamingEndpoint(), config.getInputNamingEndpoint(),
                config.getOutputNamingEndpoint(), config.getLiteralNamingEndpoint(),
                config.getComplexNamingEndpoint(), config.getBoundingBoxNamingEndpoint(),
                config.getWfsNamingEndpoint(), config.getFeatureTypeNamingEndpoint(),
                config.getQosNamingEndpoint());
        

        //Prepare db
        try {
            //create db connection and provide basic urls 
            DBAdministration.init(config.getRdfMemoryDir(), config.getResourcesURL(), config.getVocabularyURL());

            //if configured, clear db
            if (config.isStartClean()) {
                DBAdministration.clear();
            }

            //load initial data
            if (DBIO.size() == 0) {
                //create the root resource if db was empty
                Logger.getLogger(App.class).info("Inserting network node");
                ContentChanger.insertNetwork(config.getOwner(), config.getDomain());

                //import WPS information
                importWPSInformation(config);
                

                //import WFS information
                importWFSInformation(config);
            }

        } catch (Exception e) {
            Logger.getLogger(App.class).error(e.getClass() + "-exception during initialization of DB", e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }


        //prepare web frontend
        configureWebFrontEnd(config);

        System.out.println("Semantic Proxy is listening");
        Logger.getLogger(App.class).info("Semantic Proxy is listening");
    }

    /**
     * Loads a configuration from the file config.xml, if the file is missing a
     * default configuration file is created and loaded back
     *
     * @return
     */
    private static Configuration loadConfiguration() {
        Configuration config = new Configuration();
        File configFile = new File("config.xml");
        try {
            config.load(configFile);
        } catch (IOException e) {
            Logger.getLogger(App.class).warn(e.getClass() + "-exception when loading config.xml: ", e);
            try {
                Logger.getLogger(App.class).info("Writing default config.xml");
                config.writeDefaultConfiguration(configFile);
            } catch (IOException ioee) {
                Logger.getLogger(App.class).error("IO Exception when writing default config.xml: ", e);
                Logger.getLogger(App.class).info("Shutdown due to error");
                System.out.println("Shutdown due to error");
                System.exit(-1);
            }
            try {
                Logger.getLogger(App.class).info("Reading back default config.xml");
                config.load(configFile);
            } catch (XmlException | IOException io) {
                Logger.getLogger(App.class).error("IOException when reading back default config.xml", io);
                Logger.getLogger(App.class).info("Shutdown due to error");
                System.out.println("Shutdown due to error");
                System.exit(-1);
            }
        } catch (XmlException e) {
            Logger.getLogger(App.class).error("XMLException when reading config.xml. Is file malformatted?", e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }
        return config;
    }

    /**
     * Initializes data importers for WPS and WPS processes then collects and
     * inserts data
     *
     * @param config
     */
    private static void importWPSInformation(Configuration config) {

        //Sources
        ArrayList<IWPSImportSource> sourceList = new ArrayList<>();

        //configure file inporter
        InputFile[] files = config.getInputFiles().toArray(new InputFile[config.getInputFiles().size()]);
        WPSFileImporter fileImporter = new WPSFileImporter(files, config.getHostURL().toString());
        sourceList.add(fileImporter);

        //configure harvester
        try {
            for (URL target : config.getWpsServers()) {
                IWPSImportSource harvester = new WPSHarvester(target,
                        new URL(config.getWpsNamingEndpoint().toString()),
                        new URL(config.getProcessNamingEndpoint().toString()),
                        new URL(config.getInputNamingEndpoint().toString()),
                        new URL(config.getOutputNamingEndpoint().toString()),
                        new URL(config.getLiteralNamingEndpoint().toString()),
                        new URL(config.getComplexNamingEndpoint().toString()),
                        new URL(config.getBoundingBoxNamingEndpoint().toString()));
                sourceList.add(harvester);
            }
        } catch (MalformedURLException murle) {
            Logger.getLogger(App.class).warn(murle.getClass() + "Aborted configuration of WPS harvester \n", murle);
            System.err.println("[WARN] Aborted configuration of WPS harvester \n");
        }


        //loop through importers and import data
        for (IWPSImportSource source : sourceList) {

            //collect WPS
            while (true) {
                String rdf = null;
                try {
                    rdf = source.getNextWPS();
                } catch (ImportException e) {
                    Logger.getLogger(App.class).warn(e.getClass() + "Aborted reading a WPS of " + source.getInfo(), e);
                    System.err.println("[WARN] Aborted reading a WPS of " + source.getInfo());
                }
                if (rdf != null) {
                    try {
                        ContentChanger.insertWPS(rdf);
                    } catch (Exception e) {
                        Logger.getLogger(App.class).warn(e.getClass() + "Aborted inserting WPS \n" + rdf, e);
                        System.err.println("[WARN] Aborted inserting a WPS \n" + rdf);
                    }
                } else {
                    break;
                }
            }

            //collect processes
            while (true) {
                String rdf = null;
                try {
                    rdf = source.getNextProcess();
                } catch (ImportException e) {
                    Logger.getLogger(App.class).warn("Aborted reading a process of " + source.getInfo(), e);
                    System.err.println("[WARN] Aborted reading a process of " + source.getInfo());
                }
                if (rdf != null) {
                    try {
                        ContentChanger.insertProcess(rdf);
                    } catch (Exception e) {
                        Logger.getLogger(App.class).warn(e.getClass() + "Aborted inserting process RDF \n" + rdf, e);
                        System.err.println("[WARN] Aborted inserting process \n" + rdf);
                    }
                } else {
                    break;
                }
            }
        }

    }

    private static void importWFSInformation(Configuration config) {
        //Sources
        ArrayList<IWFSImportSource> sourceList = new ArrayList<>();

        //configure file inporter
        InputFile[] files = config.getInputFiles().toArray(new InputFile[config.getInputFiles().size()]);
        WFSFileImporter fileImporter = new WFSFileImporter(files, config.getHostURL().toString());
        sourceList.add(fileImporter);

        for (IWFSImportSource source : sourceList) {


            //collect WFS
            while (true) {
                String rdf = null;
                try {
                    rdf = source.getNextWFS();
                } catch (ImportException e) {
                    Logger.getLogger(App.class).warn(e.getClass() + "Aborted reading a WFS of " + source.getInfo(), e);
                    System.err.println("[WARN] Aborted reading a WFS of " + source.getInfo());
                }
                if (rdf != null) {
                    try {
                        ContentChanger.insertWFS(rdf);
                    } catch (Exception e) {
                        Logger.getLogger(App.class).warn(e.getClass() + "Aborted inserting WFS \n" + rdf, e);
                        System.err.println("[WARN] Aborted inserting a WFS \n" + rdf);
                    }
                } else {
                    break;
                }
            }
        }
    }
    
    
    
    

    /**
     * Configures the web frontend
     *
     * @param config
     */
    private static void configureWebFrontEnd(Configuration config) {
        // if there is a valid port in the config -> use it...
        // ...else use default port
        if (config.getPort() > 0) {
            spark.Spark.setPort(config.getPort());
        }
        //install http endpoints for web comunication
        BrowseAccess.activate(config.getApplicationURL(), config.getResourcesURL(),
                config.getVocabularyURL(), config.getNetworkURL(),
                config.getProcessListURL(), config.getWpsListURL());
        CreateAccess.activate(config.getProcessListURL(), config.getWpsListURL());
        DeleteAccess.activate(config.getProcessNamingEndpoint(), config.getWpsNamingEndpoint());
        UpdateAccess.activate(config.getProcessNamingEndpoint(), config.getWpsNamingEndpoint());
        SearchAccess.activate(config.getSearchURL());
        IDGeneratorAccess.activate(config.getIdGeneratorURL());
    }
}

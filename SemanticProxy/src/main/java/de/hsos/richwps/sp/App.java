package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.web.BrowseAccess;
import de.hsos.richwps.sp.web.CreateAccess;
import de.hsos.richwps.sp.web.DeleteAccess;
import de.hsos.richwps.sp.web.SearchAccess;
import de.hsos.richwps.sp.web.UpdateAccess;
import java.io.File;
import java.io.IOException;
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
     * Main routine, args args are not regarded. This class connects to the db
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
        Configuration config = new Configuration();
        File configFile = new File("config.xml");
        try {
            config.load(configFile);
        } catch (IOException e) {
            Logger.getLogger(App.class).warn(e.getClass()+"-exception when loading config.xml: ",e);
            try {
                Logger.getLogger(App.class).info("Writing default config.xml");
                config.writeDefaultConfiguration(configFile);
            } catch (IOException ioee) {  
                Logger.getLogger(App.class).error("IO Exception when writing default config.xml: ",e);
                Logger.getLogger(App.class).info("Shutdown due to error");
                System.out.println("Shutdown due to error");
                System.exit(-1);
            }
            try{
                Logger.getLogger(App.class).info("Reading back default config.xml");
                config.load(configFile);
            }catch(XmlException | IOException io){
                Logger.getLogger(App.class).error("IOException when reading back default config.xml",io);
                Logger.getLogger(App.class).info("Shutdown due to error");
                System.out.println("Shutdown due to error");
                System.exit(-1);
            }
        } catch (XmlException e) {
            Logger.getLogger(App.class).error("XMLException when reading config.xml. Is file malformatted?",e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }
        System.out.println("*** Used configuration");
        System.out.println(config.toString());
        System.out.println("***");

        
        //Initialize vacabulary
        try {
            Vocabulary.init(config.getVocabularyURL());
        } catch (Exception e) {
            Logger.getLogger(App.class).error("Exception during initialization of vocabulary",e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }


        //Prepare db
        try {
            //create connection and provide basic urls 
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

                //insert rdf resources from predefined files
                ArrayList<File> list = config.getWpsRDFFiles();
                for (int i = 0; i < list.size(); i++) {
                    //read rdf file
                    String content = TextFileReader.readPlainText(list.get(i));
                    //replace wildcard string with host
                    if (config.getReplaceableHost() != null) {
                        if (content.contains(config.getReplaceableHost())) {
                            content = content.replaceAll(config.getReplaceableHost(), config.getHostURL().toString());
                        }
                    }
                    //insert (modified) content into db
                    ContentChanger.insertWPS(content);
                    System.out.println("File " + list.get(i).getAbsolutePath() + " loaded");
                    Logger.getLogger(App.class).info("File " + list.get(i).getAbsolutePath() + " loaded");
                }

                list = config.getProcessRDFFiles();
                for (int i = 0; i < list.size(); i++) {
                    //read rdf file
                    String content = TextFileReader.readPlainText(list.get(i));
                    //replace wildcard string with host
                    if (config.getReplaceableHost() != null) {
                        if (content.contains(config.getReplaceableHost())) {
                            content = content.replaceAll(config.getReplaceableHost(), config.getHostURL().toString());
                        }
                    }
                    //insert (modified) content into db
                    ContentChanger.insertProcess(content);
                    System.out.println("File " + list.get(i).getAbsolutePath() + " loaded");
                    Logger.getLogger(App.class).info("File " + list.get(i).getAbsolutePath() + " loaded");
                }
            }


        } catch (Exception e) {
            Logger.getLogger(App.class).error(e.getClass()+"+-exception during initialization of DB",e);
            Logger.getLogger(App.class).info("Shutdown due to error");
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }


        //prepare web frontend
        
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
        DeleteAccess.activate(config.getProcessNamingURL(), config.getWpsNamingURL());
        UpdateAccess.activate(config.getProcessNamingURL(), config.getWpsNamingURL());
        SearchAccess.activate(config.getSearchURL());

        System.out.println("Semantic Proxy is listening");
        Logger.getLogger(App.class).info("Semantic Proxy is listening");
    }
}

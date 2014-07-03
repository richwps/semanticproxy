package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.types.RDFDocument;
import de.hsos.richwps.sp.web.BrowseAccess;
import de.hsos.richwps.sp.web.CreateAccess;
import de.hsos.richwps.sp.web.DeleteAccess;
import de.hsos.richwps.sp.web.SearchAccess;
import de.hsos.richwps.sp.web.UpdateAccess;
import java.io.File;
import java.util.ArrayList;

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
        System.out.println("Semantic Proxy is starting...");

        //Load configuration
        Configuration config = new Configuration();
        File configFile = new File("." + File.separator + "config.xml");
        try{
            if (!config.load(configFile)) {
                config.writeDefaultConfiguration();
                config.load(configFile);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }
        System.out.println(config.toString());

        //Initialize vacabulary
        try{
            Vocabulary.init(config.getVocabularyURL());
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }
        
            
        //Prepare db
        try {
            DBAdministration.init(config.getRdfMemoryDir(),config.getResourcesURL());
            if (config.isStartClean()) {
                DBAdministration.clear();
            }

            //load initial data
            if (DBIO.size() == 0) {
                ContentChanger.insertNetwork(config.getOwner(), config.getDomain());
                
                ArrayList<File> list = config.getWpsRDFFiles();
                for (int i = 0; i < list.size(); i++) {
                    String content = TextFileReader.readPlainText(list.get(i));
                    ContentChanger.pushWPSRDFintoDB(content);
                    System.out.println("File " + list.get(i).getAbsolutePath() + " loaded");
                }
                
                list = config.getProcessRDFFiles();
                for (int i = 0; i < list.size(); i++) {
                    String content = TextFileReader.readPlainText(list.get(i));
                    ContentChanger.pushProcessRDFintoDB(content);
                    System.out.println("File " + list.get(i).getAbsolutePath() + " loaded");
                }
            }

            //write whole db content to stdout
            RDFDocument doc = DBIO.getWholeDBContent();
            System.out.println("--- Current DB content ---");
            System.out.println(doc.rDFXMLRepresentation());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }


        //prepare web frontend
        new BrowseAccess(config.getApplicationURL(), config.getResourcesURL(), 
                config.getVocabularyURL(), config.getNetworkURL(), 
                config.getProcessListURL(), config.getWpsListURL());
        new CreateAccess(config.getProcessListURL(),config.getWpsListURL());
        new DeleteAccess(config.getProcessNamingURL(), config.getWpsNamingURL());
        new UpdateAccess(config.getProcessNamingURL(), config.getWpsNamingURL());
        new SearchAccess(config.getSearchURL());
        
        System.out.println("Semantic Proxy is listening");
    }
}

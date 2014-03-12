package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.types.RDFDocument;
import de.hsos.richwps.sp.web.BrowseAccess;
import de.hsos.richwps.sp.web.CreateAccess;
import java.io.File;

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

        //Prepare db
        try {
            DBAdministration.connect();
            DBAdministration.clear();
            if (DBIO.size() == 0) {
                String s = File.separator;
                File rdfFile = new File("." + s + "RDF" + s + "rwpsnetwork.rdf");
                DBIO.loadRDFXMLFile(rdfFile);
                rdfFile = new File("." + s + "RDF" + s + "rwpswps.rdf");
                DBIO.loadRDFXMLFile(rdfFile);
                rdfFile = new File("." + s + "RDF" + s + "rwpsprocess.rdf");
                DBIO.loadRDFXMLFile(rdfFile);
            }
            //write whole db content to stdout
            RDFDocument doc = DBIO.getWholeDBContent();
            System.out.println(doc.rDFXMLRepresentation());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Shutdown due to error");
            System.exit(-1);
        }

        //prepare web frontend
        new BrowseAccess();
        new CreateAccess();
        System.out.println("Semantic Proxy is listening");
    }
}

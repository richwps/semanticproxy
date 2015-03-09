/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.ContentGetter;
import de.hsos.richwps.sp.restlogic.LookupHandling;
import de.hsos.richwps.sp.restlogic.SearchHandling;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.types.SubjectList;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openrdf.model.Resource;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class LookupHandlingTest extends TestCase{
    
    private static final String RDF_TEST_DIR_NAME = "RDFTestDir";
    private static final String RESOURCES_URL_NAME = "http://localhost:4567/semanticproxy/resources";
    private static final String VOCAB_URL_NAME = "http://localhost:4567/semanticproxy/resources/vocab";
    private static final String OWNER = "Test owner";
    private static final String DOMAIN ="Test domain";
    private static final long INITIAL_SIZE = 3;
    private static final String TESTFILE_WPS_NAME = "RDF"+ File.separator+"BAW"+File.separator+"WPSModelValidation.rdf";
    private static final String TESTWPS_NAME = "http://localhost:4567/semanticproxy/resources/wps/ModelValidationWPS";
    private static final String TESTFILE_PROCESS_NAME = "RDF"+ File.separator+"BAW"+File.separator+"ProcessCompare.rdf";
    private static final String TESTPROCESS_NAME = "http://localhost:4567/semanticproxy/resources/process/compare";
    
    private static final String TESTWPS_ENDPOINT = "http://www.baw.de/geo/wps/modelfieldcomparator";
    private static final String TESTPROCESS_IDENTIFIER = "baw.modelvalidation.compare";
    
    private File rdfDataDir = null;
    private URL resURL = null;
    private URL vocURL = null;
    private Resource wpsResource = null;
    private Resource processResource = null;

    
    
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public LookupHandlingTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( LookupHandlingTest.class );
    }

    @Override
    public void setUp(){
        try{
            resURL = new URL(RESOURCES_URL_NAME);
            vocURL = new URL(VOCAB_URL_NAME);
            wpsResource = new URIImpl(TESTWPS_NAME);
            processResource = new URIImpl(TESTPROCESS_NAME);
        }catch(MalformedURLException murle){
            fail("Test setup for DBDeleteTest botched "+murle);
            return;
        }
        rdfDataDir = new File(RDF_TEST_DIR_NAME);
        rdfDataDir.mkdir();
        
        try{
            DBAdministration.init(rdfDataDir, resURL, vocURL);
            Vocabulary.init(vocURL);
            ContentChanger.insertNetwork(OWNER, DOMAIN);
            File testFile = new File(TESTFILE_WPS_NAME);
            DBIO.insertRDFXMLFileIntoDB(testFile);
            testFile = new File(TESTFILE_PROCESS_NAME);
            DBIO.insertRDFXMLFileIntoDB(testFile);
            //create link between wps and process
            org.openrdf.model.URI hasProcessPred = new URIImpl(Vocabulary.Process);
            DBIO.insertStatement(new StatementImpl(wpsResource, hasProcessPred, processResource));
        }catch(Exception e){
            fail("Test setup failed: "+e.getMessage());
        }
        
            
    } 
    
    
    @Override
    public void tearDown(){
        try{
            DBAdministration.close();
        }catch(Exception e){
            fail("Test tear down failed: "+e.getMessage());
        }

        for(File f : rdfDataDir.listFiles() ){
            f.delete();
        }
        rdfDataDir.delete();
    }
    
    
    /**
     * Tests looking up a process
     */
    public void testLookup() 
    {
        try {
            URL wpsEndpoint = new URL(TESTWPS_ENDPOINT);
            SubjectList subjectList = LookupHandling.processLookup(wpsEndpoint, TESTPROCESS_IDENTIFIER);
            assertTrue("The result list string contains one element", subjectList.size()==1 );
            
        } catch (RepositoryException ex) {
            fail("Method LookupHandling.processLookup("+TESTWPS_ENDPOINT+", "+TESTPROCESS_IDENTIFIER+") should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method LookupHandling.processLookup("+TESTWPS_ENDPOINT+", "+TESTPROCESS_IDENTIFIER+") should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method LookupHandling.processLookup("+TESTWPS_ENDPOINT+", "+TESTPROCESS_IDENTIFIER+") should not raise an exception: "+ex);
        }
        
        try {
            SubjectList subjectList = SearchHandling.processKeywordSearch(new String[] {"A not present keyword"});
            assertTrue("The result list string contains no elements", subjectList.isEmpty() );
        } catch (RepositoryException ex) {
            fail("Method SearchHandling.processKeywordSearch(\"A not present keyword\") should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method SearchHandling.processKeywordSearch(\"A not present keyword\") should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method SearchHandling.processKeywordSearch(\"A not present keyword\") should not raise an exception: "+ex);
        }
           
    }
    
    
    
    
   
  
   
    
}

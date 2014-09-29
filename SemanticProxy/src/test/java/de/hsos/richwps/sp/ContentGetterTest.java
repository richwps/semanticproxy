/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.ContentGetter;
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
public class ContentGetterTest extends TestCase{
    
    private static final String RDF_TEST_DIR_NAME = "RDFTestDir";
    private static final String RESOURCES_URL_NAME = "http://localhost:4567/semanticproxy/resources";
    private static final String VOCAB_URL_NAME = "http://localhost:4567/semanticproxy/resources/vocab";
    private static final String OWNER = "Test owner";
    private static final String DOMAIN = "Test domain";
    private static final long INITIAL_SIZE = 3;
    private static final String TESTFILE_WPS_NAME = "RDF" + File.separator + "BAW" + File.separator + "WPSModelValidation.rdf";
    private static final String TESTWPS_NAME = "http://localhost:4567/semanticproxy/resources/wps/ModelValidationWPS";
    private static final String TESTFILE_PROCESS_NAME = "RDF" + File.separator + "BAW" + File.separator + "ProcessCompare.rdf";
    private static final String TESTPROCESS_NAME = "http://localhost:4567/semanticproxy/resources/process/compare";
    private static final String TESTFILE_WFS_NAME = "RDF" + File.separator + "WFS" + File.separator + "WFSTest.rdf";
    private static final String TESTWFS_NAME = "http://localhost:4567/semanticproxy/resources/wfs/TestWFS";
    private File rdfDataDir = null;
    private URL resURL = null;
    private URL vocURL = null;
    private Resource wpsResource = null;
    private Resource processResource = null;
    private Resource wfsResource = null;
    private String rawWPSRDF = null;
    private String rawProcessRDF = null;
    private String rawWFSRDF = null;

    
    
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ContentGetterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ContentGetterTest.class );
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
     * Tests ContentGetter functions
     * Tests getting all processes
     */
    public void testGetAllProcesses() 
    {
        try {
            String processStr = ContentGetter.getAllProcesses();
            assertTrue("The result list string contains the processes URL", processStr.contains(TESTPROCESS_NAME) );
        } catch (RepositoryException ex) {
            fail("Method ContentGetter.getAllProcesses() should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentGetter.getAllProcesses() should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentGetter.getAllProcesses() should not raise an exception: "+ex);
        }
           
    }
    
    /**
     * Tests ContentGetter functions
     * Tests getting all WPSs
     */
    public void testGetAllWPS() 
    {
        try {
            String processStr = ContentGetter.getAllWPS();
            assertTrue("The result list string contains the WPSs URL", processStr.contains(TESTWPS_NAME) );
        } catch (RepositoryException ex) {
            fail("Method ContentGetter.getAllWPS() should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentGetter.getAllWPS() should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentGetter.getAllWPS() should not raise an exception: "+ex);
        }
           
    }
    
    /**
     * Tests ContentGetter functions
     * Tests getting RDF for a specific resource
     */
    public void testGetRDFFor() 
    {
        try {
            String wpsDescriptionStr = ContentGetter.getRDFFor(TESTWPS_NAME);
            assertTrue("The result list string contains the WPSs URL", wpsDescriptionStr.contains("about=\""+TESTWPS_NAME) );
        } catch (RepositoryException ex) {
            fail("Method ContentGetter.getRDFFor(TESTWPS_NAME) should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentGetter.getRDFFor(TESTWPS_NAME) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentGetter.getRDFFor(TESTWPS_NAME) should not raise an exception: "+ex);
        }
           
    }
    
    
    /**
     * Tests ContentGetter functions
     * Tests validation that a process exists
     */
    public void testProcessExists() 
    {
        try {
            boolean processExists = ContentGetter.processExists(TESTPROCESS_NAME);
            assertTrue("The process should exist", processExists );
        } catch (RepositoryException ex) {
            fail("Method ContentGetter.processExists(TESTPROCESS_NAME) should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentGetter.processExists(TESTPROCESS_NAME) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentGetter.processExists(TESTPROCESS_NAME) should not raise an exception: "+ex);
        }
           
    }
    
    
    /**
     * Tests ContentGetter functions
     * Tests validation that a WPS exists
     */
   public void testWPSExists() 
    {
        try {
            boolean wpsExists = ContentGetter.wpsExists(TESTWPS_NAME);
            assertTrue("The WPS should exist", wpsExists );
        } catch (RepositoryException ex) {
            fail("Method ContentGetter.wpsExists(TESTWPS_NAME) should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentGetter.wpsExists(TESTWPS_NAME) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentGetter.wpsExists(TESTWPS_NAME) should not raise an exception: "+ex);
        }
           
    }
  
   
    
}

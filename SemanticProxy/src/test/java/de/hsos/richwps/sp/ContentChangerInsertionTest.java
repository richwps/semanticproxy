/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.imports.fileimporter.TextFileReader;
import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.ValidationResult;
import de.hsos.richwps.sp.restlogic.Validator;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class ContentChangerInsertionTest extends TestCase{
    
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
    
    private File rdfDataDir = null;
    private URL resURL = null;
    private URL vocURL = null;
    private Resource wpsResource = null;
    private Resource processResource = null;
    private String rawWPSRDF = null;
    private String rawProcessRDF = null;
    
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ContentChangerInsertionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ContentChangerInsertionTest.class );
    }

    @Override
    public void setUp(){
        
        File aValidWPSFile = new File(TESTFILE_WPS_NAME);
        File aValidProcessFile = new File(TESTFILE_PROCESS_NAME);
        try {
            resURL = new URL(RESOURCES_URL_NAME);
            vocURL = new URL(VOCAB_URL_NAME);
            wpsResource = new URIImpl(TESTWPS_NAME);
            processResource = new URIImpl(TESTPROCESS_NAME);
            
            rawWPSRDF = TextFileReader.readPlainText(aValidWPSFile);
            rawProcessRDF = TextFileReader.readPlainText(aValidProcessFile);
        
            rdfDataDir = new File(RDF_TEST_DIR_NAME);
            rdfDataDir.mkdir();

            DBAdministration.init(rdfDataDir, resURL, vocURL);
            Vocabulary.init(vocURL);
            ContentChanger.insertNetwork(OWNER, DOMAIN);
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
     * Tests Validator
     * Tests validating a valid WPS description with no WPS in the DB
     */
    public void testInsertWPS() 
    {
        try {
            ContentChanger.insertWPS(rawWPSRDF);
            boolean subjExists = DBIO.subjectExists(new java.net.URL(wpsResource.stringValue()));
            assertTrue("Subject should exist now",subjExists);
        } catch (RDFException ex) {
            fail("Method  ContentChanger.insertWPS(rawWPSRDF) should not raise an exception: "+ex);
        } catch (IOException ex) {
            fail("Method  ContentChanger.insertWPS(rawWPSRDF) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method  ContentChanger.insertWPS(rawWPSRDF) should not raise an exception: "+ex);
        } catch (URISyntaxException ex) {
            fail("Method  ContentChanger.insertWPS(rawWPSRDF) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method  ContentChanger.insertWPS(rawWPSRDF) should not raise an exception: "+ex);
        }
       
    }
    
    
    public void testInsertProcess() 
    {
        try {
            ContentChanger.insertWPS(rawWPSRDF);           
            ContentChanger.insertProcess(rawProcessRDF);
            boolean subjExists = DBIO.subjectExists( new java.net.URL(processResource.stringValue()));
            assertTrue("Subject should exist now",subjExists);
        } catch (RDFException ex) {
            fail("Method  ContentChanger.insertProcess(rawProcessRDF) should not raise an exception: "+ex);
        } catch (IOException ex) {
            fail("Method  ContentChanger.insertProcess(rawProcessRDF) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method  ContentChanger.insertProcess(rawProcessRDF) should not raise an exception: "+ex);
        } catch (URISyntaxException ex) {
            fail("Method  ContentChanger.insertProcess(rawProcessRDF) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method  ContentChanger.insertProcess(rawProcessRDF) should not raise an exception: "+ex);
        }
       
    }
    
    
   
    
   
    
}

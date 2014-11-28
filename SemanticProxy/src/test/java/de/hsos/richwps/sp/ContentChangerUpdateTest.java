/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.imports.wpsfileimporter.TextFileReader;
import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBDelete;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.rdfdb.RDFException;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import de.hsos.richwps.sp.types.SubjectList;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class ContentChangerUpdateTest extends TestCase{
    
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
    public ContentChangerUpdateTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ContentChangerUpdateTest.class );
    }

    @Override
    public void setUp(){
        
        File aValidWPSFile = new File(TESTFILE_WPS_NAME);
        File aValidProcessFile = new File(TESTFILE_PROCESS_NAME);
        try{
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
     * Tests ContentChanger functions
     * Tests deleting a single process
     */
    public void testUpdateProcess() 
    {
        try {
            ContentChanger.updateProcess( rawProcessRDF, processResource.stringValue());
            boolean subjExists = DBIO.subjectExists(new java.net.URL(processResource.stringValue()));
            assertTrue("Subject should still exist",subjExists);
            SubjectList list = DBIO.getAllSubjectsForType(new URL( Vocabulary.ProcessClass ));
            assertTrue("One process should be found at this time", list.size() == 1 );
            
            Statement[] stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTWPS_NAME), new java.net.URL(Vocabulary.Process));
            assertTrue("1 statements should be found for processes", stmtArr.length==1 );
            
            if(stmtArr.length>0)
                assertTrue("Statement connects WPS -> process", stmtArr[0].getObject().stringValue().equals(processResource.stringValue()));
            
            stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTPROCESS_NAME), new java.net.URL(Vocabulary.WPS));
            assertTrue("1 statements should be found for wps", stmtArr.length==1 );
            
            if(stmtArr.length>0)
                assertTrue("Statement connects WPS <- process", stmtArr[0].getObject().stringValue().equals(wpsResource.stringValue()));
            
        } catch (RepositoryException ex) {
            fail("Method ContentChanger.updateProcess(rawProcessRDF, processResource.stringValue()) should not raise an exception: "+ex);
        } catch (IllegalStateException ex) {
            fail("Method ContentChanger.updateProcess(rawProcessRDF, processResource.stringValue()) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentChanger.updateProcess(rawProcessRDF, processResource.stringValue()) should not raise an exception: "+ex);
        }
           
    }
    
    /**
     * Tests ContentChanger functions
     * Tests deleting a WPS without any process atached
     */
    public void testUpdateWPS() 
    {
        try {
            ContentChanger.updateWPS( rawWPSRDF,wpsResource.stringValue());
            SubjectList list = DBIO.getAllSubjectsForType(new URL( Vocabulary.WPSClass ));
            
            list = DBIO.getAllSubjectsForType(new URL( Vocabulary.ProcessClass ));
            assertTrue("1 process should be found at this time", list.size() == 1 );
            
            Statement[] stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTWPS_NAME), new java.net.URL(Vocabulary.Type));
            assertTrue("1 statements should be found for wps type", stmtArr.length==1 );
            
            stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTWPS_NAME), new java.net.URL(Vocabulary.Endpoint));
            assertTrue("1 statements should be found for endpoint", stmtArr.length==1 );
            
            stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTWPS_NAME), new java.net.URL(Vocabulary.Process));
            assertTrue("1 statements should be found for processes", stmtArr.length==1 );
            
            if(stmtArr.length>0)
                assertTrue("Statement connects WPS -> process", stmtArr[0].getObject().stringValue().equals(processResource.stringValue()));
            
            stmtArr = DBIO.getStatementsForSubjAndPred(new java.net.URL(TESTPROCESS_NAME), new java.net.URL(Vocabulary.WPS));
            assertTrue("1 statements should be found for wps", stmtArr.length==1 );
            
            if(stmtArr.length>0)
                assertTrue("Statement connects WPS <- process", stmtArr[0].getObject().stringValue().equals(wpsResource.stringValue()));
            
           
            
        } catch (IllegalStateException ex) {
            fail("Method ContentChanger.deleteWPS(wpsResource.stringValue()) should not raise an exception: "+ex);
        }catch (RepositoryException ex) {
            fail("Method ContentChanger.deleteWPS(wpsResource.stringValue()) should not raise an exception: "+ex);
        } catch (Exception ex) {
            fail("Method ContentChanger.deleteWPS(wpsResource.stringValue()) should not raise an exception: "+ex);
        }
    }
    
    
   
    
   
  
   
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
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
public class DBReadTest extends TestCase{
    
    private static final String RDF_TEST_DIR_NAME = "RDFTestDir";
    private static final String RESOURCES_URL_NAME = "http://localhost:4567/semanticproxy/resources";
    private static final String VOCAB_URL_NAME = "http://localhost:4567/semanticproxy/resources/vocab";
    private static final String OWNER = "Test owner";
    private static final String DOMAIN ="Test domain";
    private static final long INITIAL_SIZE = 3;
    private static final String ANY_SUBJECT = "http://localhost:4567/semanticproxy/resources/anysubj";
    private static final String TESTFILE_NAME = "RDF"+ File.separator+"BAW"+File.separator+"ProcessCompare.rdf";
    private static final String TESTPROCESS_NAME = "http://localhost:4567/semanticproxy/resources/process/compare";
    
    private File rdfDataDir = null;
    private URL resURL = null;
    private URL vocURL = null;
    private Resource anySubj = null;
    
    
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DBReadTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DBReadTest.class );
    }

    @Override
    public void setUp(){
        try{
            resURL = new URL(RESOURCES_URL_NAME);
            vocURL = new URL(VOCAB_URL_NAME);
            anySubj = new URIImpl(ANY_SUBJECT);
        }catch(MalformedURLException murle){
            fail("Test setup for DBReadTest botched "+murle);
            return;
        }
        rdfDataDir = new File(RDF_TEST_DIR_NAME);
        rdfDataDir.mkdir();
        
        try{
            DBAdministration.init(rdfDataDir, resURL, vocURL);
            Vocabulary.init(vocURL);
            ContentChanger.insertNetwork(OWNER, DOMAIN);
            File testFile = new  File(TESTFILE_NAME);
            DBIO.insertRDFXMLFileIntoDB(testFile);
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
     * Tests basis DB IO functions
     */
    public void testGetAllStmtsForType() 
    {
        try {
            SubjectList sList = DBIO.getAllSubjectsForType(new URL(Vocabulary.DataInputClass));
            assertEquals("Process in DB should have 2 inputs",sList.size(), 2);
        } catch (IllegalStateException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (MalformedURLException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (RDFException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        }
           
    }
    
    /**
     * Tests basis DB IO functions
     */
    public void testGetResourceDescription() 
    {
        try {
            String desc = DBIO.getResourceDescription(new URL(TESTPROCESS_NAME));
            assertNotNull("Desc should contain xml/rdf expressions",desc);
        } catch (IllegalStateException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (MalformedURLException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        } catch (RDFException ex) {
            fail("Method DBIO.getAllSubjectsForType(resURL) should not raise an exception: "+ex);
        }    
    }
    
    
    /**
     * Tests basis DB IO functions
     */
    public void testGetAllStmtsForSubjectAndPredicate() 
    {
        try {
            Statement[] stmtArr = DBIO.getStatementsForSubjAndPred(new URL(TESTPROCESS_NAME),new URL(Vocabulary.Identifier));
            assertEquals("1 statement should be found for given process and predicate", 1, stmtArr.length);
        } catch (IllegalStateException ex) {
            fail("Method DBIO.getStatementsForSubjAndPred(new URL(TESTPROCESS_NAME),new URL(Vocabulary.Identifier) should not raise an exception: "+ex);
        } catch (MalformedURLException ex) {
            fail("Method DBIO.getStatementsForSubjAndPred(new URL(TESTPROCESS_NAME),new URL(Vocabulary.Identifier) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method DBIO.getStatementsForSubjAndPred(new URL(TESTPROCESS_NAME),new URL(Vocabulary.Identifier) should not raise an exception: "+ex);
        }
    }
    
     /**
     * Tests basis DB IO functions
     */
    public void testSubjectExists() 
    {
        try {
            boolean boo = DBIO.subjectExists(new URL(TESTPROCESS_NAME));
            assertTrue("The subject should be found",boo);
        } catch (IllegalStateException ex) {
            fail("Method DBIO.subjectExists(new URL(TESTPROCESS_NAME)) should not raise an exception: "+ex);
        } catch (MalformedURLException ex) {
            fail("Method DBIO.subjectExists(new URL(TESTPROCESS_NAME)) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method DBIO.subjectExists(new URL(TESTPROCESS_NAME)) should not raise an exception: "+ex);
        } catch (RDFException ex) {
            fail("Method DBIO.subjectExists(new URL(TESTPROCESS_NAME)) should not raise an exception: "+ex);
        }
    }
    
}

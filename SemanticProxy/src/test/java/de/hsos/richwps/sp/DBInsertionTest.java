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
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openrdf.model.Resource;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.repository.RepositoryException;

/**
 *
 * @author fbensman
 */
public class DBInsertionTest extends TestCase{
    
    private static final String RDF_TEST_DIR_NAME = "RDFTestDir";
    private static final String RESOURCES_URL_NAME = "http://localhost:4567/semanticproxy/resources";
    private static final String VOCAB_URL_NAME = "http://localhost:4567/semanticproxy/resources/vocab";
    private static final String OWNER = "Test owner";
    private static final String DOMAIN ="Test domain";
    private static final long INITIAL_SIZE = 3;
    private static final String ANY_SUBJECT = "http://localhost:4567/semanticproxy/resources/anysubj";
    private static final String TESTFILE_NAME = "RDF"+ File.separator+"BAW"+File.separator+"ProcessCompare.rdf";
    
    private File rdfDataDir = null;
    private URL resURL = null;
    private URL vocURL = null;
    private Resource anySubj = null;
    
    
     /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DBInsertionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DBInsertionTest.class );
    }

    @Override
    public void setUp(){
        try{
            resURL = new URL(RESOURCES_URL_NAME);
            vocURL = new URL(VOCAB_URL_NAME);
            anySubj = new URIImpl(ANY_SUBJECT);
        }catch(MalformedURLException murle){
            fail("Test setup for DBInsertionTest botched "+murle);
            return;
        }
        rdfDataDir = new File(RDF_TEST_DIR_NAME);
        rdfDataDir.mkdir();
        
        try{
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
     * Tests basis DB IO functions
     */
    public void testInsertStatement() 
    {
        
        org.openrdf.model.URI anyPred = new URIImpl(Vocabulary.Title);
        org.openrdf.model.Literal anyLiteral = new LiteralImpl("any any any");
        try{
            DBIO.insertStatement(new StatementImpl(anySubj, anyPred, anyLiteral));
            long s = DBIO.size();
            assertEquals("Size of DB should now be 1",INITIAL_SIZE+1,s );
            DBIO.insertStatement(new StatementImpl(anySubj, anyPred, new LiteralImpl("another another another")));
            s = DBIO.size();
            assertEquals("Size of DB should now be 2", INITIAL_SIZE+2,s);
        }catch(RepositoryException re){
            fail("Method insertStatement() should not raise an exception: "+re);
        }catch(RDFException rdfe){
            fail("Method insertStatement() should not raise an exception: "+rdfe);
        }
           
    }
    
    /**
     * Tests insertion of RDF file into DB
     */
    public void testInsertRDFFile() 
    {
       File testFile = new  File(TESTFILE_NAME);
        try {
            DBIO.insertRDFXMLFileIntoDB(testFile);
            long s = DBIO.size();
            assertTrue("DB content should be larger than the 3 network statements", INITIAL_SIZE < s );
        } catch (IllegalStateException ex) {
            fail("Method DBIO.loadRDFXMLFile() should not raise an exception: "+ex.getMessage());
        } catch (RepositoryException ex) {
            fail("Method DBIO.loadRDFXMLFile() should not raise an exception: "+ex.getMessage());
        } catch (RDFException ex) {
            fail("Method DBIO.loadRDFXMLFile() should not raise an exception: "+ex.getMessage());
        } catch (IOException ex) {
            fail("Method DBIO.loadRDFXMLFile() should not raise an exception: "+ex.getMessage());
        }

        
    }
    
    
    /**
     * Tests basis DB IO functions
     */
    public void testInsertRDFString() 
    {
       File testFile = new  File(TESTFILE_NAME);
       
       
       String content = null;
        try {
            content = TextFileReader.readPlainText(testFile);
        } catch (Exception ex) {
            fail("Loading test data should not raise an exception: "+ex);
        }
       
        try {
            DBIO.insertRDFXMLStringIntoDB(content);
            long s = DBIO.size();
            assertTrue("DB content should be larger than the 3 network statements", INITIAL_SIZE < s );
        } catch (IllegalStateException ex) {
            fail("Method DBIO.loadRDFXMLStringIntoDB(content) should not raise an exception: "+ex);
        } catch (RepositoryException ex) {
            fail("Method DBIO.loadRDFXMLStringIntoDB(content) should not raise an exception: "+ex);
        } catch (RDFException ex) {
            fail("Method DBIO.loadRDFXMLStringIntoDB(content) should not raise an exception: "+ex);
        } catch (IOException ex) {
            fail("Method DBIO.loadRDFXMLStringIntoDB(content) should not raise an exception: "+ex);
        }

        
    }
}

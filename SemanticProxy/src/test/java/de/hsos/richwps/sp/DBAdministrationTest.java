package de.hsos.richwps.sp;

import de.hsos.richwps.sp.rdfdb.DBAdministration;
import de.hsos.richwps.sp.rdfdb.DBIO;
import de.hsos.richwps.sp.restlogic.ContentChanger;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openrdf.repository.RepositoryException;
/**
 *
 * @author fbensman
 */
public class DBAdministrationTest extends TestCase{
    
    private static final String RDF_TEST_DIR_NAME = "RDFTestDir";
    private static final String RESOURCES_URL_NAME = "http://localhost:4567/semanticproxy/resources";
    private static final String VOCAB_URL_NAME = "http://localhost:4567/semanticproxy/resources/vocab";
    private static final String OWNER = "Test owner";
    private static final String DOMAIN ="Test domain";
    private static final long EXPECTED_SIZE = 3;
    
    private File dir = null;
    private URL resURL = null;
    private URL vocURL = null;
    
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DBAdministrationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DBAdministrationTest.class );
    }

    @Override
    public void setUp(){
        try{
            resURL = new URL(RESOURCES_URL_NAME);
            vocURL = new URL(VOCAB_URL_NAME);
        }catch(MalformedURLException murle){
            fail("Test setup for DBAdministration botched "+murle);
            return;
        }
        dir = new File(RDF_TEST_DIR_NAME);
        dir.mkdir();
        
    } 
    
    
    @Override
    public void tearDown(){
        for(File f : dir.listFiles() ){
            f.delete();
        }
        dir.delete();
    }
    
    
    /**
     * Tests writing the default configuration
     * Tests reading the default configuration
     */
    public void testDBAdministration() 
    {
        
        try{
            DBAdministration.init(dir, resURL, vocURL);
        }catch(RepositoryException re){
            fail("Init should not throw an exception: "+re);
            return;
        }
        assertNotNull("Respository should not be null", DBAdministration.getRepository());
        
        try{
            DBAdministration.init(dir, resURL, vocURL);
            fail("Init should not work a second time");
        }catch(RepositoryException re){
            //beeing here is ok
        }catch(IllegalStateException ise){
            //beeing here is ok
        }
        try {
            Vocabulary.init(vocURL);
        } catch (Exception ex) {
            fail("Initializatio of vocabulary should not raise an exception: "+ex.getMessage());
        }
        assertTrue("Vocabulary element should start with a valid URL", Vocabulary.Input.startsWith(VOCAB_URL_NAME));
        
        
        try {
            ContentChanger.insertNetwork(OWNER, DOMAIN);
        } catch (Exception ex) {
            fail("Insert network should not raise an exception: "+ex.getMessage());
            return;
        }
        
        try{
            long s = DBIO.size();
            assertEquals("DB should contain 2 entries",s, EXPECTED_SIZE);
        }catch(RepositoryException re){
            fail("Method size() should not raise an exception: "+re);
            return;
        }
        
        try{
            DBAdministration.clear();
        }catch(RepositoryException re){
            fail("Method size() should not raise an exception: "+re);
            return;
        }
        
        
        try{
            long s = DBIO.size();
            assertEquals("Size of DB should now be empty",s, 0);
        }catch(RepositoryException re){
            fail("Method size() should not raise an exception: "+re);
            return;
        }
        
        
        try{
            DBAdministration.close();
        }catch(RepositoryException re){
            fail("Close should not throw an exception: "+re);
        }
        
        
    }
    
    
    
}

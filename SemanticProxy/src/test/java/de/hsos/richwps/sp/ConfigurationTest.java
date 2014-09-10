/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import java.io.File;
import java.io.IOException;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.xmlbeans.XmlException;

/**
 *
 * @author fbensman
 */
public class ConfigurationTest extends TestCase{
    
    private File testFile = null;
    private static final String TESTFILE_NAME = "configTestfile.xml";
    private static final int DEFAULT_PORT = 4567;
    
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConfigurationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConfigurationTest.class );
    }

    @Override
    public void setUp(){
        if(testFile != null ){
            if(testFile.exists())
                testFile.delete();
            testFile = null;
        }
    } 
    
    
    @Override
    public void tearDown(){
        testFile.delete();
        testFile = null;
    }
    
    
    /**
     * Tests writing the default configuration
     * Tests reading the default configuration
     */
    public void testConfiguration()
    {
        
        Configuration config = new Configuration();
        testFile = new File(TESTFILE_NAME);
        try{
            config.writeDefaultConfiguration(testFile);
        }catch(IOException ioe){
            fail("Writing default configuration should not throw an IOException: "+ioe);
        }
        try{
            config.load(testFile);
        }catch(XmlException xmle){
            fail("Loading default configuration should not throw an XmlException: "+xmle);
        }catch(IOException ioe){
            fail("Loading default configuration should not throw an IOException: "+ioe);
        }
        assertNotNull("ApplicationURL should not be null", config.getApplicationURL());
        assertNotNull("Domain should not be null", config.getDomain());
        assertNotNull("HostURL should not be null", config.getHostURL());
        assertNotNull("InputNamingEndpoint should not be null", config.getInputNamingEndpoint());
        assertNotNull("NetworkURL should not be null", config.getNetworkURL());
        assertNotNull("OutputNamingEndpoint should not be null", config.getOutputNamingEndpoint());
        assertNotNull("Owner should not be null", config.getOwner());
        assertNotNull("ProcessListURL should not be null", config.getProcessListURL());
        assertNotNull("ProcessNamingEndpoint should not be null", config.getProcessNamingEndpoint());
        assertNotNull("ProcessNamingURL should not be null", config.getProcessNamingEndpoint());
        assertNotNull("ProcessRDFFiles should not be null", config.getProcessRDFFiles());
        assertNotNull("RdfMemoryDir should not be null", config.getRdfMemoryDir());
        assertNotNull("ResourcesURL should not be null", config.getResourcesURL());
        assertNotNull("SearchURL should not be null", config.getSearchURL());
        assertNotNull("VocabularyURL should not be null", config.getVocabularyURL() );
        assertNotNull("WpsListURL should not be null", config.getWpsListURL());
        assertNotNull("WpsNamingEndpoint should not be null", config.getWpsNamingEndpoint());
        assertNotNull("WpsNamingURL should not be null", config.getWpsNamingEndpoint());
        assertNotNull("WpsRDFFiles should not be null", config.getWpsRDFFiles());
        assertNull("ReplacableHost should be null", config.getReplaceableHost());
        assertEquals("Port should be "+ DEFAULT_PORT,config.getPort(), DEFAULT_PORT);
        
    }
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.mweserver.LaskuraClient.LaskuraCommand;
import java.net.MalformedURLException;
import java.net.URL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Gaurav Manek
 */
public class LaskuraClientTest {
    
    public LaskuraClientTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of request method, of class LaskuraClient.
     */
    @Test
    public void testRequest() throws MalformedURLException, AbstractIOException {
        LaskuraClient c = new LaskuraClient(new URL("http://127.0.0.1/laskura/"));
        //System.err.println(c.login("a", "b"));
        
        //toSend.addProperty("action", c.getCommandString());
    }
    
}

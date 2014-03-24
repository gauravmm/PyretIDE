/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.laskura.LaskuraClient;
import edu.brown.cs.cutlass.sys.io.AbstractIOException;
import edu.brown.cs.cutlass.sys.io.laskura.ui.PnlLaskuraLogin;
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
        URL u = new URL("http://127.0.0.1/laskura/");
        LaskuraClient c = new LaskuraClient(u);
        
        System.err.println(PnlLaskuraLogin.getLogin(u));
    }
    
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io.mweserver;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Option;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gaurav Manek
 */
public class LaskuraIOTest {
    
    public LaskuraIOTest() {
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
     * Test of getConfigurationFile method, of class LaskuraIO.
     */
    @Test
    public void testGetConfigurationFile() throws Exception {
        System.out.println("getConfigurationFile");
        String identifier = "";
        LaskuraIO instance = new LaskuraIO();
        List<String> expResult = null;
        List<String> result = instance.getConfigurationFile(identifier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfigurationFile method, of class LaskuraIO.
     */
    @Test
    public void testSetConfigurationFile_String_List() throws Exception {
        System.out.println("setConfigurationFile");
        String identifier = "";
        List<? extends CharSequence> contents = null;
        LaskuraIO instance = new LaskuraIO();
        instance.setConfigurationFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfigurationFile method, of class LaskuraIO.
     */
    @Test
    public void testSetConfigurationFile_String_CharSequence() throws Exception {
        System.out.println("setConfigurationFile");
        String identifier = "";
        CharSequence contents = null;
        LaskuraIO instance = new LaskuraIO();
        instance.setConfigurationFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserFile method, of class LaskuraIO.
     */
    @Test
    public void testGetUserFile() throws Exception {
        System.out.println("getUserFile");
        LaskuraIdentifier identifier = null;
        LaskuraIO instance = new LaskuraIO();
        List<String> expResult = null;
        List<String> result = instance.getUserFile(identifier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserFile method, of class LaskuraIO.
     */
    @Test
    public void testSetUserFile_LaskuraIdentifier_List() throws Exception {
        System.out.println("setUserFile");
        LaskuraIdentifier identifier = null;
        List<? extends CharSequence> contents = null;
        LaskuraIO instance = new LaskuraIO();
        instance.setUserFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserFile method, of class LaskuraIO.
     */
    @Test
    public void testSetUserFile_LaskuraIdentifier_CharSequence() throws Exception {
        System.out.println("setUserFile");
        LaskuraIdentifier identifier = null;
        CharSequence contents = null;
        LaskuraIO instance = new LaskuraIO();
        instance.setUserFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of requestUserFileDestination method, of class LaskuraIO.
     */
    @Test
    public void testRequestUserFileDestination() throws Exception {
        System.out.println("requestUserFileDestination");
        LaskuraIO instance = new LaskuraIO();
        Option<LaskuraIdentifier> expResult = null;
        Option<LaskuraIdentifier> result = instance.requestUserFileDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of requestUserFileSource method, of class LaskuraIO.
     */
    @Test
    public void testRequestUserFileSource() throws Exception {
        System.out.println("requestUserFileSource");
        LaskuraIO instance = new LaskuraIO();
        Option<LaskuraIdentifier> expResult = null;
        Option<LaskuraIdentifier> result = instance.requestUserFileSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdentifierParser method, of class LaskuraIO.
     */
    @Test
    public void testGetIdentifierParser() {
        System.out.println("getIdentifierParser");
        LaskuraIO instance = new LaskuraIO();
        AbstractIdentifierParser<LaskuraIdentifier> expResult = null;
        AbstractIdentifierParser<LaskuraIdentifier> result = instance.getIdentifierParser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

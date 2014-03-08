/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io;

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
 * @author milesholland
 */
public class DiskIOTest {
    
    public DiskIOTest() {
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
     * Test of getConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testGetConfigurationFile() {
        System.out.println("getConfigurationFile");
        String identifier = "";
        DiskIO instance = new DiskIO();
        List<String> expResult = null;
        List<String> result = instance.getConfigurationFile(identifier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_List() {
        System.out.println("setConfigurationFile");
        String identifier = "";
        List<? extends CharSequence> contents = null;
        DiskIO instance = new DiskIO();
        instance.setConfigurationFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_CharSequence() {
        System.out.println("setConfigurationFile");
        String identifier = "";
        CharSequence contents = null;
        DiskIO instance = new DiskIO();
        instance.setConfigurationFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserFile method, of class DiskIO.
     */
    @Test
    public void testGetUserFile() {
        System.out.println("getUserFile");
        DiskIdentifier identifier = null;
        DiskIO instance = new DiskIO();
        List<String> expResult = null;
        List<String> result = instance.getUserFile(identifier);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_CharSequence() {
        System.out.println("setUserFile");
        DiskIdentifier identifier = null;
        CharSequence contents = null;
        DiskIO instance = new DiskIO();
        instance.setUserFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_List() {
        System.out.println("setUserFile");
        DiskIdentifier identifier = null;
        List<? extends CharSequence> contents = null;
        DiskIO instance = new DiskIO();
        instance.setUserFile(identifier, contents);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of requestUserFileDestination method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileDestination() {
        System.out.println("requestUserFileDestination");
        DiskIO instance = new DiskIO();
        Option<DiskIdentifier> expResult = null;
        Option<DiskIdentifier> result = instance.requestUserFileDestination();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of requestUserFileSource method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileSource() {
        System.out.println("requestUserFileSource");
        DiskIO instance = new DiskIO();
        Option<DiskIdentifier> expResult = null;
        Option<DiskIdentifier> result = instance.requestUserFileSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIdentifierParser method, of class DiskIO.
     */
    @Test
    public void testGetIdentifierParser() {
        System.out.println("getIdentifierParser");
        DiskIO instance = new DiskIO();
        AbstractIdentifierParser<DiskIdentifier> expResult = null;
        AbstractIdentifierParser<DiskIdentifier> result = instance.getIdentifierParser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

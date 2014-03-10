/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io;

import edu.brown.cs.cutlass.util.Option;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    
    static DiskIO testIO;
    static String inputIdentifier;
    static List<String> inputContent;
    static DiskIdentifier testDID;
    
    public DiskIOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testIO = new DiskIO();
        inputIdentifier = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "cutLassTest.arr";
        inputContent = new ArrayList<>();
        inputContent.add("line 1");
        inputContent.add("line 2");
        inputContent.add("line 3");
        testDID = new DiskIdentifier(inputIdentifier);
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
        List<String> result = testIO.getConfigurationFile(inputIdentifier);
        assertEquals(result.get(0), "line 1");
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_List() {
        System.out.println("setConfigurationFile");
        testIO.setConfigurationFile(inputIdentifier, inputContent);
        assertEquals(false, true);
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_CharSequence() {
        System.out.println("setConfigurationFile");
        testIO.setConfigurationFile(inputIdentifier, inputContent);
        assertEquals(false, true);
    }

    /**
     * Test of getUserFile method, of class DiskIO.
     */
    @Test
    public void testGetUserFile() {
        System.out.println("getUserFile");
        List<String> result = testIO.getUserFile(testDID);
        assertEquals(result.get(0), "line 1");
    }

    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_CharSequence() {
        System.out.println("setUserFile");
        testIO.setUserFile(testDID, inputContent);
        assertEquals(false, true);
    }

    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_List() {
        System.out.println("setUserFile");
        testIO.setUserFile(testDID, inputContent);
        assertEquals(false, true);
    }

    /**
     * Test of requestUserFileDestination method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileDestination() {
        System.out.println("requestUserFileDestination");
        Option<DiskIdentifier> result = testIO.requestUserFileDestination();
        assertEquals(result.hasData(), false);
    }

    /**
     * Test of requestUserFileSource method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileSource() {
        System.out.println("requestUserFileSource");
        Option<DiskIdentifier> result = testIO.requestUserFileSource();
        assertEquals(false, true);
    }

    /**
     * Test of getIdentifierParser method, of class DiskIO.
     */
    @Test
    public void testGetIdentifierParser() {
        System.out.println("getIdentifierParser");
        AbstractIdentifierParser<DiskIdentifier> expResult = null;
        AbstractIdentifierParser<DiskIdentifier> result = testIO.getIdentifierParser();
        assertEquals(expResult, result);
    }
    
}

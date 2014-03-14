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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    static String configID, fileID;
    static List<String> configContent, arrFileContent, newContent1, newContent2;
    static DiskIdentifier testDID;
    
    public DiskIOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        testIO = new DiskIO();
        configID = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "cutLassTest.cfg";
        fileID = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "cutLassTest.arr";
        configContent = new ArrayList<>();
        configContent.add("line 1");
        configContent.add("line 2");
        configContent.add("line 3");
        arrFileContent = new ArrayList<>();
        arrFileContent.add("line a");
        arrFileContent.add("line b");
        arrFileContent.add("line c");
        newContent1 = new ArrayList<>();
        newContent1.add("line 1");
        newContent1.add("line y");
        newContent1.add("line z");
        newContent2 = new ArrayList<>();
        newContent2.add("line a");
        newContent2.add("line 500");
        newContent2.add("line 600");
        testDID = new DiskIdentifier(fileID);
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
        try {
            System.out.println("getConfigurationFile");
            List<String> result = testIO.getConfigurationFile(configID);
            assertEquals(result.get(0), "line 1");
        } catch (AbstractIOException ex) {
            fail("testGetConfigurationFile failed?");
        }
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_List() throws AbstractIOException {
        System.out.println("setConfigurationFile");
        try {
            testIO.setConfigurationFile(configID, newContent1);
            assertEquals(testIO.getConfigurationFile(configID).get(1), "line y");
            testIO.setConfigurationFile(configID, configContent);
            assertEquals(testIO.getConfigurationFile(configID).get(1), "line 2");
        } catch (AbstractIOException ex) {
            fail("testSetConfigurationFile_String_List failed?");
        }
    }

    /**
     * Test of setConfigurationFile method, of class DiskIO.
     */
    @Test
    public void testSetConfigurationFile_String_CharSequence() {
        try {
            System.out.println("setConfigurationFile");
            testIO.setConfigurationFile(configID, newContent2);
            assertEquals(testIO.getConfigurationFile(configID).get(0), "line a");
            testIO.setConfigurationFile(configID, configContent);
            assertEquals(testIO.getConfigurationFile(configID).get(1), "line 2");
        } catch (AbstractIOException ex) {
            fail("testSetConfigurationFile_String_CharSequence failed?");
        }
    }

    /**
     * Test of getUserFile method, of class DiskIO.
     */
    @Test
    public void testGetUserFile() {
        try {
            System.out.println("getUserFile");
            List<String> result = testIO.getUserFile(testDID);
            assertEquals(result.get(0), "line a");
        } catch (AbstractIOException ex) {
           fail("testGetUserFile failed?");
        }
    }


    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_CharSequence() {
        try {
            System.out.println("setUserFile");
            testIO.setUserFile(testDID, newContent1);
            assertEquals(testIO.getUserFile(testDID).get(1), "line y");
            testIO.setUserFile(testDID, arrFileContent);
            assertEquals(testIO.getUserFile(testDID).get(1), "line b");
        } catch (AbstractIOException ex) {
            fail("testSetUserFile_DiskIdentifier_CharSequence failed?");
        }
    }


    /**
     * Test of setUserFile method, of class DiskIO.
     */
    @Test
    public void testSetUserFile_DiskIdentifier_List() {
        try {
            System.out.println("setUserFile");
            testIO.setUserFile(testDID, newContent1);
            assertEquals(testIO.getUserFile(testDID).get(1), "line y");
            testIO.setUserFile(testDID, arrFileContent);
            assertEquals(testIO.getUserFile(testDID).get(1), "line b");
        } catch (AbstractIOException ex) {
            fail("testSetUserFile_DiskIdentifier_List failed?");
        }
    }

    /**
     * Test of requestUserFileDestination method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileDestination() {
        try {
            System.out.println("requestUserFileDestination");
            Option<DiskIdentifier> result = testIO.requestUserFileDestination();
            assertEquals(result.hasData(), true);
            assertEquals(result.getData().toString(), configID);
        } catch (AbstractIOException ex) {
            fail("testRequestUserFileDestination failed?");
        }
    }

    /**
     * Test of requestUserFileSource method, of class DiskIO.
     */
    @Test
    public void testRequestUserFileSource() {
        try {
            System.out.println("requestUserFileSource");
            Option<DiskIdentifier> result = testIO.requestUserFileSource();
            assertEquals(result.hasData(), true);
            assertEquals(result.toString(), fileID);
        } catch (AbstractIOException ex) {
            fail("testRequestUserFileSource failed?");
        }
    }

}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io;

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
public class DiskIdentifierParserTest {
    
    public DiskIdentifierParserTest() {
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
     * Test of parse method, of class DiskIdentifierParser.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String in = "";
        DiskIdentifierParser instance = new DiskIdentifierParser();
        DiskIdentifier expResult = null;
        DiskIdentifier result = instance.parse(in);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

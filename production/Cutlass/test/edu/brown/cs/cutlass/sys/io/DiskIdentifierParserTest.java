/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io;

import edu.brown.cs.cutlass.sys.io.disk.DiskIdentifier;
import edu.brown.cs.cutlass.sys.io.disk.DiskIdentifierParser;
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
     * Tests by making sure the outputted string is what we'd expect.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String in = "/documents";
        DiskIdentifierParser instance = new DiskIdentifierParser();
        DiskIdentifier result = instance.parse(in);
        assertEquals(result.toString(), in);
    }
    
    /**Error Test of parse, makes sure that it complains about null entries.
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void badPathTest1(){
        System.out.println("bad path 1");
        String in = null;
        DiskIdentifierParser instance = new DiskIdentifierParser();
        DiskIdentifier result = instance.parse(in);
    }
    
    
}

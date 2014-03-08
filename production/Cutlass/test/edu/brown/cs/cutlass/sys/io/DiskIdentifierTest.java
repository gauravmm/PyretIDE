/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.io;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
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
public class DiskIdentifierTest {
    
    public DiskIdentifierTest() {
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
     * Test of toString method, of class DiskIdentifier.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        String input = "/somepath/going/here";
        DiskIdentifier instance = new DiskIdentifier(input);

        assertEquals(instance.toString(), input);
        assertEquals(instance.getId(), Paths.get(input));
    }
    /** Tests that the string constructor throws a null pointer error as expected
     * when given a null input (to make sure the parser's parse function has
     * something to catch.)
     */
    @Test(expected = NullPointerException.class)
    public void badconstructorTest1(){
        System.out.println("bad constructor test 1");
        DiskIdentifier instance = new DiskIdentifier((String)null);
    }
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass;

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
public class LaunchStateTest {
    
    public LaunchStateTest() {
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
     * Test of getOpenFiles method, of class LaunchState.
     */
    @Test
    public void testGetOpenFiles() {
        System.out.println("getOpenFiles");
        LaunchState instance = new LaunchState();
        List expResult = null;
        List result = instance.getOpenFiles();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class LaunchState.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        LaunchState instance = new LaunchState();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fromString method, of class LaunchState.
     */
    @Test
    public void testFromString() {
        System.out.println("fromString");
        LaunchState expResult = null;
        //LaunchState result = LaunchState.fromString(null);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toState method, of class LaunchState.
     */
    @Test
    public void testToState() {
        System.out.println("toState");
        LaunchState expResult = null;
        ///LaunchState result = LaunchState.toState(null);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.DiskIdentifier;
import edu.brown.cs.cutlass.sys.io.DiskIdentifierParser;
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
 * @author Gaurav Manek
 */
public class LaunchStateTest {
    static ArrayList<String> inputStrings;
    static LaunchState typicalState;
    public LaunchStateTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
        //create an input list to use as base 'good' input
        inputStrings = new ArrayList<>();
        inputStrings.add("CURRENT_TAB\t1");
        inputStrings.add("          ");
        inputStrings.add("\t\t\t\t\t\t");
        inputStrings.add("#commented line should be ignored");
        inputStrings.add("FILE\t/documents/business");//some paths as well
        inputStrings.add("FILE\t/some/directory/tothis.txt");
        
        typicalState = LaunchState.fromString(inputStrings, new DiskIdentifierParser());
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
        List<DiskIdentifier> result = typicalState.getOpenFiles();
        assertEquals(result.get(0).toString(), "/documents/business");
        assertEquals(result.get(1).toString(), "/some/directory/tothis.txt");
    }

    /**
     * Test of toString method, of class LaunchState.
     */
    @Test
    public void testToString() {
        System.out.println("toString");

        String expResult = "CURRENT_TAB\t1\nFILE\t/documents/business\nFILE\t/some/directory/tothis.txt\n";
        assertEquals(typicalState.toString(), expResult);
    }

    /**
     * Test of fromString method, of class LaunchState.
     * Tests that fromString catches bad tab formats properly
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringError1() {
        System.out.println("fromString bad tab format");
        ArrayList<String> badFormatInput = new ArrayList<String>();
        badFormatInput.add("CURRENT_TAB\t not a number");
        LaunchState badLaunchState = LaunchState.fromString(badFormatInput, new DiskIdentifierParser());
    }
    
    /**
     * Test of fromString method, of class LaunchState.
     * Tests that fromString catches tab-less lines properly
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringError2() {
        System.out.println("fromString file with no second part format");
        ArrayList<String> badFormatInput = new ArrayList<String>();
        badFormatInput.add("FILE");
        LaunchState badLaunchState = LaunchState.fromString(badFormatInput, new DiskIdentifierParser());
    }
    
     /**
     * Test of fromString method, of class LaunchState.
     * Tests that fromString catches lines with only one tab and nothing after it
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringError3() {
        System.out.println("fromString file with tab but no second part");
        ArrayList<String> badFormatInput = new ArrayList<String>();
        badFormatInput.add("FILE\t");
        LaunchState badLaunchState = LaunchState.fromString(badFormatInput, new DiskIdentifierParser());
    }
    
    /**
     * Test of fromString method, of class LaunchState.
     * Tests that fromString catches when the inputted tab is out of range
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringError4() {
        System.out.println("fromString file with tab but no second part");
        ArrayList<String> badFormatInput = new ArrayList<String>();
        badFormatInput.add("FILE\tderp");
        badFormatInput.add("CURRENT_TAB\t9001");
        LaunchState badLaunchState = LaunchState.fromString(badFormatInput, new DiskIdentifierParser());
    }
    
        /**
     * Test of fromString method, of class LaunchState.
     * Tests that fromString catches unknown commands
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFromStringError5() {
        System.out.println("fromString file with tab but no second part");
        ArrayList<String> badFormatInput = new ArrayList<String>();
        badFormatInput.add("Wait this isn't right\tthis is not file or tab");
        LaunchState badLaunchState = LaunchState.fromString(badFormatInput, new DiskIdentifierParser());
    }


    /**
     * Test of toState method, of class LaunchState.
     * Currenlty creates a LaunchState using toState and sees if that produces 
     * the same output string from toString as the LaunchState produced from
     * fromString using inputStrings
     */
    @Test
    public void testToState() {
        ArrayList<DiskIdentifier> state = new ArrayList<DiskIdentifier>();
        state.add(new DiskIdentifier("/documents/business"));
        state.add(new DiskIdentifier("/some/directory/tothis.txt"));
        
        LaunchState result = LaunchState.toState(state, 1);
        LaunchState expResult = LaunchState.fromString(inputStrings, new DiskIdentifierParser());
        System.out.println(result.toString());
        System.out.println(expResult.toString());
        assertEquals(result.toString(), expResult.toString());
    }
    
}

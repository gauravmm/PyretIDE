/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.config;

import java.awt.Color;
import java.awt.Dimension;
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
public class ConfigEngineTest {
    
    public ConfigEngineTest() {
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
     * Test of getBoolean method, of class ConfigEngine.
     */
    @Test
    public void testGetBoolean() {
        System.out.println("getBoolean");
        String key = "";
        ConfigEngine instance = null;
        Boolean expResult = null;
        Boolean result = instance.getBoolean(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInteger method, of class ConfigEngine.
     */
    @Test
    public void testGetInteger() {
        System.out.println("getInteger");
        String key = "";
        ConfigEngine instance = null;
        Integer expResult = null;
        Integer result = instance.getInteger(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDouble method, of class ConfigEngine.
     */
    @Test
    public void testGetDouble() {
        System.out.println("getDouble");
        String key = "";
        ConfigEngine instance = null;
        Double expResult = null;
        Double result = instance.getDouble(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getString method, of class ConfigEngine.
     */
    @Test
    public void testGetString() {
        System.out.println("getString");
        String key = "";
        ConfigEngine instance = null;
        String expResult = "";
        String result = instance.getString(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDimension method, of class ConfigEngine.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        String key = "";
        ConfigEngine instance = null;
        Dimension expResult = null;
        Dimension result = instance.getDimension(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getColor method, of class ConfigEngine.
     */
    @Test
    public void testGetColor() {
        System.out.println("getColor");
        String key = "";
        ConfigEngine instance = null;
        Color expResult = null;
        Color result = instance.getColor(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getList method, of class ConfigEngine.
     */
    @Test
    public void testGetList() {
        System.out.println("getList");
        String key = "";
        ConfigEngine instance = null;
        List<String> expResult = null;
        List<String> result = instance.getList(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBoolean method, of class ConfigEngine.
     */
    @Test
    public void testSetBoolean() {
        System.out.println("setBoolean");
        String key = "";
        Boolean value = null;
        ConfigEngine instance = null;
        instance.setBoolean(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInteger method, of class ConfigEngine.
     */
    @Test
    public void testSetInteger() {
        System.out.println("setInteger");
        String key = "";
        Integer value = null;
        ConfigEngine instance = null;
        instance.setInteger(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDouble method, of class ConfigEngine.
     */
    @Test
    public void testSetDouble() {
        System.out.println("setDouble");
        String key = "";
        Double value = null;
        ConfigEngine instance = null;
        instance.setDouble(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setString method, of class ConfigEngine.
     */
    @Test
    public void testSetString() {
        System.out.println("setString");
        String key = "";
        String value = "";
        ConfigEngine instance = null;
        instance.setString(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDimension method, of class ConfigEngine.
     */
    @Test
    public void testSetDimension() {
        System.out.println("setDimension");
        String key = "";
        Dimension value = null;
        ConfigEngine instance = null;
        instance.setDimension(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setColor method, of class ConfigEngine.
     */
    @Test
    public void testSetColor() {
        System.out.println("setColor");
        String key = "";
        Color value = null;
        ConfigEngine instance = null;
        instance.setColor(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setList method, of class ConfigEngine.
     */
    @Test
    public void testSetList() {
        System.out.println("setList");
        String key = "";
        List<String> value = null;
        ConfigEngine instance = null;
        instance.setList(key, value);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeProperty method, of class ConfigEngine.
     */
    @Test
    public void testRemoveProperty() {
        System.out.println("removeProperty");
        String key = "";
        ConfigEngine instance = null;
        instance.removeProperty(key);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of save method, of class ConfigEngine.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        ConfigEngine instance = null;
        boolean expResult = false;
        boolean result = instance.save();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class ConfigEngine.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ConfigEngine.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.config;

import java.awt.Color;
import java.awt.Dimension;
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
public class ConfigEngineTest {
    
    private final ConfigEngine engine1;
    private final ConfigEngine engine2;
    
    public ConfigEngineTest() {
        List<String> empty = new ArrayList<>();
        engine1 = ConfigEngine.fromString(empty);
        List<String> populated = new ArrayList<>();
        populated.add("truekey=true"); //true boolean
        populated.add("falsekey=false"); //false boolean
        populated.add("invalidboolkey=nottrue"); //bad boolean
        populated.add("magentacolor=Color: #00FF00FF"); //magenta
        populated.add("lemonygreen=Color: #45A69C12"); //I have no idea what color this is
        populated.add("xyz=Dimension: [30,45]"); //Dimension
        populated.add("doubletest=5.0"); //double as double
        populated.add("doubleasint=48"); //double or int as int
        populated.add("imanint=-13"); //an int
        populated.add("imnotanint=99.9999"); //not an int
        populated.add("plainlist={I am a list, I contain, 3 elements}"); //regular List<String>
        populated.add("funlist={I am a list with all sorts of weird\\,,things like \\\n\\\\n\\ ,\\,&^&%(^$&$*&_*)&,♠╘⌡m↕}");
        populated.add("vanillastring=hello, world!");//vanilla string
        engine2 = ConfigEngine.fromString(populated);
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
    public void testSample() {
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
     * Test of fromString method, of class ConfigEngine.
     */
    @Test
    public void testFromString() {
        System.out.println("fromString");
        List<String> cfgData = null;
        List<String> cfgData2 = new ArrayList<>();
        cfgData2.add("key1=value1");
        cfgData2.add("key2=true");
        ConfigEngine result = ConfigEngine.fromString(cfgData);
        ConfigEngine result2 = ConfigEngine.fromString(cfgData2);
        assertFalse(result.equals(result2));
    }

    /**
     * Test of getBoolean method, of class ConfigEngine
     * Testing exception generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetBooleanFromEmpty() {
        boolean bool = engine1.getBoolean("boolkey");
    }
    
    /**
     * Test of getBoolean method, of class ConfigEngine
     * Testing exception generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetBooleanOnllNonboolean() {
        boolean bool = engine2.getBoolean("invalidboolkey");
    }
    
    /**
     * Test of getBoolean method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetBoolean() {
        assertTrue(engine2.getBoolean("truekey"));
        assertFalse(engine2.getBoolean("falsekey"));
    }

    /**
     * Test of getInteger method, of class ConfigEngine
     * Testing exception generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetIntegerFromEmpty() {
        int integ = engine1.getInteger("intkey");
    }
    
    /**
     * Test of getInteger method, of class ConfigEngine
     * Testing exception generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetIntegerOnNoninteger() {
        int integ = engine2.getInteger("imnotanint");
    }
    
    /**
     * Test of getInteger method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetInteger() {
        assertTrue(engine2.getInteger("imanint").intValue() == -13);
        assertTrue(engine2.getInteger("doubleasint").intValue() == 48);
    }
    
    /**
     * Test of getDouble method, of class ConfigEngine
     * Testing exception generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetDoubleFromEmpty() {
        double dbl = engine1.getDouble("doublekey");
    }
    
    /**
     * Test of getDouble method, of class ConfigEngine
     * Testing exception generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetDoubleonnonDouble() {
        double dbl = engine2.getDouble("vanillastring");
    }

    /**
     * Test of getDouble method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetDouble() {
        assertTrue(engine2.getDouble("doubletest").doubleValue() == 5.0);
        assertTrue(engine2.getDouble("doubleasint").doubleValue() == 48.0);
    }

    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetStringFromEmpty() {
        String str = engine1.getString("strkey");
    }
    
    /**
     * Test of getString method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetString() {
        assertEquals(engine2.getString("vanillastring"), "hello, world!");
    }

    /**
     * Test of getDimension method, of class ConfigEngine
     * Testing exception generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetDimensionFromEmpty() {
        Dimension dim = engine1.getDimension("dimension");
    }
    
    /**
     * Test of getDimension method, of class ConfigEngine
     * Testing exception generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetDimensiononnonDimension() {
        Dimension dim = engine2.getDimension("vanillastring");
    }
    
    /**
     * Test of getDimension method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetDimension() {
        assertEquals(engine2.getDimension("xyz"), new Dimension(30, 45));
    }

    /**
     * Test of getColor method, of class ConfigEngine
     * Testing exception generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetColorFromEmpty() {
        Color col = engine1.getColor("color");
    }
    
    /**
     * Test of getColor method, of class ConfigEngine
     * Testing exception generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetColoronnonColor() {
        Color col = engine2.getColor("xyz");
    }
    
    /**
     * Test of getColor method, of class ConfigEngine.
     * Testing expected use
     */
    @Test
    public void testGetColor() {
        assertEquals(engine2.getColor("magentacolor"), new Color(255, 0, 255, 0));
        assertEquals(engine2.getColor("lemonygreen"), new Color(166, 156, 18, 69));
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
     * Test of toString method, of class ConfigEngine.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ConfigEngine instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

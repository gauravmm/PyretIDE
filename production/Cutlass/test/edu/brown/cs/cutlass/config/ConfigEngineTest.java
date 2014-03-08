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
        populated.add("plainlist={I am a list// I contain// 3 elements}"); //regular List<String>
        populated.add("funlist={I am a list with all sorts of weird things// \\nnomore,//,//&(%*&$$&^*}");
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
        String key = "";
        ConfigEngine instance = null;
        Boolean expResult = null;
        // Boolean result = instance.getBoolean(key);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of fromString method, of class ConfigEngine.
     */
    @Test
    public void testFromString() {
        List<String> cfgData = null;
        List<String> cfgData2 = new ArrayList<>();
        cfgData2.add("key1=value1");
        cfgData2.add("key2=true");
        ConfigEngine result = ConfigEngine.fromString(cfgData);
        ConfigEngine result2 = ConfigEngine.fromString(cfgData2);
        assertFalse(result.equals(result2));
    }

    /**
     * Test of getBoolean method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetBooleanFromEmpty() {
        boolean bool = engine1.getBoolean("boolkey");
    }

    /**
     * Test of getBoolean method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetBooleanOnllNonboolean() {
        boolean bool = engine2.getBoolean("invalidboolkey");
    }

    /**
     * Test of getBoolean method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetBoolean() {
        assertTrue(engine2.getBoolean("truekey"));
        assertFalse(engine2.getBoolean("falsekey"));
    }

    /**
     * Test of getInteger method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetIntegerFromEmpty() {
        int integ = engine1.getInteger("intkey");
    }

    /**
     * Test of getInteger method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetIntegerOnNoninteger() {
        int integ = engine2.getInteger("imnotanint");
    }

    /**
     * Test of getInteger method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetInteger() {
        assertTrue(engine2.getInteger("imanint").intValue() == -13);
        assertTrue(engine2.getInteger("doubleasint").intValue() == 48);
    }

    /**
     * Test of getDouble method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetDoubleFromEmpty() {
        double dbl = engine1.getDouble("doublekey");
    }

    /**
     * Test of getDouble method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetDoubleonnonDouble() {
        double dbl = engine2.getDouble("vanillastring");
    }

    /**
     * Test of getDouble method, of class ConfigEngine. Testing expected use
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
     * Test of getString method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetString() {
        assertEquals(engine2.getString("vanillastring"), "hello, world!");
    }

    /**
     * Test of getDimension method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetDimensionFromEmpty() {
        Dimension dim = engine1.getDimension("dimension");
    }

    /**
     * Test of getDimension method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetDimensiononnonDimension() {
        Dimension dim = engine2.getDimension("vanillastring");
    }

    /**
     * Test of getDimension method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetDimension() {
        engine2.getDimension("xyz");
        assertEquals(engine2.getDimension("xyz").toString(), new Dimension(30, 45).toString());
    }

    /**
     * Test of getColor method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetColorFromEmpty() {
        Color col = engine1.getColor("color");
    }

    /**
     * Test of getColor method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetColoronnonColor() {
        Color col = engine2.getColor("xyz");
    }

    /**
     * Test of getColor method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetColor() {
        assertEquals(engine2.getColor("magentacolor"), new Color(255, 0, 255, 0));
        assertEquals(engine2.getColor("lemonygreen"), new Color(166, 156, 18, 69));
    }

    /**
     * Test of getList method, of class ConfigEngine Testing exception
     * generation on nonexistent key
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testGetListFromEmpty() {
        List<String> lst = engine1.getList("list");
    }

    /**
     * Test of getList method, of class ConfigEngine Testing exception
     * generation on bad value data type
     */
    @Test(expected = ConfigTypeException.class)
    public void testGetListonnonList() {
        List<String> lst = engine2.getList("xyz");
    }

    /**
     * Test of getList method, of class ConfigEngine. Testing expected use
     */
    @Test
    public void testGetList() {
        List<String> plainlist = engine2.getList("plainlist");
        List<String> funlist = engine2.getList("funlist");
        assertTrue(plainlist.contains("I am a list"));
        assertTrue(plainlist.contains(" I contain"));
        assertTrue(plainlist.contains(" 3 elements"));
        assertTrue(plainlist.size() == 3);
        assertTrue(funlist.size() == 4);
        assertTrue(funlist.contains("&(%*&$$&^*"));
        assertTrue(funlist.contains(","));
    }

    /**
     * Test of setBoolean method, of class ConfigEngine.
     */
    @Test
    public void testSetBoolean() {
        engine1.setBoolean("myfirstbool", true);
        engine2.setBoolean("truekey", false);
        assertTrue(engine1.getBoolean("myfirstbool"));
        assertFalse(engine2.getBoolean("truekey"));
    }

    /**
     * Test of setInteger method, of class ConfigEngine.
     */
    @Test
    public void testSetInteger() {
        engine1.setInteger("myFirstInteger", Integer.MAX_VALUE);
        engine2.setInteger("imanint", 42);
        engine2.setInteger("imanewkey", -44);
        assertTrue(engine1.getInteger("myFirstInteger").intValue() == Integer.MAX_VALUE);
        assertTrue(engine2.getInteger("imanint").intValue() == 42);
        assertTrue(engine2.getInteger("imanewkey").intValue() == -44);
    }

    /**
     * Test of setDouble method, of class ConfigEngine.
     */
    @Test
    public void testSetDouble() {
        engine1.setDouble("myFirstDouble", -99984515.999999999);
        engine2.setDouble("doubletest", 3.14159);
        engine2.setDouble("e", 2.718281828);
        assertTrue(engine1.getDouble("myFirstDouble").doubleValue() == -99984515.999999999);
        assertTrue(engine2.getDouble("doubletest").doubleValue() == 3.14159);
        assertTrue(engine2.getDouble("e").doubleValue() == 2.718281828);
    }

    /**
     * Test of setString method, of class ConfigEngine.
     */
    @Test
    public void testSetString() {
        engine1.setString("newstring", "imnew");
        assertEquals(engine1.getString("newstring"), "imnew");
        engine2.setString("vanillastring", "\"The, only thing we have to fear is fear itself.\"");
        engine2.setString("speaker", "FDR");
        assertEquals(engine2.getString("speaker"), "FDR");
        assertEquals(engine2.getString("vanillastring"), "\"The, only thing we have to fear is fear itself.\"");
    }

    /**
     * Test of setDimension method, of class ConfigEngine.
     */
    @Test
    public void testSetDimension() {
        engine1.setDimension("thefifthdim", new Dimension(-2, -2));
        engine2.setDimension("xyz", new Dimension(34, 56));
        engine2.setDimension("finalfrontier", new Dimension(2001, 301));
        assertEquals(engine1.getDimension("thefifthdim"), new Dimension(-2, -2));
        assertEquals(engine2.getDimension("xyz"), new Dimension(34, 56));
        assertEquals(engine2.getDimension("finalfrontier"), new Dimension(2001, 301));
        //instance.setDimension(key, value);
        // TODO review the generated test code and remove the default call to fail.
        //    fail("The test case is a prototype.");
    }

    /**
     * Test of setColor method, of class ConfigEngine.
     */
    @Test
    public void testSetColor() {
        engine1.setColor("red", Color.blue);
        engine2.setColor("magentacolor", Color.orange);
        engine2.setColor("purple", Color.yellow);
        assertEquals(engine1.getColor("red"), Color.blue);
        assertEquals(engine2.getColor("magentacolor"), Color.orange);
        assertEquals(engine2.getColor("purple"), Color.yellow);
    }

    /**
     * Test of setList method, of class ConfigEngine.
     */
    @Test
    public void testSetList() {
        List<String> fortesting = new ArrayList<>();
        fortesting.add("I'm a nice easy string");
        fortesting.add("I might, or might not be");
        fortesting.add("COMMAS EVERYWHERE,,,,,,,,!!!!!!!!!!!AK;LSDHLAGSDL7");
        fortesting.add("\tTabby\tMcTabby \nnewlines, are f\tun");
        engine1.setList("test", fortesting);
        List<String> stored = engine1.getList("test");
        assertTrue(stored.contains("COMMAS EVERYWHERE,,,,,,,,!!!!!!!!!!!AK;LSDHLAGSDL7"));
        engine2.setList("plainlist", new ArrayList<String>());
        assertFalse(engine2.getList("plainlist").contains(" I contain"));
    }

    /**
     * Test of removeProperty method, of class ConfigEngine.
     */
    @Test(expected = ConfigKeyNotFoundException.class)
    public void testRemoveProperty() {
        engine2.setColor("red", new Color(2, 4, 6, 8));
        engine2.removeProperty("red");
        engine2.getColor("red"); //throws ConfigKeyNotFound
    }

    /**
     * Test of toString method, of class ConfigEngine.
     */
    @Test
    public void testToString() {
        String eng2 = engine2.toString();
        assertTrue(engine1.toString().contains("#Format: key=value"));
        assertTrue(eng2.contains("#Format: key=value"));
        assertTrue(eng2.contains("truekey=true"));
        assertTrue(eng2.contains("falsekey=false")); 
        assertTrue(eng2.contains("invalidboolkey=nottrue")); 
        assertTrue(eng2.contains("magentacolor=Color\\: \\#00FF00FF")); 
        assertTrue(eng2.contains("lemonygreen=Color\\: \\#45A69C12")); 
        assertTrue(eng2.contains("xyz=Dimension\\: [30,45]"));
        assertTrue(eng2.contains("doubletest=5.0")); 
        assertTrue(eng2.contains("doubleasint=48")); 
        assertTrue(eng2.contains("imanint=-13"));
        assertTrue(eng2.contains("imnotanint=99.9999"));
        assertTrue(eng2.contains("plainlist={I am a list// I contain// 3 elements}")); 
        assertTrue(eng2.contains("funlist={I am a list with all sorts of weird things// \\nnomore,//,//&(%*&$$&^*}"));
        assertTrue(eng2.contains("vanillastring=hello, world\\!"));
    }
    
}

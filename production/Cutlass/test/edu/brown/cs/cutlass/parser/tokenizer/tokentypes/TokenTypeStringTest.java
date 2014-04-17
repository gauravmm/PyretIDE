/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Gaurav Manek
 */
public class TokenTypeStringTest {

    public TokenTypeStringTest() {
    }

    Pattern p = TokenTypeString.getInstance().getPattern();//Pattern.compile("('([^']|([^\\\\]\\'))*')");
    //Pattern p = Pattern.compile("^'([^'\\\\]|((\\\\\\\\)*\\\\')|(\\[^']))*'");

    /**
     * Test of toAggregate method, of class TokenTypeString.
     */
    @Test
    public void testRegex() {
        getMatchSame("'asd'");
        getMatchSame("'as\\'d'");
        getMatchSame("'as\\\\\\'d'");
        getMatchSame("'\\\\\\'d'");
        Assert.assertEquals("'as\\'da'", getMatch("'as\\'da'ff"));
        
        getMatchSame("\"asd\"");
        getMatchSame("\"as\\\"d\"");
        getMatchSame("\"as\\\\\\\"d\"");
        getMatchSame("\"\\\\\\\"d\"");
        Assert.assertEquals("\"as\\\"da\"", getMatch("\"as\\\"da\"ff"));
    }
    
    public String getMatch(String test) {
        //System.out.println(test);
        Matcher m = p.matcher(test);
        if (!m.find()) {
            return null;
        }
        return m.group();
    }
    
    public void getMatchSame(String test){
        Assert.assertEquals(test, getMatch(test));
    }

}

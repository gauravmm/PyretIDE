/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.util.Option;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Gaurav Manek
 */
public class TokenTypeStringTest {

    public TokenTypeStringTest() {
    }

    TokenTypeString p = TokenTypeString.getInstance();

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
        Option<String> m = p.getMatch(test);
        if (m.hasData()) {
            return m.getData();
        } else {
            Assert.fail();
            return null;
        }
    }

    public void getMatchSame(String test) {
        Assert.assertEquals(test, p.getMatch(test).getData());
    }

}

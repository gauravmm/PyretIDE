/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

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
public class TokenParserTest {

    public TokenParserTest() {
    }

    String in = "data BinTree:\n"
            + "  | leaf\n"
            + "  | node(value, left, right)\n"
            + "end\n"
            + "\n"
            + "fun tree-sum(t):\n"
            + "  doc: \"Calculate the sum of node values\"\n"
            + "  cases(BinTree) t:\n"
            + "    | leaf => 0\n"
            + "    | node(v, l, r) =>\n"
            + "      v + tree-sum(l) + tree-sum(r)\n"
            + "  end\n"
            + "where:\n"
            + "  tree-sum(leaf) is 0\n"
            + "  node4 = node(4, leaf, leaf)\n"
            + "  tree-sum(node(5, node4, leaf)) is 9\n"
            + "end";

    /**
     * Test of parseTokens method, of class TokenParser.
     */
    @Test
    public void testParseTokens_String() {
        System.err.println(TokenParser.parseTokens(in).getTokenLines());
    }

}

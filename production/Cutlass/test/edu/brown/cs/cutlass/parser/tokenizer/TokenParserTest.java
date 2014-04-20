/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.PyretFeatureExtractor;
import org.junit.Test;

/**
 *
 * @author Gaurav Manek
 */
public class TokenParserTest {

    public TokenParserTest() {
    }

    public final static String test1 = "data BinTree:\n"
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
            + "end\n"
            + "\n"
            + "\n"
            + "\n"
            + "\n";

    public final static String test2 = "eps = 0.001\n"
            + "fun d-dx(f):\n"
            + "  doc: \"Approximate the derivative of f\"\n"
            + "  fun(x): (f(x + eps) - f(x)) / eps;\n"
            + "where:\n"
            + "  fun square(x): x * x;\n"
            + "  fun within(delta, target):\n"
            + "    fun(actual): (actual - target).abs() < delta;;\n"
            + "\n"
            + "  dsquare = d-dx(square)\n"
            + "\n"
            + "  dsquare(5) satisfies within(0.1, 10)\n"
            + "  dsquare(10) satisfies within(0.1, 20)\n"
            + "end";

    /**
     * Test of parseTokens method, of class TokenParser.
     */
    @Test
    public void testParseTokens_String() {
        TokenParserOutput tokens = TokenParser.parseTokens(test1);
        System.out.println(PyretFeatureExtractor.extract(tokens).functionCallGraphTo);
    }

}

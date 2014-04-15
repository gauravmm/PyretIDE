/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Gaurav Manek
 */
public class TokenParserOutput {
    private final List<Line> tokenLines;
    private final Map<TokenType, Map<String, List<Token>>> tokenCollected;

    TokenParserOutput(List<Line> tokenLines, Map<TokenType, Map<String, List<Token>>> tokenCollected) {
        this.tokenLines = tokenLines;
        this.tokenCollected = tokenCollected;
    }
}

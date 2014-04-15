/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenTypes {

    private static final List<TokenType> types = Arrays.asList(
            TokenTypePairedOpenBrace.getInstance(),
            TokenTypePairedCloseBrace.getInstance()
    );

    public static List<TokenType> getTypes() {
        return types;
    }

    public static TokenType getWhitespaceTokenType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

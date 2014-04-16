/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenTypePaired extends TokenType {

    public TokenTypePaired(Pattern pattern) {
        super(pattern);
    }

}

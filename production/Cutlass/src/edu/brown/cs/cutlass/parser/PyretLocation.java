/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.Token;

/**
 *
 * @author Gaurav Manek
 */
public class PyretLocation {

    /**
     * The number of characters from the start of file that this location points
     * to.
     */
    public final Token token;

    public PyretLocation(Token token) {
        this.token = token;
    }
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class ParsingError {
    private final Token token;
    private final String errorString;

    public ParsingError(Token token, String errorString) {
        this.token = token;
        this.errorString = errorString;
    }

    public Token getToken() {
        return token;
    }

    public String getErrorString() {
        return errorString;
    }
    
}

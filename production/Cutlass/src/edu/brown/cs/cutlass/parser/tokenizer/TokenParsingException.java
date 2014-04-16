/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class TokenParsingException extends RuntimeException {

    public TokenParsingException() {
    }

    public TokenParsingException(String message) {
        super(message);
    }

    public TokenParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenParsingException(Throwable cause) {
        super(cause);
    }
    
}

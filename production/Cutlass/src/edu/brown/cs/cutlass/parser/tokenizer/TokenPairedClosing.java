/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class TokenPairedClosing extends TokenPaired {

    public TokenPairedClosing(String value, int length, int offset, TokenScope scope, TokenTypePairedClose type) {
        super(value, length, offset, scope, type);
    }
    
}

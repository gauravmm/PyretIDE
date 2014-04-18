/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class TokenPaired extends Token {

    public TokenPaired other;
    
    public TokenPaired(String value, int length, int offset, TokenTypePaired type) {
        super(value, length, offset, type);
    }    
    
}

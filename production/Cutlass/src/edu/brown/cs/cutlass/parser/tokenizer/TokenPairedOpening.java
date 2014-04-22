/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class TokenPairedOpening extends TokenPaired {

    public TokenPairedOpening(String value, int length, int offset, TokenScope scope, TokenTypePairedOpen type) {
        super(value, length, offset, scope, type);
    }

    @Override
    public TokenTypePairedOpen getType() {
        return (TokenTypePairedOpen) super.getType();
    }
    
}

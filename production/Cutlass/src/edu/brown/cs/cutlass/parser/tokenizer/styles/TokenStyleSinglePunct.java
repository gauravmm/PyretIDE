/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleSinglePunct extends TokenStyleColor {

    private static final TokenStyleSinglePunct instance = new TokenStyleSinglePunct();
    
    public static TokenStyleSinglePunct getInstance(){
        return instance;
    }
    
    private TokenStyleSinglePunct() {
        super(Color.BLUE);
    }
    
}

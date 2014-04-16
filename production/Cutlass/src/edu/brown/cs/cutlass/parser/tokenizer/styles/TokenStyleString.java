/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleString extends TokenStyleColor {

    private static final TokenStyleString instance = new TokenStyleString();
    
    public static TokenStyleString getInstance(){
        return instance;
    }
    
    private TokenStyleString() {
        super(Color.ORANGE);
    }
    
}

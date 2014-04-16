/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleDoublePunct extends TokenStyleColor {

    private static final TokenStyleDoublePunct instance = new TokenStyleDoublePunct();
    
    public static TokenStyleDoublePunct getInstance(){
        return instance;
    }
    
    private TokenStyleDoublePunct() {
        super(Color.GRAY);
    }
    
}

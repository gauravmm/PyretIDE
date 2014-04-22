/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStylePunct extends TokenStyleColor {

    private static final TokenStylePunct instance = new TokenStylePunct();
    
    public static TokenStylePunct getInstance(){
        return instance;
    }
    
    private TokenStylePunct() {
        super(new Color(51,01,70).brighter());
    }
    
}

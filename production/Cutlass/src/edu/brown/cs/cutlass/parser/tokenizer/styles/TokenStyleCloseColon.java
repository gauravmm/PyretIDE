/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleCloseColon extends TokenStyleColor {

    private static final TokenStyleCloseColon instance = new TokenStyleCloseColon();
    
    public static TokenStyleCloseColon getInstance(){
        return instance;
    }
    
    private TokenStyleCloseColon() {
        super(Color.BLUE);
    }
    
}

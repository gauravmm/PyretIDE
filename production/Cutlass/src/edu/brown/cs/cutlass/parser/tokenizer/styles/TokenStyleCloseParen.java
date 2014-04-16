/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleCloseParen extends TokenStyleColor {

    private static final TokenStyleCloseParen instance = new TokenStyleCloseParen();
    
    public static TokenStyleCloseParen getInstance(){
        return instance;
    }
    
    private TokenStyleCloseParen() {
        super(Color.BLUE);
    }
    
}

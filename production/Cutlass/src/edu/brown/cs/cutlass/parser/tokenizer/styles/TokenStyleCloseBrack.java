/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleCloseBrack extends TokenStyleColor {

    private static final TokenStyleCloseBrack instance = new TokenStyleCloseBrack();
    
    public static TokenStyleCloseBrack getInstance(){
        return instance;
    }
    
    private TokenStyleCloseBrack() {
        super(Color.BLUE);
    }
    
}

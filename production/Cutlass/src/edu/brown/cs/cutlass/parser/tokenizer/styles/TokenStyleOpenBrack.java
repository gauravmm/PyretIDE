/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleOpenBrack extends TokenStyleColor {

    private static final TokenStyleOpenBrack instance = new TokenStyleOpenBrack();
    
    public static TokenStyleOpenBrack getInstance(){
        return instance;
    }
    
    private TokenStyleOpenBrack() {
        super(Color.BLUE);
    }
    
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleCloseBrace extends TokenStyleColor {

    private static final TokenStyleCloseBrace instance = new TokenStyleCloseBrace();
    
    public static TokenStyleCloseBrace getInstance(){
        return instance;
    }
    
    private TokenStyleCloseBrace() {
        super(Color.BLUE);
    }
    
}

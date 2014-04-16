/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleOpenParen extends TokenStyleColor {

    private static final TokenStyleOpenParen instance = new TokenStyleOpenParen();
    
    public static TokenStyleOpenParen getInstance(){
        return instance;
    }
    
    private TokenStyleOpenParen() {
        super(Color.BLUE);
    }
    
}

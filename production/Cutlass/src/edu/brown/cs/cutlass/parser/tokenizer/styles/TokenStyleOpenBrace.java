/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleOpenBrace extends TokenStyleColor {

    private static final TokenStyleOpenBrace instance = new TokenStyleOpenBrace();
    
    public static TokenStyleOpenBrace getInstance(){
        return instance;
    }
    
    private TokenStyleOpenBrace() {
        super(Color.BLUE);
    }
    
}

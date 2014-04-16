/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleKeyword extends TokenStyleColor {

    private static final TokenStyleKeyword instance = new TokenStyleKeyword();
    
    public static TokenStyleKeyword getInstance(){
        return instance;
    }
    
    private TokenStyleKeyword() {
        super(Color.MAGENTA);
    }
    
}

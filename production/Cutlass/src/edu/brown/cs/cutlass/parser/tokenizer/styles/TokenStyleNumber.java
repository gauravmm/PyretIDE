/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleNumber extends TokenStyleColor {

    private static final TokenStyleNumber instance = new TokenStyleNumber();
    
    public static TokenStyleNumber getInstance(){
        return instance;
    }
    
    private TokenStyleNumber() {
        super(Color.GREEN);
    }
    
}

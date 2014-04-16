/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleOpenColon extends TokenStyleColor {

    private static final TokenStyleOpenColon instance = new TokenStyleOpenColon();
    
    public static TokenStyleOpenColon getInstance(){
        return instance;
    }
    
    private TokenStyleOpenColon() {
        super(Color.GRAY);
    }
    
}

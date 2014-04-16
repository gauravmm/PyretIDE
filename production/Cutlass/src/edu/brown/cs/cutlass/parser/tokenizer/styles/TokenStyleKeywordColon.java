/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleKeywordColon extends TokenStyleColor {

    private static final TokenStyleKeywordColon instance = new TokenStyleKeywordColon();
    
    public static TokenStyleKeywordColon getInstance(){
        return instance;
    }
    
    private TokenStyleKeywordColon() {
        super(Color.MAGENTA);
    }
    
}

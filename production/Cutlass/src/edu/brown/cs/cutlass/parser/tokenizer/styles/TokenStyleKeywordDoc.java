/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleKeywordDoc extends TokenStyleColor {

    private static final TokenStyleKeywordDoc instance = new TokenStyleKeywordDoc();
    
    public static TokenStyleKeywordDoc getInstance(){
        return instance;
    }
    
    private TokenStyleKeywordDoc() {
        super(Color.MAGENTA);
    }
    
}

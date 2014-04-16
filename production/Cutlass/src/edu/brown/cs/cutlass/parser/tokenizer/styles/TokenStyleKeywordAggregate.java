/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleKeywordAggregate extends TokenStyleColor {

    private static final TokenStyleKeywordAggregate instance = new TokenStyleKeywordAggregate();
    
    public static TokenStyleKeywordAggregate getInstance(){
        return instance;
    }
    
    private TokenStyleKeywordAggregate() {
        super(Color.MAGENTA);
    }
    
}

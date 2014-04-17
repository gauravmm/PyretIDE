/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStylePaired extends TokenStyleBackground {

    private static final TokenStylePaired instance = new TokenStylePaired();
    
    public static TokenStylePaired getInstance(){
        return instance;
    }
    
    private TokenStylePaired() {
        super(Color.YELLOW);
    }
    
}

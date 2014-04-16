/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleComment extends TokenStyleColor {

    private static final TokenStyleComment instance = new TokenStyleComment();
    
    public static TokenStyleComment getInstance(){
        return instance;
    }
    
    private TokenStyleComment() {
        super(Color.BLUE);
    }
    
}

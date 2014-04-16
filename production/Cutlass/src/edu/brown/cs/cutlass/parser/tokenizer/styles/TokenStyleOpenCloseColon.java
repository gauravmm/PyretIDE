/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleOpenCloseColon extends TokenStyleColor {

    private static final TokenStyleOpenCloseColon instance = new TokenStyleOpenCloseColon();
    
    public static TokenStyleOpenCloseColon getInstance(){
        return instance;
    }
    
    private TokenStyleOpenCloseColon() {
        super(Color.GRAY);
    }
    
}

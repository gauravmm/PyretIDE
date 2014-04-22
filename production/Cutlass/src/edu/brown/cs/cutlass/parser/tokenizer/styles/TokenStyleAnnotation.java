/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;


public class TokenStyleAnnotation extends TokenStyleColor {

    private static final TokenStyleAnnotation instance = new TokenStyleAnnotation();
    
    public static TokenStyleAnnotation getInstance(){
        return instance;
    }
    
    private TokenStyleAnnotation() {
        super(new Color(160,80,0).brighter());
    }
    
}

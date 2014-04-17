/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;


public class TokenStyleError extends TokenStyleColor {

    private static final TokenStyleError instance = new TokenStyleError();
    
    public static TokenStyleError getInstance(){
        return instance;
    }
    
    private TokenStyleError() {
        super(new Color(160,61,57));
    }

    @Override
    protected void setStyle(Style s) {
        super.setStyle(s); 
        StyleConstants.setUnderline(s,true);
    }
    
    
    
}

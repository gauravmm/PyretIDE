/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.awt.Color;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenStyleBackground extends TokenStyle {

    private final Color color;

    public TokenStyleBackground(Color color) {
        super();
        this.color = color;
    }
    
    @Override
    protected void setStyle(Style s) {
        StyleConstants.setBackground(s, color);
    }
    
}

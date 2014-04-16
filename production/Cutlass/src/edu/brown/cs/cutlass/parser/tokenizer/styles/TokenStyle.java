/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.styles;

import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenStyle {

    private final String styleName;
    private final Color styleColor;

    public TokenStyle() {
        this.styleName = this.getClass().getCanonicalName();
    }

    public final void applyTo(StyledDocument sD) {
        setStyle(sD.addStyle(styleName, null));
        StyleConstants.setForeground(....,styleColor)
    }
    
    public final String getName(){
        return styleName;
    }

    protected abstract void setStyle(Style s);
}

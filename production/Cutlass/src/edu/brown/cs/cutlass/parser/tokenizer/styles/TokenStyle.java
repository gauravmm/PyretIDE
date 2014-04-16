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
    private Style style = null;

    public TokenStyle() {
        this.styleName = this.getClass().getCanonicalName();
    }

    public final void applyTo(StyledDocument sD) {
        style = sD.addStyle(styleName, null);
        setStyle(style);
    }
    
    public final String getName(){
        return styleName;
    }
    
    public final Style getStyle(){
        return style;
    }

    protected abstract void setStyle(Style s);
}

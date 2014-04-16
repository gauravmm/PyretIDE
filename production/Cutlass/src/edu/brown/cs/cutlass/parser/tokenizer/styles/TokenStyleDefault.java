/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.styles;

import javax.swing.text.Style;

public class TokenStyleDefault extends TokenStyle {

    private static final TokenStyleDefault instance = new TokenStyleDefault();

    public static TokenStyleDefault getInstance() {
        return instance;
    }

    private TokenStyleDefault() {
    }

    @Override
    protected void setStyle(Style s) {
    }

}

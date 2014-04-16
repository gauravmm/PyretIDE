/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.styles;

import javax.swing.text.Style;

public class TokenStyleWhitespace extends TokenStyle {

    private static final TokenStyleWhitespace instance = new TokenStyleWhitespace();

    public static TokenStyleWhitespace getInstance() {
        return instance;
    }

    private TokenStyleWhitespace() {
    }

    @Override
    protected void setStyle(Style s) {
    }

}

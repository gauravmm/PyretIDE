/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;

/**
 *
 * @author Gaurav Manek
 */
public class Token {

    private final String value;
    private final int length;
    private final int offset;
    private final TokenType type;
    
    public Token next = null;
    public Token previous = null;
    private TokenStyle style = TokenStyles.getDefaultStyle();
    
    public Token(String value, int length, int offset, TokenType type) {
        this.value = value;
        this.length = length;
        this.offset = offset;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }

    public TokenType getType() {
        return type;
    }

    
    public TokenStyle getStyle() {
        return style;
    }
    
    public void setStyle(TokenStyle style) {
        this.style = style;
    }

    public Token getNextToken() {
        return next;
    }

    public void setNextToken(Token next) {
        this.next = next;
    }

    public Token getPreviousToken() {
        return previous;
    }

    public void setPreviousToken(Token previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "(" + type.getClass().getSimpleName() + " " + offset + " " + value + ')';
    }
}

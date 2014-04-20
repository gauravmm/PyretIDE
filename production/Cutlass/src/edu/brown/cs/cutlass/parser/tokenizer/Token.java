/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;
import java.util.Objects;

/**
 *
 * @author Gaurav Manek
 */
public class Token implements Comparable<Integer> {

    private final String value;
    private final int length;
    private final int offset;
    private final TokenType type;
    private final TokenScope scope;

    private Token next = null;
    private Token previous = null;
    private TokenStyle style = TokenStyles.getDefaultStyle();

    @Deprecated
    private Token(String value, int offset, int length, TokenType type) {
        this(value, offset, length, new TokenScope(), type);
    }

    public Token(String value, int offset, int length, TokenScope scope, TokenType type) {
        this.value = value;
        this.length = length;
        this.offset = offset;
        this.scope = scope;
        this.type = type;
        if (type.getStyle() != null) {
            style = type.getStyle();
        }
        type.applyStyle(this);
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

    public TokenScope getScope() {
        return scope;
    }
    
    // Type:
    public TokenStyle getTokenStyle() {
        return style;
    }

    public void setStyle(TokenStyle style) {
        this.style = style;
    }

    // Linked List Implementation:
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

    public boolean hasNextToken() {
        return next != null;
    }

    public boolean hasPreviousToken() {
        return next != null;
    }

    // Debug:
    @Override
    public String toString() {
        return "(" + type.getClass().getSimpleName() + " " + offset + " " + value + ')';
    }

    @Override
    public int compareTo(Integer o) {
        if (o < offset) {
            return 1;
        } else if (o >= offset + length) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.value);
        hash = 97 * hash + this.length;
        hash = 97 * hash + this.offset;
        hash = 97 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (this.length != other.length) {
            return false;
        }
        if (this.offset != other.offset) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }


}

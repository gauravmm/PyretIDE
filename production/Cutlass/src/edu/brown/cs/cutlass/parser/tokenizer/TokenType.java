/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeComment;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenType {

    /*
     Whitespace (but not newlines) --
     Keywords, --
     Keywords with colons --
     colon : --
     open and close {}[]() --
     Punctuation --
     Initial Operators --
     Whitespace --
     Strings (with ' and " quotes) --
     Comments --
     Default -- check regex
     Null/Illegal token type
     */
    public TokenType(Pattern pattern) {
        if (!pattern.pattern().startsWith("^")) {
            throw new IllegalArgumentException("All patterns must be anchored to the start of string.");
        }

        this.pattern = pattern;
    }

    // NOTE: pattern must not 
    private final Pattern pattern;

    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Construct a token from the given matching string.
     *
     * @param value The string that matches the pattern.
     * @param offset The offset at which the token starts
     * @param length The length of the token.
     * @param scope The current scope of the token.
     * @return A Token containing the given contents.
     */
    //public abstract Token constructToken(String value, int offset, int length);
    public Token constructToken(String value, int offset, int length, TokenScope scope) {
        //return new Token(value, offset, length, scope, getTokenType());
        return new Token(value, offset, length, scope, this);
    }
    
    //protected abstract TokenType getTokenType(); 

    /**
     *
     * @return true, if tokens of this type should be collected by the parser
     * for later processing. false otherwise;
     */
    public boolean toAggregate() {
        return false;
    }

    public List<TokenType> expectedFollowingTokens() {
        return Collections.emptyList();
    }

    public boolean ignoreForExpectedToken() {
        return false;
    }

    @Override
    @Deprecated
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Use pointer equality (==) to check for equality.");
    }

    public TokenStyle getStyle() {
        return null; // Default Style
    }

    public void applyStyle(Token t) {
    }

}

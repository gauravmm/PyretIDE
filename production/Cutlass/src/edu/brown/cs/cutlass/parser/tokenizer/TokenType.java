/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.util.Option;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenType {

    public TokenType(Pattern pattern) {
        if (!pattern.pattern().startsWith("^")) {
            throw new IllegalArgumentException("All patterns must be anchored to the start of string.");
        }

        this.pattern = pattern;
    }

    protected TokenType() {
        this.pattern = Pattern.compile("^$");
    }

    private final Pattern pattern;

    protected Pattern getPattern() {
        return pattern;
    }

    public Option<String> getMatch(String inLine) {
        Matcher m = getPattern().matcher(inLine);
        // If this pattern matches:
        if (m.find()) {
            if (m.start() != 0) {
                throw new IllegalStateException("The token parsed does not start at position 0." + this.getClass().getName() + " \"" + inLine + "\"");
            } else if (m.end() == 0) {
                throw new IllegalStateException("The token parsed has length 0. " + this.getClass().getName() + " \"" + inLine + "\"");
            }
            int size = getMatchLength(m);
            return new Option<>(inLine.substring(0, size));
        }
        return new Option<>();
    }
    
    protected int getMatchLength(Matcher m) {
        return m.end();
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
        return this == obj;
    }

    public TokenStyle getStyle() {
        return null; // Default Style
    }

    public void applyStyle(Token t) {
    }

}

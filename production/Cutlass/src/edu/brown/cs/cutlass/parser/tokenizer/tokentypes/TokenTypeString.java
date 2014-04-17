/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleString;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeString extends TokenType {

    public static TokenTypeString getInstance() {
        return instance;
    }

    private static final TokenTypeString instance = new TokenTypeString();

    private static String getRegex(String q) {
        return "(" + q + "([^" + q + "\\\\]|((\\\\\\\\)*\\\\" + q + ")|(\\\\[^']))*" + q + ")";
    }

    private TokenTypeString() {
        super(Pattern.compile("^(" + getRegex("\"") + "|" + getRegex("'") + ")"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeString.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleString.getInstance();
    }
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
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
        System.out.println(this.getPattern().pattern());
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeString.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }

}

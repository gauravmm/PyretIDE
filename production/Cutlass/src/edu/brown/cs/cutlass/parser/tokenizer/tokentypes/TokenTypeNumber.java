/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleNumber;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeNumber extends TokenType {

    public static TokenTypeNumber getInstance() {
        return instance;
    }

    private static final TokenTypeNumber instance = new TokenTypeNumber();

    private TokenTypeNumber() {
        super(Pattern.compile("^[0-9]+(\\.[0-9]+)?"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeNumber.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleNumber.getInstance();
    }

}

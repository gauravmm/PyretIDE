/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedClosing;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedClose;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePunct;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public class TokenTypePairedCloseBrace extends TokenTypePairedClose {

    public static TokenTypePairedCloseBrace getInstance() {
        return instance;
    }

    private static final TokenTypePairedCloseBrace instance = new TokenTypePairedCloseBrace();

    private TokenTypePairedCloseBrace() {
        super(Pattern.compile("^\\}"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedClosing(value, offset, length, TokenTypePairedCloseBrace.getInstance());
    }

    @Override
    public boolean isMatchingTokenType(TokenTypePairedOpen t) throws IllegalArgumentException {
        return t instanceof TokenTypePairedOpenBrace;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

}

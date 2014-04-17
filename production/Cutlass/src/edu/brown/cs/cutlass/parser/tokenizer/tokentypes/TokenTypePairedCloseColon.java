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
 * @author dilip
 */
public class TokenTypePairedCloseColon extends TokenTypePairedClose {

    public static TokenTypePairedCloseColon getInstance() {
        return instance;
    }

    private static final TokenTypePairedCloseColon instance = new TokenTypePairedCloseColon();

    private TokenTypePairedCloseColon() {
        super(Pattern.compile("^((;)|(end\b))"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedClosing(value, offset, length, TokenTypePairedCloseColon.getInstance());
    }

    @Override
    public boolean isMatchingTokenType(TokenTypePairedOpen t) throws IllegalArgumentException {
        return t instanceof TokenTypePairedOpenColon;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

}

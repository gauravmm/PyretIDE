/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedClosing;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedClose;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleOpenCloseColon;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypePairedOpenCloseColon extends TokenTypePairedClose {

    public static TokenTypePairedOpenCloseColon getInstance() {
        return instance;
    }

    private static final TokenTypePairedOpenCloseColon instance = new TokenTypePairedOpenCloseColon();

    private TokenTypePairedOpenCloseColon() {
        super(Pattern.compile("^((where)|(sharing)|(with))"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedClosing(value, offset, length, TokenTypePairedOpenCloseColon.getInstance());
    }

    @Override
    public List<TokenType> expectedFollowingTokens() {
        LinkedList<TokenType> rv = new LinkedList<>();
        rv.add(TokenTypePairedOpenColon.getInstance());
        return rv;
    }

    @Override
    public boolean isMatchingTokenType(TokenTypePairedOpen t) throws IllegalArgumentException {
        return t instanceof TokenTypePairedOpenColon;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleOpenCloseColon.getInstance();
    }

}

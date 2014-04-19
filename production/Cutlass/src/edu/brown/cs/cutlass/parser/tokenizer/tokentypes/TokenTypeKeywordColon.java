/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleKeyword;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeKeywordColon extends TokenType {

    public static TokenTypeKeywordColon getInstance() {
        return instance;
    }

    private static final TokenTypeKeywordColon instance = new TokenTypeKeywordColon();

    private TokenTypeKeywordColon() {
        super(Pattern.compile("^((try)|(ask)|(otherwise)|(then)|(with)|(sharing)|(where)|(check)|(graph)|(block))"));
    }

    @Override
    public List<TokenType> expectedFollowingTokens() {
        LinkedList<TokenType> rv = new LinkedList<>();
        rv.add(TokenTypePairedOpenColon.getInstance());
        return rv;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleKeyword.getInstance();
    }

}

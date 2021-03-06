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
public class TokenTypeKeywordDoc extends TokenType {

    public static TokenTypeKeywordDoc getInstance() {
        return instance;
    }

    private static final TokenTypeKeywordDoc instance = new TokenTypeKeywordDoc();

    private TokenTypeKeywordDoc() {
        super(Pattern.compile("^doc(\\s)*:"));
    }

    @Override
    public List<TokenType> expectedFollowingTokens() {
        LinkedList<TokenType> rv = new LinkedList<>();
        rv.add(TokenTypeString.getInstance());
        return rv;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleKeyword.getInstance();
    }

}

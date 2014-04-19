/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleKeyword;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeKeywordAggregate extends TokenType {

    public static TokenTypeKeywordAggregate getInstance() {
        return instance;
    }

    private static final TokenTypeKeywordAggregate instance = new TokenTypeKeywordAggregate();

    private TokenTypeKeywordAggregate() {
        super(Pattern.compile("^((fun)|(data))"));
    }

    @Override
    public boolean toAggregate() {
        return true;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleKeyword.getInstance();
    }

}

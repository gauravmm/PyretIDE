/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleKeyword;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeKeyword extends TokenType {

    public static TokenTypeKeyword getInstance() {
        return instance;
    }

    private static final TokenTypeKeyword instance = new TokenTypeKeyword();

    private TokenTypeKeyword() {
        super(Pattern.compile("^((method\b)|(var\b)|(when\b)|(import\b)|(provide\b)|(except\b)|(for\b)|(from\b)|(and\b)|(or\b)|(not\b)|(as\b)|(if\b)|(else\b)|(cases\b))"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeKeyword.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleKeyword.getInstance();
    }

}

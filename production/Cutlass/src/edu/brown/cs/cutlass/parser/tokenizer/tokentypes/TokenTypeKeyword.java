/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleKeyword;
import java.util.regex.Matcher;
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
        super(Pattern.compile("^((method)|(var)|(when)|(import)|(provide)|(except)|(for)|(from)|(and)|(or)|(not)|(as)|(if)|(cases)|(is))" + TokenTypeDefault.nonWordCharRegex));
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleKeyword.getInstance();
    }

    @Override
    protected int getMatchLength(Matcher m) {
        return m.end(1);
    }

}

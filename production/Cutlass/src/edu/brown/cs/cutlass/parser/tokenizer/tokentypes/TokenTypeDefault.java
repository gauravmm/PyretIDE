/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleDefault;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeDefault extends TokenType {

    public static final String wordCharRegex = "[a-zA-Z0-9\\-_]";
    public static final String nonWordCharRegex = "([^a-zA-Z0-9\\-_]|$)";
    
    public static TokenTypeDefault getInstance() {
        return instance;
    }

    private static final TokenTypeDefault instance = new TokenTypeDefault();
    
    private TokenTypeDefault() {
        super(Pattern.compile("^([a-zA-Z]" + wordCharRegex + "*)"));
    }

    @Override
    public boolean toAggregate() {
        return true;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleDefault.getInstance();
    }

}

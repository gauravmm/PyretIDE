/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleAnnotation;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeAnnotation extends TokenType {

    public static TokenTypeAnnotation getInstance() {
        return instance;
    }

    private static final TokenTypeAnnotation instance = new TokenTypeAnnotation();

    private TokenTypeAnnotation() {
        super(Pattern.compile("^((::)|(->))"));
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleAnnotation.getInstance();
    }

    @Override
    public boolean toAggregate() {
        return true;
    }

}

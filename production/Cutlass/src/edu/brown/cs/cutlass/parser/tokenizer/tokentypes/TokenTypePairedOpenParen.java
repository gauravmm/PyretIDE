/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenScope;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePunct;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypePairedOpenParen extends TokenTypePairedOpen {

    public static TokenTypePairedOpenParen getInstance() {
        return instance;
    }

    private static final TokenTypePairedOpenParen instance = new TokenTypePairedOpenParen();

    private TokenTypePairedOpenParen() {
        super(Pattern.compile("^\\("));
    }

    @Override
    public Token constructToken(String value, int offset, int length, TokenScope scope) {
        return new TokenPairedOpening(value, offset, length, scope, TokenTypePairedOpenParen.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

}

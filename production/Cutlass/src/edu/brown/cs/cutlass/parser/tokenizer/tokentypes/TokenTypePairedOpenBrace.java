/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleOpenBrace;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public class TokenTypePairedOpenBrace extends TokenTypePairedOpen {

    public static TokenTypePairedOpenBrace getInstance() {
        return instance;
    }

    private static final TokenTypePairedOpenBrace instance = new TokenTypePairedOpenBrace();

    private TokenTypePairedOpenBrace() {
        super(Pattern.compile("^\\{"));
    }

    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedOpening(value, offset, length, TokenTypePairedOpenBrace.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleOpenBrace.getInstance();
    }

}

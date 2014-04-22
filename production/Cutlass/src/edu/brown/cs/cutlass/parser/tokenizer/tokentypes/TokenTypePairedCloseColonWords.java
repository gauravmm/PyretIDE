/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedClosing;
import edu.brown.cs.cutlass.parser.tokenizer.TokenScope;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedClose;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePunct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypePairedCloseColonWords extends TokenTypePairedClose {

    public static TokenTypePairedCloseColonWords getInstance() {
        return instance;
    }

    private static final TokenTypePairedCloseColonWords instance = new TokenTypePairedCloseColonWords();

    private TokenTypePairedCloseColonWords() {
        super(Pattern.compile("^((end)|(else))" + TokenTypeDefault.nonWordCharRegex));
    }
    
    @Override
    protected int getMatchLength(Matcher m) {
        return m.end(1);
    }

    @Override
    public Token constructToken(String value, int offset, int length, TokenScope scope) {
        return new TokenPairedClosing(value, offset, length, scope, TokenTypePairedCloseColonWords.getInstance());
    }

    @Override
    public boolean isMatchingTokenType(TokenTypePairedOpen t) throws IllegalArgumentException {
        return t instanceof TokenTypePairedOpenColon;
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

}

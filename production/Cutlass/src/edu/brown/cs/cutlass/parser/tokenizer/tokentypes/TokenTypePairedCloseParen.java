/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedClosing;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedClose;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypePairedCloseParen extends TokenTypePairedClose {

    public static TokenTypePairedCloseParen getInstance(){
        return instance;
    }
    
    private static final TokenTypePairedCloseParen instance = new TokenTypePairedCloseParen();
    
    private TokenTypePairedCloseParen(){
        super(Pattern.compile("^))"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new TokenPairedClosing(value, offset, length, TokenTypePairedCloseParen.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }

    @Override
    public TokenTypePairedOpen getMatchingTokenType() throws IllegalArgumentException {
        return TokenTypePairedOpenBrace.getInstance();
    }
    
}

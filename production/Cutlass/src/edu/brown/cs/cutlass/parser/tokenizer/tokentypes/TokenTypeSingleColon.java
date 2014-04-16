/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeSingleColon extends TokenTypePairedOpen {

    public static TokenTypeSingleColon getInstance(){
        return instance;
    }
    
    private static final TokenTypeSingleColon instance = new TokenTypeSingleColon();
    
    private TokenTypeSingleColon(){
        super(Pattern.compile("^\\:[^\\:]"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeSingleColon.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

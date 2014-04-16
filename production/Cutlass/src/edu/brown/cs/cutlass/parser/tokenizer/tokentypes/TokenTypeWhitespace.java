/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeWhitespace extends TokenType {

    public static TokenTypeWhitespace getInstance(){
        return instance;
    }
    
    private static final TokenTypeWhitespace instance = new TokenTypeWhitespace();
    
    private TokenTypeWhitespace(){
        super(Pattern.compile("^\\s+"));
    }
    
    @Override
    public boolean ignoreForExpectedToken() {
        return true;
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeWhitespace.getInstance());
    }
    
}

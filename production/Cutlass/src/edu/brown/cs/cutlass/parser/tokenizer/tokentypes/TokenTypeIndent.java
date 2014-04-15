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
public class TokenTypeIndent extends TokenType {

    public static TokenTypeIndent getInstance(){
        return instance;
    }
    
    private static final TokenTypeIndent instance = new TokenTypeIndent();
    
    private TokenTypeIndent(){
        super(Pattern.compile("^[a-zA-Z_][a-zA-Z0-9$_\\-]*"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeIndent.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

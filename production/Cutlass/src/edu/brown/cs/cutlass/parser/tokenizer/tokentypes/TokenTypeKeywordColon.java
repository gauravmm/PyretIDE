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
public class TokenTypeKeywordColon extends TokenType {

    public static TokenTypeKeywordColon getInstance(){
        return instance;
    }
    
    private static final TokenTypeKeywordColon instance = new TokenTypeKeywordColon();
    
    private TokenTypeKeywordColon(){
        super(Pattern.compile("^((doc)|(try)|(ask)|(otherwise)|(then)|(with)|(sharing)|(where)|(check)|(graph)|(block))"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeKeywordColon.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

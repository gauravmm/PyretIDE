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
public class TokenTypeStringDouble extends TokenType {

    public static TokenTypeStringDouble getInstance(){
        return instance;
    }
    
    private static final TokenTypeStringDouble instance = new TokenTypeStringDouble();
    
    private TokenTypeStringDouble(){
        super(Pattern.compile("^/[^\"\\\\]//\"/"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeStringDouble.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

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
public class TokenTypeDefault extends TokenType {

    public static TokenTypeDefault getInstance(){
        return instance;
    }
    
    private static final TokenTypeDefault instance = new TokenTypeDefault();
    
    private TokenTypeDefault(){
        super(Pattern.compile("^([a-zA-Z][a-zA-Z0-9\\-]*)"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeDefault.getInstance());
    }

}

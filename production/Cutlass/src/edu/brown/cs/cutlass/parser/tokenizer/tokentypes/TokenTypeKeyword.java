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
public class TokenTypeKeyword extends TokenType {

    public static TokenTypeKeyword getInstance(){
        return instance;
    }
    
    private static final TokenTypeKeyword instance = new TokenTypeKeyword();
    
    private TokenTypeKeyword(){
        super(Pattern.compile("^((fun)|(method)|(var)|(when)|(import)|(provide)|(data)|(end)|(except)|(for)|(from)|(and)|(or)|(not)|(as)|(if)|(else)|(cases))"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeKeyword.getInstance());
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

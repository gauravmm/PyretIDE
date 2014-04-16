/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeKeywordDoc extends TokenType {

    public static TokenTypeKeywordDoc getInstance(){
        return instance;
    }
    
    private static final TokenTypeKeywordDoc instance = new TokenTypeKeywordDoc();
    
    private TokenTypeKeywordDoc(){
        super(Pattern.compile("^doc(\\s)*:"));
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeKeywordDoc.getInstance());
    }

    @Override
    public List<TokenType> expectedFollowingTokens() {
        LinkedList<TokenType> rv = new LinkedList<>();
        rv.add(TokenTypeString.getInstance());
        return rv;
    }

    @Override
    public boolean toAggregate() {
        return false;
    }
    
}

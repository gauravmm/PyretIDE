/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleComment;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeComment extends TokenType {

    public static TokenTypeComment getInstance(){
        return instance;
    }
    
    private static final TokenTypeComment instance = new TokenTypeComment();
    
    private TokenTypeComment(){
        super(Pattern.compile("^\\#.*$"));
    }

    @Override
    public boolean ignoreForExpectedToken() {
        return true;
    }
    
    @Override
    public Token constructToken(String value, int offset, int length) {
        return new Token(value, offset, length, TokenTypeComment.getInstance());
    }

    @Override
    public TokenStyle getStyle() {
        return TokenStyleComment.getInstance();
    }

}

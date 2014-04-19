/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleError;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeError extends TokenType {

    public static TokenTypeError getInstance(){
        return instance;
    }
    
    private static final TokenTypeError instance = new TokenTypeError();
    
    private TokenTypeError(){
        super(Pattern.compile("^([^\\s]*)"));
    }

    @Override
    public boolean toAggregate() {
        return true;
    }
    
    @Override
    public TokenStyle getStyle() {
        return TokenStyleError.getInstance();
    }
    
}

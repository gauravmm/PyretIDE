/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.tokentypes;

import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePunct;
import java.util.regex.Pattern;

/**
 *
 * @author dilip
 */
public class TokenTypeDoublePunct extends TokenType {

    public static TokenTypeDoublePunct getInstance(){
        return instance;
    }
    
    private static final TokenTypeDoublePunct instance = new TokenTypeDoublePunct();
    
    private TokenTypeDoublePunct(){
        super(Pattern.compile("^((==)|(>=)|(<=)|(=>)|(:=)|(<>))"));
    }
    
    @Override
    public TokenStyle getStyle() {
        return TokenStylePunct.getInstance();
    }

}

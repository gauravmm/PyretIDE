/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer.styles;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenStyles {
    
    private static final List<TokenStyle> list = Arrays.asList(
            TokenStyleDefault.getInstance(),
            TokenStyleComment.getInstance(),
            TokenStyleKeyword.getInstance(),
            TokenStyleNumber.getInstance(),
            TokenStylePunct.getInstance(),
            TokenStyleString.getInstance(),
            TokenStyleError.getInstance()
    );
    
    public static TokenStyle getDefaultStyle(){
        return TokenStyleDefault.getInstance();
    }
    
    public static TokenStyle getErrorStyle(){
        return TokenStyleError.getInstance();
    }
    
    public static List<TokenStyle> getAllStyles(){
        return list;
    }
}

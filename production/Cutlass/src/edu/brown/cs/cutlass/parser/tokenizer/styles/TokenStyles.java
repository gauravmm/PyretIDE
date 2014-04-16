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
    
    private static List<TokenStyle> list = Arrays.asList(
            TokenStyleDefault.getInstance(),
            TokenStyleComment.getInstance(),
            TokenStyleDoublePunct.getInstance(),
            TokenStyleKeyword.getInstance(),
            TokenStyleKeywordAggregate.getInstance(),
            TokenStyleKeywordColon.getInstance(),
            TokenStyleKeywordDoc.getInstance(),
            TokenStyleNumber.getInstance(),
            TokenStyleCloseBrace.getInstance(),
            TokenStyleCloseBrack.getInstance(),
            TokenStyleCloseColon.getInstance(),
            TokenStyleCloseParen.getInstance(),
            TokenStyleOpenBrace.getInstance(),
            TokenStyleOpenBrack.getInstance(),
            TokenStyleOpenColon.getInstance(),
            TokenStyleOpenCloseColon.getInstance(),
            TokenStyleSinglePunct.getInstance(),
            TokenStyleString.getInstance(),
            TokenStyleWhitespace.getInstance()
    );
    
    public static TokenStyle getDefaultStyle(){
        return TokenStyleDefault.getInstance();
    }
    
    public static List<TokenStyle> getAllStyles(){
        return list;
    }
}

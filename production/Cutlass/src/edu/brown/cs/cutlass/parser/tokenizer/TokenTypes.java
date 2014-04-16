/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenTypes {

    private static final List<TokenType> types = Arrays.asList(
            TokenTypePairedOpenBrace.getInstance(),
            TokenTypePairedCloseBrace.getInstance(),
            TokenTypePairedCloseBrack.getInstance(),
            TokenTypePairedCloseParen.getInstance(),
            TokenTypePairedOpenBrack.getInstance(),
            TokenTypePairedOpenParen.getInstance(),
            TokenTypeComment.getInstance(),
            TokenTypeKeywordDoc.getInstance(),
            TokenTypeDoublePunct.getInstance(),
            TokenTypePairedOpenCloseColon.getInstance(),
            TokenTypePairedOpenColon.getInstance(), 
            TokenTypePairedCloseColon.getInstance(), 
            TokenTypeKeyword.getInstance(),
            TokenTypeKeywordColon.getInstance(),
            TokenTypeSinglePunct.getInstance(),
            TokenTypeString.getInstance(),
            TokenTypeNumber.getInstance(),
            TokenTypeWhitespace.getInstance(),
            TokenTypeDefault.getInstance()
    );

    public static List<TokenType> getTypes() {
        return types;
    }

    public static boolean isWhitespaceTokenType(TokenType tt) {
        return tt == TokenTypeWhitespace.getInstance();
    }
}

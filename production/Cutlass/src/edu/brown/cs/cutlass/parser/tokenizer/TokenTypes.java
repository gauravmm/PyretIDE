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

    // NOTE: The order is important.
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
            TokenTypeAnnotation.getInstance(),
            TokenTypePairedOpenCloseColon.getInstance(),
            TokenTypePairedOpenColon.getInstance(), 
            TokenTypePairedCloseColon.getInstance(), 
            TokenTypePairedCloseColonWords.getInstance(), 
            TokenTypeKeywordAggregate.getInstance(),
            TokenTypeKeyword.getInstance(),
            TokenTypeKeywordColon.getInstance(),
            TokenTypeString.getInstance(),
            TokenTypeNumber.getInstance(),
            TokenTypeSinglePunct.getInstance(),
            TokenTypeWhitespace.getInstance(),
            TokenTypeDefault.getInstance(),
            TokenTypeError.getInstance()
    );

    public static List<TokenType> getTypes() {
        return types;
    }

    public static boolean isWhitespaceTokenType(TokenType tt) {
        return tt == TokenTypeWhitespace.getInstance();
    }
}

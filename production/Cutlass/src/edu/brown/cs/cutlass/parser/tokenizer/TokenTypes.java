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
            TokenTypeDefault.getInstance(),
            TokenTypeDoublePunct.getInstance(),
            TokenTypeSingleColon.getInstance(), // MUST appear after DoublePunct
            TokenTypeKeyword.getInstance(),
            TokenTypeKeywordColon.getInstance(),
            TokenTypeSinglePunct.getInstance(),
            TokenTypeStringDouble.getInstance(),
            TokenTypeStringSingle.getInstance(),
            TokenTypeWhitespace.getInstance()
    );

    public static List<TokenType> getTypes() {
        return types;
    }

    public static TokenType getWhitespaceTokenType() {
        return TokenTypeWhitespace.getInstance();
    }
}

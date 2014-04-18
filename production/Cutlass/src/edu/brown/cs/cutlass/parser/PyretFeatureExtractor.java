/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPaired;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedClosing;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.parser.tokenizer.TokenType;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePairedOpen;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeDefault;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeKeywordAggregate;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeKeywordColon;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypePairedOpenColon;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypePairedOpenParen;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeSinglePunct;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeWhitespace;
import edu.brown.cs.cutlass.util.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Understand Pyret source code and provide the IDE with metadata of program.
 *
 * @author Gaurav Manek
 */
public class PyretFeatureExtractor {

    public PyretMetadata extract(TokenParserOutput tpo) {
        // Keywords
        Map<String, List<Token>> keywords = tpo.getTokenCollected().get(TokenTypeKeywordAggregate.getInstance());
        Map<String, List<Token>> names = tpo.getTokenCollected().get(TokenTypeDefault.getInstance());
        if (keywords == null || names == null) {
            throw new IllegalStateException("Collected tokens not properly set-up.");
        }

        List<PyretFunction> functions = new ArrayList<>();

        // Extract function definitions:
        List<Token> funLocs = keywords.get("fun");
        for (Token tok : funLocs) {
            // Check if the next token is a name
            Option<Token> nextToken = getNextToken(tok, TokenTypeDefault.getInstance());
            if (!nextToken.hasData()) { // If there is no name, skip it.
                continue; // Skip this.
            }
            Token funNameToken = nextToken.getData();

            Option<Token> checkParenToken = getNextToken(funNameToken, TokenTypePairedOpenParen.getInstance());
            if (!checkParenToken.hasData()) { // If there is no open paren, skip it.
                continue;
            }

            functions.add(new PyretFunction(new PyretLocation(tok), funNameToken.getValue()));
        }

        // Extract function calls:
        Map<PyretFunction, List<PyretFunctionCall>> functionCalls = new HashMap<>();
        for (PyretFunction fun : functions) {
            List<PyretFunctionCall> functionCallsSingle = new ArrayList<>();
            List<Token> possibleFunctionCalls = names.get(fun.getName());
            for (Token funCall : possibleFunctionCalls) {
                Option<Token> checkParenToken = getNextToken(funCall, TokenTypePairedOpenParen.getInstance());
                if (!checkParenToken.hasData()) { // If there is no open paren, skip it.
                    continue;
                }
                functionCallsSingle.add(new PyretFunctionCall(new PyretLocation(funCall), fun));
            }
            functionCalls.put(fun, functionCallsSingle);
        }

        // NOTE: SCOPE NOT IMPLEMENTED YET
        // Data Extraction
        List<PyretData> data = new ArrayList<>();
        List<Token> funData = keywords.get("data");
        for (Token tok : funData) {
            Option<Token> nextToken = getNextToken(tok, TokenTypeDefault.getInstance());
            if (!nextToken.hasData()) { // If there is no name, skip it.
                continue; // Skip this.
            }
            Token dataNameToken = nextToken.getData();

            Option<Token> checkColonToken = getNextToken(dataNameToken, TokenTypeKeywordColon.getInstance());
            if (!checkColonToken.hasData()) { // If there is no open paren, skip it.
                continue;
            }
            TokenPairedOpening colonToken = (TokenPairedOpening) checkColonToken.getData();
            if (colonToken.other == null) {
                continue; // Can't establish scope, forget it.
            }
            Token endingToken = colonToken.other;

            PyretData thisData = new PyretData(new PyretLocation(tok), dataNameToken.getValue());
            data.add(thisData);

            // Traverse the contents of this data, skipping over brackets and locating | tokens.
            Token curr = colonToken;
            Token prev = colonToken;
            while (true) {
                if (!curr.hasNextToken()) { // End of stream
                    break;
                }
                if (curr == endingToken) { // End of data definition
                    break;
                }
                curr = curr.getNextToken();

                // If this is a data entry:
                if (prev.getType() instanceof TokenTypeSinglePunct && prev.getValue().equals("|") && curr.getType() instanceof TokenTypeDefault) {
                    // Assemble the variant string and check for value
                    Option<String> variantString = getVariantString(curr);
                    if (variantString.hasData()) {
                        thisData.add(new PyretDataVariant(new PyretLocation(prev), curr.getValue(), thisData, variantString.getData()));
                    }
                }

                // If this is some kind of scope, skip across it
                if (curr.getType() instanceof TokenTypePairedOpen) {
                    TokenPairedOpening opening = (TokenPairedOpening) curr;
                    if (opening.other == null) {
                        break;
                    }
                    curr = opening.other;
                }

                prev = curr;
            }
        }

        return new PyretMetadata(functions, data, null, null, functionCalls);
    }

    private Option<Token> getNextToken(Token start, TokenType type) {
        return getNextToken(start, type, true);
    }

    private Option<Token> getNextToken(Token start, TokenType type, boolean onlySkipWhitespace) {
        Token tok = start;
        while (true) {
            if (tok.hasNextToken()) {
                tok = tok.getNextToken();
                if (tok.getType() == type) {
                    return new Option<>(tok);
                } else if (onlySkipWhitespace && !tok.getType().ignoreForExpectedToken()) {
                    return new Option<>();
                }
            } else {
                return new Option<>();
            }
        }
    }

    private Option<String> getVariantString(Token starting) {
        StringBuilder sb = new StringBuilder();
        Option<Token> checkOpenBracket = getNextToken(starting, TokenTypePairedOpenParen.getInstance());
        if (!checkOpenBracket.hasData()) {
            return new Option<>();
        }
        TokenPairedOpening openBracket = (TokenPairedOpening) checkOpenBracket.getData();
        if (openBracket.other == null) {
            return new Option<>();
        }
        TokenPaired closeBracket = openBracket.other;
        Token curr = openBracket;

        // Assemble the string:
        do {
            if (curr.getType() instanceof TokenTypeWhitespace) {
                sb.append(" ");
            } else {
                sb.append(curr.getValue());
            }

            curr = curr.getNextToken();
        } while (curr != closeBracket);

        return new Option<>(sb.toString());
    }

    /*
     private Option<Token> getNextToken(Token start, List<TokenType> types, boolean onlySkipWhitespace) {
     Token tok = start;
     while (true) {
     if (tok.hasNextToken()) {
     tok = tok.getNextToken();
     for (TokenType type : types) {
     if (tok.getType() == type) {
     return new Option<>(tok);
     }
     }
     if (onlySkipWhitespace && !tok.getType().ignoreForExpectedToken()) {
     return new Option<>();
     }
     } else {
     return new Option<>();
     }
     }
     }
     */
}

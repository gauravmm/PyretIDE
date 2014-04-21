/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.editor.EditorJumpTo;
import edu.brown.cs.cutlass.editor.EditorJumpToClient;
import edu.brown.cs.cutlass.editor.callgraph.CallGraphEntry;
import edu.brown.cs.cutlass.parser.tokenizer.*;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.*;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Understand Pyret source code and provide the IDE with metadata of program.
 *
 * @author Gaurav Manek
 */
public class PyretFeatureExtractor {

    public static List<CallGraphEntry> getCallGraphEntries(PyretMetadata meta, Option<Token> token, EditorJumpToClient client) {
        ArrayList<CallGraphEntry> rv = new ArrayList<>();

        // Figure out scope for current token
        PyretFunction tokenFunction = null;
        if (token.hasData()) {
            for (Token possibleParent : token.getData().getScope()) {
                PyretFunction possibleFunction = meta.functions.get(possibleParent);
                if (possibleFunction != null) {
                    tokenFunction = possibleFunction;
                    break;
                }
            }
        }

        // Now tokenFunction contains the most specific scope of the current token, or null if no such scope can be located.
        if (tokenFunction == null) {
            for (PyretFunction fun : meta.functions.values()) {
                rv.add(new CallGraphEntry(fun.getName(), false, false, false, fun.isDataVariant() ? new Option<>(fun.getConstructorOf().getData().name) : new Option<String>(), client.createJumpTo(fun.getLocation().token.getOffset())));
            }
        } else {
            Set<PyretFunction> callsFrom = meta.functionCallGraphFrom.get(tokenFunction);
            Set<PyretFunction> callsTo = meta.functionCallGraphTo.get(tokenFunction);
            
            if(callsFrom == null || callsTo == null){
                Lumberjack.log(Lumberjack.Level.ERROR, "Call Graph From/To not populated correctly!");
                callsFrom = Collections.emptySet();
                callsTo = Collections.emptySet();
            }
            
            for (PyretFunction fun : meta.functions.values()) {
                rv.add(new CallGraphEntry(
                        fun.getName(), 
                        tokenFunction.equals(fun), 
                        callsFrom.contains(fun), 
                        callsTo.contains(fun), 
                        fun.isDataVariant() ? new Option<>(fun.getConstructorOf().getData().name) : new Option<String>(), client.createJumpTo(fun.getLocation().token.getOffset())));
            }
        }

        return rv;
    }

    public static PyretMetadata extract(TokenParserOutput tpo) {
        // Keywords
        Map<String, List<Token>> keywords = tpo.getTokenCollected().get(TokenTypeKeywordAggregate.getInstance());
        Map<String, List<Token>> names = tpo.getTokenCollected().get(TokenTypeDefault.getInstance());
        if (keywords == null || names == null) {
            throw new IllegalStateException("Collected tokens not properly set-up.");
        }

        Map<Token, PyretFunction> functions = new LinkedHashMap<>();

        // Extract function definitions:
        List<Token> funLocs = keywords.get("fun");
        if (funLocs != null) {
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
                TokenPairedOpening parenOpenToken = (TokenPairedOpening) checkParenToken.getData();
                if (parenOpenToken.other == null) {
                    continue;
                }

                Option<Token> tokenScope = getNextToken(parenOpenToken.other, TokenTypePairedOpenColon.getInstance());
                if (!tokenScope.hasData()) {
                    continue;
                }

                functions.put(tokenScope.getData(), new PyretFunction(new PyretLocation(funNameToken), funNameToken.getValue(), (TokenPairedOpening) tokenScope.getData()));
            }
        }

        // Data Extraction
        List<PyretData> data = new ArrayList<>();
        List<Token> funData = keywords.get("data");
        if (funData != null) {
            for (Token tok : funData) {
                Option<Token> nextToken = getNextToken(tok, TokenTypeDefault.getInstance());
                if (!nextToken.hasData()) { // If there is no name, skip it.
                    continue; // Skip this.
                }
                Token dataNameToken = nextToken.getData();

                Option<Token> checkColonToken = getNextToken(dataNameToken, TokenTypePairedOpenColon.getInstance());
                if (!checkColonToken.hasData()) { // If there is no open colon, skip it.
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

                    do {
                        curr = curr.getNextToken();
                    } while (curr.getType() instanceof TokenTypeWhitespace);

                    // If this is a data entry:
                    if ((prev.getType() instanceof TokenTypeSinglePunct) && prev.getValue().equals("|") && (curr.getType() instanceof TokenTypeDefault)) {
                        // Assemble the variant string and check for value
                        Option<String> variantString = getVariantString(curr);
                        if (variantString.hasData()) {
                            PyretDataVariant variant = new PyretDataVariant(new PyretLocation(curr), curr.getValue(), thisData, variantString.getData());
                            thisData.add(variant);
                            functions.put(curr, new PyretFunction(variant));
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
        }

        // Extract function calls:
        Map<PyretFunction, List<PyretFunctionCall>> functionCalls = new HashMap<>();
        for (PyretFunction fun : functions.values()) {
            List<PyretFunctionCall> functionCallsSingle = new ArrayList<>();
            List<Token> possibleFunctionCalls = names.get(fun.getName());
            if (fun.isDataVariant()) {
                for (Token funCall : possibleFunctionCalls) {
                    if (funCall != fun.location.token) {
                        functionCallsSingle.add(new PyretFunctionCall(new PyretLocation(funCall), fun));
                    }
                }
            } else {
                for (Token funCall : possibleFunctionCalls) {
                    Option<Token> checkParenToken = getNextToken(funCall, TokenTypePairedOpenParen.getInstance());
                    if (!checkParenToken.hasData()) { // If there is no open paren, skip it.
                        continue;
                    }
                    // Check if the function call is the same as the original function definition:
                    if (funCall != fun.location.token) {
                        functionCallsSingle.add(new PyretFunctionCall(new PyretLocation(funCall), fun));
                    }
                }
            }
            functionCalls.put(fun, functionCallsSingle);
        }

        // Prepare the scope-finding code
        Map<PyretFunction, Set<PyretFunction>> functionCallGraphFrom = new HashMap<>();
        Map<PyretFunction, Set<PyretFunction>> functionCallGraphTo = new HashMap<>();

        // Initialize
        for (PyretFunction fun : functions.values()) {
            functionCallGraphFrom.put(fun, new HashSet<PyretFunction>());
            functionCallGraphTo.put(fun, new HashSet<PyretFunction>());
        }

        // Loop through each function call, figure out which scope it occurs in.
        for (List<PyretFunctionCall> funcs : functionCalls.values()) {
            for (PyretFunctionCall fCall : funcs) {
                // For each scope, if the function 
                for (Token possibleParent : fCall.getLocation().token.getScope()) {
                    PyretFunction possibleFunction = functions.get(possibleParent);
                    if (possibleFunction != null) {
                        functionCallGraphTo.get(possibleFunction).add(fCall.getFunction());
                        functionCallGraphFrom.get(fCall.getFunction()).add(possibleFunction);
                    }
                }
            }
        }

        // Figure out the current scope
        return new PyretMetadata(functions, data, functionCallGraphFrom, functionCallGraphTo, functionCalls);
    }

    private static Option<Token> getNextToken(Token start, TokenType type) {
        return getNextToken(start, type, true);
    }

    private static Option<Token> getNextToken(Token start, TokenType type, boolean onlySkipWhitespace) {
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

    private static Option<String> getVariantString(Token starting) {
        StringBuilder sb = new StringBuilder();
        Option<Token> checkOpenBracket = getNextToken(starting, TokenTypePairedOpenParen.getInstance());
        if (!checkOpenBracket.hasData()) {
            Option<Token> checkPipe = getNextToken(starting, TokenTypeSinglePunct.getInstance());
            if (checkPipe.hasData() && checkPipe.getData().getValue().equals("|")) {
                return new Option<>("");
            }
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
}

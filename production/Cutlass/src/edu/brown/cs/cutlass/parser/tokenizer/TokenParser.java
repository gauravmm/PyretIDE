/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeWhitespace;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenParser {

    public static TokenParserOutput parseTokens(String s) {
        return parseTokens(s.split("(\r)?\n", -1)); // -1 to prevent multiple delimiters from being discarded.
    }

    public static TokenParserOutput parseTokens(String[] s) {
        return parseTokens(Arrays.asList(s));
    }

    public static TokenParserOutput parseTokens(List<String> input) {
        Stack<TokenPairedOpening> pairOpenStart = new Stack<>();
        TokenScope scope = new TokenScope();
        Stack<Pair<Token, TokenType>> expectedFutureToken = new Stack<>();
        List<ParsingError> parsingErrors = new LinkedList<>();

        List<TokenType> types = new LinkedList<>(TokenTypes.getTypes());
        LinkedList<Line> outLine = new LinkedList<>();

        // Prepare aggregator
        Map<TokenType, Map<String, List<Token>>> aggregator = new HashMap<>();
        for (TokenType tt : types) {
            if (tt.toAggregate()) {
                aggregator.put(tt, new HashMap<String, List<Token>>());
            }
        }

        Iterator<String> itr = input.iterator();

        int offset = 0;
        int lineNumber = 0;
        Token prev = null;

        // For each line in the input:
        while (itr.hasNext()) {
            String inLine = itr.next();

            int lineOffset = offset;
            int lineIndent = pairOpenStart.size();
            ArrayList<Token> lineContents = new ArrayList<>();

            // Repeat until the line has been entirely tokenized:
            while (!inLine.isEmpty()) {
                TokenType matchedType = null;
                Token token = null;

                // Attempt to match:
                for (TokenType tt : types) {
                    Option<String> m = tt.getMatch(inLine);
                    // If this matches:
                    if (m.hasData()) {
                        String tokenString = m.getData();
                        int size = tokenString.length();
                        token = tt.constructToken(tokenString, offset, size, scope);
                        offset += size;
                        inLine = inLine.substring(size);
                        matchedType = tt;
                        break;
                    }
                }

                if (matchedType == null || token == null) {
                    throw new IllegalStateException("Nothing matched! \"" + inLine + "\"");
                }

                assert matchedType != null;
                assert token != null;

                // Now that we have a matchedType, do something with it.
                // Check if previous expectation is correct:
                TokenType tt = token.getType();
                if (!tt.ignoreForExpectedToken() && !expectedFutureToken.empty()) {
                    if (expectedFutureToken.peek().getY() == tt) {
                        expectedFutureToken.pop();
                    } else {
                        parsingErrors.add(new ParsingError(token, "A different token is expected here, are you forgetting something?"));
                        expectedFutureToken.pop().getX().setStyle(TokenStyles.getErrorStyle());
                    }
                }

                // Update the expected future token.
                if (!tt.expectedFollowingTokens().isEmpty()) {
                    List<TokenType> efT = tt.expectedFollowingTokens();
                    ListIterator<TokenType> listIterator = efT.listIterator(efT.size());
                    // Push the expected future tokens onto the stack in reverse order.
                    while (listIterator.hasPrevious()) {
                        expectedFutureToken.push(new Pair<>(token, listIterator.previous()));
                    }
                }

                // Pair matching
                if (tt instanceof TokenTypePairedOpen) {
                    pairOpenStart.add((TokenPairedOpening) token);
                    scope = scope.push(token);
                    // Update the token types to ignore.
                    types.removeAll(((TokenTypePairedOpen) tt).getIgnoreTokenTypes());
                } else if (tt instanceof TokenTypePairedClose) {
                    if (!(token instanceof TokenPairedClosing)) {
                        throw new IllegalStateException("Illegal state in parser.");
                    }
                    TokenTypePairedClose ttc = (TokenTypePairedClose) tt;
                    TokenPairedClosing tc = (TokenPairedClosing) token;

                    // If the type matches correctly:
                    if (pairOpenStart.empty()) {
                        parsingErrors.add(new ParsingError(token, "There is nothing to close here."));
                        token.setStyle(TokenStyles.getErrorStyle());
                    } else if (ttc.isMatchingTokenType(pairOpenStart.peek().getType())) {
                        // This token matches the closing token.
                        // Give them references to each other and remove it from the list.
                        TokenPairedOpening startingToken = pairOpenStart.pop();
                        // Pop the scope stack.
                        scope = scope.getRest();
                        startingToken.other = tc;
                        tc.other = startingToken;
                    } else {
                        parsingErrors.add(new ParsingError(token, "This doesn't close anything."));
                        token.setStyle(TokenStyles.getErrorStyle());
                    }

                    // Reset the token types to ignore:
                    types = new LinkedList<>(TokenTypes.getTypes());
                    for (TokenPairedOpening st : pairOpenStart) {
                        types.removeAll(st.getType().getIgnoreTokenTypes());
                    }
                }

                // Aggregator
                if (tt.toAggregate()) {
                    Map<String, List<Token>> get = aggregator.get(tt);
                    // Check that the aggregator has been defined.
                    if (get == null) {
                        throw new IllegalStateException("Aggregator for " + tt.getClass().getSimpleName() + " not initialized!");
                    } else {
                        // Check if the key is defined.
                        List<Token> tokstr = get.get(token.getValue());
                        if (tokstr == null) {
                            // Define the key if not already done so.
                            tokstr = new LinkedList<>();
                            get.put(token.getValue(), tokstr);
                        }
                        // Add to the list.
                        tokstr.add(token);
                    }
                }

                // Set the line-indent to be the leftmost size
                lineIndent = Math.min(pairOpenStart.size(), lineIndent);

                // Add to line, complete linked list reference.
                token.setPreviousToken(prev);
                if (prev != null) {
                    prev.setNextToken(token);
                }
                lineContents.add(token);
                prev = token;
            }

            // Line is over
            if (itr.hasNext()) {
                lineContents.add(TokenTypeWhitespace.getInstance().constructToken(Line.LINE_TERMINATOR, offset, Line.LINE_TERMINATOR.length(), scope));
                offset += Line.LINE_TERMINATOR.length();
            }
            outLine.add(new Line(lineNumber++, lineOffset, offset - lineOffset, lineIndent, lineContents));
        }

        // Check that everything has been closed:
        while (!pairOpenStart.empty()) {
            TokenPairedOpening errtok = pairOpenStart.pop();
            parsingErrors.add(new ParsingError(errtok, "This is not closed."));
            errtok.setStyle(TokenStyles.getErrorStyle());
        }

        if (!expectedFutureToken.empty()) {
            if (prev != null) {
                prev.setStyle(TokenStyles.getErrorStyle());
            }
            StringBuilder ex = new StringBuilder();
            ex.append("The following tokens are expected: ");
            while (!expectedFutureToken.empty()) {
                ex.append(expectedFutureToken.pop().getClass().getName());
                if (!expectedFutureToken.empty()) {
                    ex.append(", ");
                }
            }
            parsingErrors.add(new ParsingError(prev, ex.toString()));
        }

        return new TokenParserOutput(outLine, aggregator, parsingErrors);
    }

}

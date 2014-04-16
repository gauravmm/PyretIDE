/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenParser {

    private static final Pattern tag = Pattern.compile("<(/?)([a-zA-Z0-9]*)(\\b[^>]*)?>");

    public static TokenParserOutput parseTokens(String s) {
        return parseTokens(s.split("(\r)?\n"));
    }

    public static TokenParserOutput parseTokens(String[] s) {
        return parseTokens(Arrays.asList(s));
    }

    public static TokenParserOutput parseTokens(List<String> input) {
        Stack<TokenPairedOpening> pairOpenStart = new Stack<>();
        Stack<TokenType> expectedFutureToken = new Stack<>();

        List<TokenType> types = TokenTypes.getTypes();
        ArrayList<Line> outLine = new ArrayList<>();
        outLine.ensureCapacity(input.size());

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

        // For each line in the input:
        while (itr.hasNext()) {
            String inLine = itr.next();

            int lineOffset = offset;
            ArrayList<Token> lineContents = new ArrayList<>();

            // Repeat until the line has been entirely tokenized:
            while (!inLine.isEmpty()) {
                TokenType matchedType = null;
                Token token = null;

                // Attempt to match:
                for (TokenType tt : types) {
                    Matcher m = tt.getPattern().matcher(inLine);
                    // If this pattern matches:
                    if (m.find()) {
                        if (m.start() != 0) {
                            throw new IllegalStateException("The token parsed does not start at position 0." + tt.getClass().getName() + " \"" + inLine + "\"");
                        } else if (m.end() == 0) {
                            throw new IllegalStateException("The token parsed has length 0. " + tt.getClass().getName() + " \"" + inLine + "\"");
                        }
                        int size = m.end() - m.start();
                        matchedType = tt;
                        token = tt.constructToken(m.group(), offset, size);
                        offset += size;
                        inLine = inLine.substring(size);

                        break;
                    }
                }

                if (matchedType == null || token == null) {
                    throw new IllegalStateException("Nothing matched!");
                }

                assert matchedType != null;
                assert token != null;

                // Now that we have a matchedType, do something with it.
                // Check if previous expectation is correct:
                TokenType tt = token.getType();
                if (!tt.ignoreForExpectedToken() && !expectedFutureToken.empty()) {
                    if (expectedFutureToken.peek() == tt) {
                        expectedFutureToken.pop();
                    } else {
                        throw new TokenParsingException("Well-formedness: The token does not match the expected following token.");
                    }
                }

                // Update the expected future token.
                if (!tt.expectedFollowingTokens().isEmpty()) {
                    List<TokenType> efT = tt.expectedFollowingTokens();
                    ListIterator<TokenType> listIterator = efT.listIterator(efT.size());
                    // Push the expected future tokens onto the stack in reverse order.
                    while (listIterator.hasPrevious()) {
                        expectedFutureToken.push(listIterator.previous());
                    }
                }

                // Pair matching
                if (tt instanceof TokenTypePairedOpen) {
                    pairOpenStart.add((TokenPairedOpening) token);
                } else if (tt instanceof TokenTypePairedClose) {
                    if (!(token instanceof TokenPairedClosing)) {
                        throw new IllegalStateException("Illegal state in parser.");
                    }
                    TokenTypePairedClose ttc = (TokenTypePairedClose) tt;
                    TokenPairedClosing tc = (TokenPairedClosing) token;

                    // If the type matches correctly:
                    if (ttc.isMatchingTokenType(pairOpenStart.peek().getType())) {
                        // This token matches the closing token.
                        // Give them references to each other and remove it from the list.
                        TokenPairedOpening startingToken = pairOpenStart.pop();
                        startingToken.other = tc;
                        tc.other = startingToken;
                    } else {
                        throw new TokenParsingException("Well-formedness: The following tokens do not match:" + pairOpenStart.peek() + " " + tc);
                    }
                }

                // Add to line
                lineContents.add(token);
            }

            // Line is over
            offset += Line.LINE_TERMINATOR.length();
            outLine.add(new Line(lineNumber++, lineOffset, offset - lineOffset, pairOpenStart.size(), lineContents));
        }

        // Check that everything has been closed:
        if (!pairOpenStart.empty()) {
            throw new TokenParsingException("Well-formedness: The following tokens are not closed:" + pairOpenStart.toString());
        } else if (!expectedFutureToken.empty()) {
            throw new TokenParsingException("Well-formedness: The following tokens are expected:" + expectedFutureToken.toString());
        } else {
            return new TokenParserOutput(outLine, aggregator);
        }
    }
}

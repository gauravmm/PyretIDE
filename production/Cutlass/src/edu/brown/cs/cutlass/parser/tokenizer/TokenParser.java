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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;

/**
 *
 * @author Gaurav Manek
 */
public final class TokenParser {

    public static TokenParserOutput parseTokens(String s) {
        return parseTokens(s.split("(\r)?\n"));
    }

    public static TokenParserOutput parseTokens(String[] s) {
        return parseTokens(Arrays.asList(s));
    }

    public static TokenParserOutput parseTokens(List<String> input) {
        Stack<TokenPairedOpening> pairOpenStart = new Stack<>();
        Stack<TokenType> expectedFutureToken = new Stack<>();
        List<ParsingError> parsingErrors = new LinkedList<>();

        List<TokenType> types = TokenTypes.getTypes();
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
                    throw new IllegalStateException("Nothing matched! \"" + inLine + "\"");
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
                        parsingErrors.add(new ParsingError(token, "A different token is expected here, are you forgetting something?"));
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
                    if (pairOpenStart.empty()) {
                        parsingErrors.add(new ParsingError(token, "There is nothing to close here."));
                    } else if (ttc.isMatchingTokenType(pairOpenStart.peek().getType())) {
                        // This token matches the closing token.
                        // Give them references to each other and remove it from the list.
                        TokenPairedOpening startingToken = pairOpenStart.pop();
                        startingToken.other = tc;
                        tc.other = startingToken;
                    } else {
                        parsingErrors.add(new ParsingError(token, "This doesn't close anything."));
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
                token.previous = prev;
                if (prev != null) {
                    prev.next = token;
                }
                lineContents.add(token);
                prev = token;
            }

            // Line is over
            offset += Line.LINE_TERMINATOR.length();
            outLine.add(new Line(lineNumber++, lineOffset, offset - lineOffset, lineIndent, lineContents));
            //System.err.print(outLine.getLast());
        }

        // Check that everything has been closed:
        while (!pairOpenStart.empty()) {
            parsingErrors.add(new ParsingError(pairOpenStart.pop(), "This is not closed."));
        }

        if (!expectedFutureToken.empty()) {
            StringBuilder ex = new StringBuilder();
            ex.append("The following tokens are expected: ");
            while (!expectedFutureToken.empty()) {
                ex.append(expectedFutureToken.pop().getClass().getName());
                if(!expectedFutureToken.empty()){
                    ex.append(", ");
                }
            }
            parsingErrors.add(new ParsingError(prev, ex.toString()));
        }

        return new TokenParserOutput(outLine, aggregator, parsingErrors);
    }
}

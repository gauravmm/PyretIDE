/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeWhitespace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Gaurav Manek
 */
public class Line implements Comparable<Integer> {

    public final static String LINE_TERMINATOR = "\n";
    public final static String LINE_INDENT = "  ";
    public final static String LINE_SPACING = " ";

    final int offset;
    final int length;
    final int number;
    final int expectedIndentation;
    final List<Token> contents;

    public Line(int n, int offset, int length, int expectedIndentation, List<Token> contents) {
        this.expectedIndentation = expectedIndentation;
        if (contents == null) {
            throw new IllegalStateException("Null contents passed.");
        }
        this.contents = Collections.unmodifiableList(new ArrayList<>(contents));

        this.offset = offset;
        this.length = length;
        this.number = n;
    }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%s%n", number, expectedIndentation, contents.toString());
    }

    public List<Token> getContents() {
        return contents;
    }

    public String toIndentedString() {
        StringBuilder sb = new StringBuilder();
        int eI = expectedIndentation;
        while (eI-- > 0) {
            sb.append(LINE_INDENT);
        }
        Iterator<Token> ci = contents.iterator();
        boolean start = true;
        while (ci.hasNext()) {
            Token tok = ci.next();
            if (start && (TokenTypes.isWhitespaceTokenType(tok.getType()))) {
                start = false;
                continue;
            }
            sb.append(tok.getValue());
            start = false;
        }
        sb.append(LINE_TERMINATOR);
        return sb.toString();
    }

    /**
     * NOTE: This function returns token offsets, lengths and links between
     * tokens in an inconsistent state.
     *
     * @return
     */
    public Line toIndentedLine() {
        ArrayList<Token> nCont = new ArrayList<>();
        nCont.ensureCapacity(contents.size());

        StringBuilder sb = new StringBuilder();
        int eI = expectedIndentation;
        while (eI-- > 0) {
            sb.append(LINE_INDENT);
        }
        String indent = sb.toString();

        Token startingToken = TokenTypeWhitespace.getInstance().constructToken(indent, offset, indent.length(), new TokenScope());
        nCont.add(startingToken); // Add the leading whitespace

        Iterator<Token> ci = contents.iterator();

        // Starting token:
        if (ci.hasNext()) {
            Token tok = ci.next();
            if (TokenTypes.isWhitespaceTokenType(tok.getType()) && !tok.getValue().equals(LINE_TERMINATOR)) {
                // Swap token into linked list
                //startingToken.setPreviousToken(tok.getPreviousToken());
                //startingToken.setNextToken(tok.getNextToken());
            } else {
                // Insert token into linked list
                /*
                 if(tok.hasPreviousToken()){
                 tok.getPreviousToken().setNextToken(startingToken);
                 }
                 startingToken.setPreviousToken(tok.getPreviousToken());
                 startingToken.setNextToken(tok.getNextToken());
                 tok.setPreviousToken(startingToken);
                 */
                nCont.add(tok); // Add the first token if its not whitespace:    
            }

            while (ci.hasNext()) {
                tok = ci.next();
                if (TokenTypes.isWhitespaceTokenType(tok.getType()) && !tok.getValue().equals(LINE_TERMINATOR)) {
                    nCont.add(TokenTypeWhitespace.getInstance().constructToken(LINE_SPACING, offset, LINE_SPACING.length(), tok.getScope()));
                } else {
                    nCont.add(tok);
                }
            }
        } else {
            // This line is empty, return it with just the leading spaces.
        }

        return new Line(this.number, this.offset, this.length, this.expectedIndentation, nCont);
    }

    @Override
    public int compareTo(Integer o) {
        if (o < offset) {
            return 1;
        } else if (o >= offset + length) {
            return -1;
        } else {
            return 0;
        }
    }

}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser.tokenizer;

/**
 *
 * @author Gaurav Manek
 */
public class LineIndentMetadata {
    public final int charsBefore;
    public final int charsAfter;
    public final boolean updateCharPositionIndent;

    public LineIndentMetadata(int charsBefore, int charsAfter, boolean updateCharPositionIndent) {
        this.charsBefore = charsBefore;
        this.charsAfter = charsAfter;
        this.updateCharPositionIndent = updateCharPositionIndent;
    }
}

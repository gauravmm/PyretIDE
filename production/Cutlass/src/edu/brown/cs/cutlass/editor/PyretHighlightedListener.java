/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;

/**
 *
 * @author Gaurav Manek
 */
public interface PyretHighlightedListener {
    public void highlighted(TokenParserOutput output);
}

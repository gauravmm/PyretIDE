/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser.tokenizer;

import java.util.regex.Pattern;

/**
 *
 * @author Gaurav Manek
 */
public abstract class TokenTypePairedClose extends TokenTypePaired {

    public TokenTypePairedClose(Pattern pattern) {
        super(pattern);
    }

    /**
     * Get the TokenTypePairedOpen that corresponds to the starting token for
     * this.
     *
     * @return The TokenPaired that corresponds to the starting token.
     */
    public abstract TokenTypePairedOpen getMatchingTokenType() throws IllegalArgumentException;

}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser;

import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;

/**
 * Represents 
 * @author Gaurav Manek
 */
public class PyretFunction extends PyretBlock {
    
    private final TokenPairedOpening scopeOpen;
    
    public PyretFunction(PyretLocation location, String name, TokenPairedOpening scopeOpen) {
        super(location, name);
        this.scopeOpen = scopeOpen;
    }
    
}

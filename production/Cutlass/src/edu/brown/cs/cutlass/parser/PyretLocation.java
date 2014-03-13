/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

/**
 *
 * @author Gaurav Manek
 */
public class PyretLocation {

    /**
     * The number of characters from the start of file that this location points
     * to.
     */
    public final long offset;
    
    public PyretLocation(long offset) {
        this.offset = offset;
    }
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser;

/**
 *
 * @author Gaurav Manek
 */
abstract class PyretBlock {
    
    protected final PyretLocation location;
    protected final String name;

    public PyretBlock(PyretLocation location, String name) {
        this.location = location;
        this.name = name;
    }

    public PyretLocation getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
    
}

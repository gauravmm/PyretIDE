/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.parser;

/**
 *
 * @author Gaurav Manek
 */
public class PyretDataVariant extends PyretBlock {

    private final PyretData data;
    private final String definition;

    public PyretDataVariant(PyretLocation location, String name, PyretData data, String definition) {
        super(location, name);
        this.data = data;
        this.definition = definition;
    }

    public PyretData getData() {
        return data;
    }

    public String getDefinition() {
        return definition;
    }
    
}

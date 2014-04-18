/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.parser;

/**
 *
 * @author Gaurav Manek
 */
public class PyretFunctionCall extends PyretBlock {
    
    private final PyretFunction function;

    public PyretFunctionCall(PyretLocation location, PyretFunction function) {
        super(location, function.getName());
        this.function = function;
    }

    public PyretFunction getFunction() {
        return function;
    }
    
}

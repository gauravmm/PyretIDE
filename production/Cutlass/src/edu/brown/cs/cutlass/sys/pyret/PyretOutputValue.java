/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.sys.pyret;

/**
 *
 * @author Gaurav Manek
 */
public class PyretOutputValue {
    private final AbstractPyretAccess.Stream stream;
    private final String data;

    public AbstractPyretAccess.Stream getStream() {
        return stream;
    }

    public String getData() {
        return data;
    }

    public PyretOutputValue(AbstractPyretAccess.Stream stream, String data) {
        this.stream = stream;
        this.data = data;
    }
    
}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.util.io;

/**
 * Represents a unique resource identifier for AbstractIO. T is the underlying
 * type of the representation, typically String or Path.
 *
 * @author Gaurav Manek
 */
public abstract class AbstractIdentifier<T> {

    protected final T id;

    public AbstractIdentifier(T id) {
        this.id = id;
    }

    public T get() {
        return id;
    }
    
    //public abstract <T> AbstractIdentifier<T> fromString(String s);
}

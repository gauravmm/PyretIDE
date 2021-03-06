/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

/**
 * Represents an exception thrown by implementations of AbstractIO. All
 * implementations should ONLY throw this exception and provide a human-readable
 * message.
 *
 * @author Gaurav Manek
 */
public class AbstractIOException extends Exception {

    public AbstractIOException() {
        this("Abstract IO has encountered an error.");
    }

    public AbstractIOException(String message) {
        super(message);
    }

    public AbstractIOException(Throwable cause) {
        super(cause);
    }
}

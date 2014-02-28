/*
 * Project 01 - Autocorrect
 * CSCI 0320, Brown University, Fall 2014
 * Gaurav Manek
 */

package edu.brown.cs.cutlass.util.clargs;

/**
 * Exception thrown when command line arguments are incorrectly structured.
 * @author Gaurav Manek
 */
@SuppressWarnings("serial")
public class IncorrectCommandlineArgumentException extends RuntimeException {
    
    /**
     * Thrown when the command line arguments are incorrectly structured.
     */
    public IncorrectCommandlineArgumentException() {
        super("Commandline Arguments are incorrect!");
    }

    /**
     * Thrown when the command line arguments are incorrectly structured.
     * @param message Custom message to use.
     */
    public IncorrectCommandlineArgumentException(String message) {
        super(message);
    }
}

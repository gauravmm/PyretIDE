/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

/**
 * Converts a string-encoded AbstractIdentifer into an object.
 *
 * @author Miles Holland
 */
public interface AbstractIdentifierParser<T extends AbstractIdentifier> {

    /**
     * Convert an AbstractIdentifier from a String to an Object.
     *
     * @param in The String to parse.
     * @return An AbstractIdentifier of type T.
     * @throws IllegalArgumentException if in does not represent a valid
     * AbstractIdentifier.
     */
    public T parse(String in) throws IllegalArgumentException;

}

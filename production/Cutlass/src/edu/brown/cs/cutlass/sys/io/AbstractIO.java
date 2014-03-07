/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.io;

import edu.brown.cs.cutlass.util.Option;
import java.util.List;

/**
 * This interface is used to provide centralized access to I/O from any source
 * (i.e. disk, though Java Web Start's Sandbox IO and/or some other I/O). In
 * this paradigm, all files are considered resources, and the underlying
 * implementations are free to load them from any source, such as a hard drive
 * or a remote server or even a stack of punched cards.
 *
 * For the sake of simplicity, these are all blocking IO calls.
 *
 * T is the underlying representation of the AbstractIdentifier used.
 *
 * @author Gaurav Manek
 */
public interface AbstractIO<T extends AbstractIdentifier> {

    /**
     * Request a configuration file. This is a file that is used to store
     * metadata about the user or the program.
     *
     * @param identifier A unique identifier for a resource.
     * @return A String with the entire contents of the resource (if it exists).
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be read.
     */
    public List<String> getConfigurationFile(String identifier) throws AbstractIOException;

    /**
     * Store a configuration file. This is a file that is used to store metadata
     * about the user or the program.
     *
     * @param identifier A unique identifier for a resource.
     * @param contents The new value to store in the resource.
     * @return A String with the entire contents of the resource (if it exists).
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be written to.
     */
    public void setConfigurationFile(String identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException;

    /**
     * Request a user file. This is a file that contains source code.
     *
     * @param identifier A unique identifier for a resource.
     * @return A String with the entire contents of the resource (if it exists).
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be read.
     */
    public List<String> getUserFile(T identifier) throws AbstractIOException;

    /**
     * Store a user file. This is a file that contains source code.
     *
     * @param identifier A unique identifier for a resource.
     * @param contents The new value to store in the resource.
     * @return A String with the entire contents of the resource (if it exists).
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be written to.
     */
    public void setUserFile(T identifier, Iterable<? extends CharSequence> contents) throws AbstractIOException;

    /**
     * Ask the user to pick a file to save. This is a file that contains source
     * code.
     *
     * @return A String that is a unique identifier for a resource.
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be written to.
     */
    public Option<T> requestUserFileDestination() throws AbstractIOException;

    /**
     * Ask the user to pick a file to load. This is a file that contains source
     * code.
     *
     * @return An Option containing an AbstractIdentifier that is a unique
     * identifier for a resource, or empty if the user chooses to cancel.
     * @throws AbstractIOException If the underlying implementation cannot
     * locate the resource or if the resource cannot be written to.
     */
    public Option<T> requestUserFileSource() throws AbstractIOException;

    /**
     * Get an object that is used to parse AbstractIdentifiers.
     *
     * @return An AbstractIdentifierParser that is used to parse the
     * AbstractIdentifiers for this AbstractIO implementation.
     */
    public AbstractIdentifierParser<T> getIdentifierParser();
}

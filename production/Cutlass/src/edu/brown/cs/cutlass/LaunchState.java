/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Holds (and handles serializing/unserializing of) the program state at launch.
 * This includes the files currently open. This is an immutable object.
 *
 * @author Gaurav Manek
 */
class LaunchState<T extends AbstractIdentifier> {

    /**
     * 
     */
    private final List<T> openFiles;
    
    /**
     * Defaults to 0.
     */
    private final int currentTabId;

    /**
     *
     * @param openFiles
     */
    private LaunchState(ArrayList<T> openFiles, int currentTabId) {
        this.openFiles = openFiles;
        this.currentTabId = currentTabId;
    }

    /**
     *
     */
    public LaunchState() {
        openFiles = new ArrayList();
        this.currentTabId = 0;
    }

    /**
     *
     * @return
     */
    public List<T> getOpenFiles() {
        return Collections.unmodifiableList(openFiles);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("Not yet written");
    }

    // R is the same type as T, but defined in the static scope.
    /**
     * Convert a LaunchState-encoded file into a LaunchState object
     *
     * Note: If an AbstractIdentifer cannot be parsed, a warning is logged and
     * the function ignores it.
     *
     * @param <R> The underlying type of the I/O system, as defined by the
     * AbstractIdentifier.
     * @param in The List&lt;String&gt;, each element containing one line of the
     * LaunchState file.
     * @param parser A converter that takes an AbstractIdentifier as a String
     * and converts it to an Object.
     * @return A LaunchState containing the state of the program at previous
     * shutdown.
     * @throws IllegalArgumentException If the structure of the file is
     * incorrect.
     */
    public static <R extends AbstractIdentifier> LaunchState<R> fromString(List<String> in, AbstractIdentifierParser<R> parser) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not yet written");
    }

    /**
     * Produce a LaunchState that represents the state of the program at
     * closing.
     *
     * @param <R>
     * @param filesOpen
     * @return
     */
    public static <R extends AbstractIdentifier> LaunchState<R> toState(Collection<R> filesOpen, int tabId) {
        throw new UnsupportedOperationException("Not yet written");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.brown.cs.cutlass;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.sys.io.AbstractIdentifierParser;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Lumberjack.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Holds (and handles serializing/unserializing of) the program state at launch.
 * This includes the files currently open. This is an immutable object.
 *
 * @author Gaurav Manek T is an arrPointer
 */
public class LaunchState<T extends AbstractIdentifier> {
    // T is an AbstractIdentifer that identifies a unique resource on the 
    // current I/O media (Ex. a path object to a space in disk).

    private static final String sep = "\t";
    private static final String comment = "#";
    private static final String newline = System.getProperty("line.separator");

    /**
     *
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
        openFiles = new ArrayList<>();
        this.currentTabId = 0;
    }

    /**
     *
     * @return
     */
    public List<T> getOpenFiles() {
        return Collections.unmodifiableList(openFiles);
    }

    /**
     * Provides the serialized output of the current LaunchState as a List of
     * String, such that each String corresponds to one line in the output file.
     *
     * @return A serialized representation of this object as a List of String.
     */
    public List<String> toStringArr() {
        List<String> builtString = new ArrayList<>();
        builtString.add("CURRENT_TAB" + sep + Integer.toString(currentTabId));
        for (T t : openFiles) {
            builtString.add("FILE" + sep + t.toString());
        }
        return builtString;
    }

    @Override
    public String toString() {
        // StringBuilders are more efficient than String concatenation.
        StringBuilder sb = new StringBuilder();
        List<String> tmp = this.toStringArr();
        for (String s : tmp) {
            sb.append(s).append(newline);
        }
        return sb.toString();
    }

    // R is the same type as T, but defined in the static scope.
    /**
     * Convert a LaunchState-encoded file into a LaunchState object.
     *
     * Note: If an AbstractIdentifer cannot be parsed, a warning is logged and
     * the function ignores it. File path representation anything we decide on
     * later.
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
        //create pieces that will eventually be placed in launch state.
        ArrayList<R> openFiles0 = new ArrayList<>();
        int currentTabID0 = -1; //not instantiated in case it's not found in file.

        //loop through input strings
        for (String line : in) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith(comment)) {
                // if empty or comment line, do nothing
            } else {
                String[] parts = line.split("\t");
                if (parts.length != 2) {
                    Lumberjack.log(Level.ERROR, "Line did not have two sections (header <tab> data)");
                    throw new IllegalArgumentException("Error: This line does not have 2 parts.");
                }

                switch (parts[0]) {
                    case ("CURRENT_TAB"): //Current tab: try to parse it as a number, throw errors if it can't
                        try {
                            currentTabID0 = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            Lumberjack.log(Level.ERROR, "current tab line was formatted wrong.");
                            throw new IllegalArgumentException("Error: the current tab line did not contain a parseable integer.");
                        }
                        break;
                    case ("FILE"): //File, try to parse it with the given AIDParser, ignore it if it fails
                        try {
                            openFiles0.add(parser.parse(parts[1]));
                        } catch (IllegalArgumentException e) {
                            Lumberjack.log(Level.WARN, "Warning: Could not properly parse the following line during launchstate creation: " + line);
                        }
                        break;
                    //Default to errors, since we've already checked for blank/commented lines.
                    default:
                        Lumberjack.log(Level.ERROR, "Line did not match any known entry type");
                        throw new IllegalArgumentException("Error: This line did not match any known entry type.");
                }
            }
        }
        
        //this check is important, please do not remove it.
        if(currentTabID0 < 0 || currentTabID0 >= openFiles0.size()){
             Lumberjack.log(Level.ERROR, "Tab is was not in bounds");
                        throw new IllegalArgumentException("Error: Tab is was not in bounds.");
  
        }
        // Note: LaunchState is not responsible for checking the consistency of the data it holds.
        // Actual creating and returning of a LaunchState
        return new LaunchState<>(openFiles0, currentTabID0);
    }

    /**
     * Produce a LaunchState that represents the state of the program at
     * closing.
     *
     * @param filesOpen The files currently open.
     * @param tabId The current tab in the foreground.
     * @return A LaunchState object with the current state.
     */
    public static <R extends AbstractIdentifier> LaunchState<R> toState(Collection<R> filesOpen, int tabId) {
        return new LaunchState<>(new ArrayList<>(filesOpen), tabId);
    }
}

/*
 * Project 01 - Autocorrect
 * CSCI 0320, Brown University, Fall 2014
 * Gaurav Manek
 */
package edu.brown.cs.cutlass.util.clargs;

/**
 *
 * @author Gaurav Manek
 */
public class CLArg {

    /**
     * An arbitrary string associated with this CLArg.
     */
    public final String text;

    /**
     * An array of switches used to represent this argument.
     */
    public final String[] switches;

    /**
     * If true, this argument may be followed by a variable number of data entries.
     */
    public final boolean variableArgCount;

    /**
     * The number of data entries expected after the switch.
     */
    public final int argCount;

    private CLArg(String text, String[] switches, boolean variableArgCount, int argCount) {
        this.text = text;
        this.switches = switches;
        this.variableArgCount = variableArgCount;
        this.argCount = argCount;
    }
    
    /**
     *
     * @param text
     * @param switches
     * @param variableArgCount
     */
    public CLArg(String text, String[] switches, boolean variableArgCount) {
        this(text, switches, variableArgCount, -1);
        if (variableArgCount == false) {
            throw new RuntimeException("Wrong construction of CLArg.");
        }
    }

    /**
     *
     * @param text
     * @param switches
     * @param argCount
     */
    public CLArg(String text, String[] switches, int argCount) {
        this(text, switches, false, argCount);
    }

    /**
     *
     * @param text
     * @param switches
     */
    public CLArg(String text, String[] switches) {
        this(text, switches, false, 0);
    }
    
    /**
     *
     * @param text
     * @param switches
     * @param argCount
     */
    public CLArg(String text, String switches, int argCount) {
        this(text, new String[]{switches}, false, argCount);
    }

    /**
     *
     * @param text
     * @param switches
     */
    public CLArg(String text, String switches) {
        this(text, new String[]{switches}, false, 0);
    }
}

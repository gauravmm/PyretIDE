/*
 * Project 03 - Bacon
 * CSCI 0320, Brown University, Fall 2014
 * Gaurav Manek
 */

package edu.brown.cs.cutlass;

/**
 *
 * @author Gaurav Manek
 */
public enum ExitCode {
    OK(0, false, "if OK,"), 
    CLARGS(1, "if the command-line options cannot be parsed,"),
    NO_CONFIG(2, "if the configuration file data cannot be loaded.");
    
    private final int code;
    private final boolean isError;
    private final String helpText;

    ExitCode(int code, boolean isError, String helpText) {
        this.code = code;
        this.isError = isError;
        this.helpText = helpText;
    }

    ExitCode(int code, String helpText) {
        this.code = code;
        this.isError = true;
        this.helpText = helpText;
    }

    public int getCode() {
        return code;
    }

    public boolean isErrorCode() {
        return isError;
    }

    public String getHelpText() {
        return helpText;
    }
}

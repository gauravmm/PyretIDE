/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class provides quick access to the status and raw output of one instance
 * of Pyret.
 *
 * @author Gaurav Manek
 */
class PyretMonitor {

    /**
     * Get
     * 
     * @return 
     */
    public OutputStream getOut() {
        throw new UnsupportedOperationException("Not written yet!");
    }

    /**
     * Get
     * 
     * @return 
     */
    public OutputStream getErr() {
        throw new UnsupportedOperationException("Not written yet!");
    }

    /**
     * Get
     * 
     * @return 
     */
    public InputStream getIn() {
        throw new UnsupportedOperationException("Not written yet!");
    }

    /**
     * Check if this instance of Pyret is running.
     * 
     * @return 
     */
    public boolean isRunning() {
        throw new UnsupportedOperationException("Not written yet!");
    }

    /**
     * Check if this running instance of Pyret supports REPL (i.e. is it
     * available for user input, or has it catastrophically failed such that it
     * can no longer accept user input.)
     *
     * @return true if this instance can respond to dynamically entered code,
     * false otherwise.
     */
    public boolean isREPL() {
        return false;
    }

}

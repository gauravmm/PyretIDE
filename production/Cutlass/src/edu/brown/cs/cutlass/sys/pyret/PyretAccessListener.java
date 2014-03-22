/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.sys.pyret;

/**
 *
 * @author Gaurav Manek
 */
public interface PyretAccessListener {

    public void handlePyretAccessOutput(PyretOutputValue output);

    public void handlePyretAccessEnds(PyretTerminationValue output);
}

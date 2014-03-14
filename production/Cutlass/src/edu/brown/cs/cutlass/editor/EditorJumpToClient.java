/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

/**
 *
 * @author Gaurav Manek
 */
public interface EditorJumpToClient {

    /**
     * Request the client move the cursor position to a particular location.
     * Temporarily, this uses offset.
     *
     * @param offset The number of characters to move to, counted from the start
     * of the file.
     */
    public void handleJumpTo(long offset);

}

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
     *
     * @param offset The number of characters to move to, counted from the start
     * of the file.
     */
    public void handleJumpTo(int offset);

    /**
     * Return an object representing a request to move the cursor position to a
     * particular location.
     *
     * @param offset The number of characters to move to, counted from the start
     * of the file.
     * @return An object representing the above request.
     */
    public EditorJumpTo createJumpTo(int offset);

}

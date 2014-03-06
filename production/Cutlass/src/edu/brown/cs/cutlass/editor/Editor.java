/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.quicknav.QuickNavigationJumpClient;

/**
 * Interface exposing the interactions offered by the editor
 *
 * @author Gaurav Manek
 */
public interface Editor extends AutoCloseable, QuickNavigationJumpClient {

    //
    // Pyret Interaction:
    //
    /**
     * Run the Pyret program.
     */
    public void run();

    /**
     * Stop a running Pyret program.
     *
     * @throws IllegalStateException if the program is not running.
     */
    public void halt() throws IllegalStateException;

    //
    // Clipboard:
    //
    /**
     * Copy the current selection to clipboard and delete it in the buffer.
     *
     * @throws IllegalStateException if nothing is selected.
     */
    public void clipboardCut() throws IllegalStateException;

    /**
     * Copy the current selection to clipboard and leave the buffer untouched.
     *
     * @throws IllegalStateException if nothing is selected.
     */
    public void clipboardCopy() throws IllegalStateException;

    /**
     * Copy the clipboard contents to the current cursor position in the buffer.
     */
    public void clipboardPaste();

    //
    // Data Access:
    //
    /**
     * Get the contents of the editor pane as a String, suitable for saving.
     *
     * @return A String containing the entire contents of the editing buffer.
     */
    public String getBuffer();

    /**
     * Check if any text is selected.
     *
     * @return true if some text is selected, false otherwise.
     */
    public boolean hasSelection();

    /**
     * Get the current selection.
     *
     * @return The currently selected text as a String, or an empty String if
     * nothing is selected.
     */
    public String getSelection();
}

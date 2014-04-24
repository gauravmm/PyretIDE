/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.util.Option;

/**
 * Interface exposing the interactions offered by the editor
 *
 * @author Gaurav Manek
 * @param <T>
 */
public abstract class Editor<T extends AbstractIdentifier> extends javax.swing.JPanel implements AutoCloseable {

    private Option<T> identifier;
    private boolean changedSinceLastSave;
    private final boolean isEditorWindow;

    public Editor(boolean isEditorWindow) {
        super();
        this.isEditorWindow = isEditorWindow;
        this.changedSinceLastSave = true;
        this.identifier = new Option();
    }

    public boolean isEditorWindow() {
        return this.isEditorWindow;
    }

    //
    // Pyret Interaction:
    //
    /**
     * Run the Pyret program.
     */
    public void run() {
        throw defaultResponse();
    }

    /**
     * Stop a running Pyret program.
     *
     * @throws IllegalStateException if the program is not running.
     */
    public void halt() throws IllegalStateException {
        defaultResponse();
    }

    //
    // Clipboard:
    //
    /**
     * Copy the clipboard contents to the current cursor position in the buffer.
     *
     * @param paste
     */
    public void paste(String paste) {
        throw defaultResponse();
    }

    //
    // Data Access:
    //
    /**
     * Get the contents of the editor pane as a String, suitable for saving.
     *
     * @return A String containing the entire contents of the editing buffer.
     */
    public String getBuffer() {
        throw defaultResponse();
    }

    /**
     * Check if any text is selected.
     *
     * @return true if some text is selected, false otherwise.
     */
    public boolean hasSelection() {
        throw defaultResponse();
    }

    /**
     * Reindent the code.
     */
    public void reindent() {
        throw defaultResponse();
    }

    /**
     * Perform and undo if possible.
     */
    public void undo() {
        throw defaultResponse();
    }

    /**
     * Perform a redo if possible.
     */
    public void redo() {
        throw defaultResponse();
    }

    /**
     * Get the current selection.
     *
     * @return The currently selected text as a String, or an empty String if
     * nothing is selected.
     */
    public String getSelection() {
        throw defaultResponse();
    }

    private RuntimeException defaultResponse() {
        if (isEditorWindow()) {
            return new IllegalStateException("This is not a valid editor window.");
        } else {
            return new UnsupportedOperationException("Not supported yet.");
        }
    }

    public void deleteSelection() {
        throw defaultResponse();
    }

    public void selectAll() {
        throw defaultResponse();
    }

    public Option<T> getIdentifier() {
        if (!isEditorWindow()) {
            throw new IllegalStateException("This is not a valid editor window.");
        }
        return identifier;
    }

    public void setIdentifier(T identifier) {
        if (!isEditorWindow()) {
            throw new IllegalStateException("This is not a valid editor window.");
        }
        this.identifier = new Option<>(identifier);
    }

    public boolean isChangedSinceLastSave() {
        if (!isEditorWindow()) {
            throw new IllegalStateException("This is not a valid editor window.");
        }
        return changedSinceLastSave;
    }

    public void setChangedSinceLastSave(boolean changedSinceLastSave) {
        if (!isEditorWindow()) {
            throw new IllegalStateException("This is not a valid editor window.");
        }
        this.changedSinceLastSave = changedSinceLastSave;
    }

    @Override
    public void close() throws RuntimeException {

    }
}

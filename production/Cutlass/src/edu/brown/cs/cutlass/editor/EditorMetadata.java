/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.sys.io.AbstractIdentifier;
import edu.brown.cs.cutlass.util.Option;

/**
 *
 * @author Gaurav Manek
 * @param <T> The AbstractIdentifier type to use.
 */
public class EditorMetadata<T extends AbstractIdentifier> {

    private final boolean isEditorWindow;
    private Option<T> identifier;
    private boolean changedSinceLastSave;

    public EditorMetadata(boolean isEditorWindow) {
        this.isEditorWindow = isEditorWindow;
        identifier = new Option<>();
        changedSinceLastSave = true;
    }

    public Option<T> getIdentifier() {
        if(!isEditorWindow){
            throw new IllegalStateException("This is not a valid editor window.");
        }
        return identifier;
    }

    public void setIdentifier(T identifier) {
        if(!isEditorWindow){
            throw new IllegalStateException("This is not a valid editor window.");
        }
        this.identifier = new Option<>(identifier);
    }

    public boolean isChangedSinceLastSave() {
        if(!isEditorWindow){
            throw new IllegalStateException("This is not a valid editor window.");
        }
        return changedSinceLastSave;
    }

    public void setChangedSinceLastSave(boolean changedSinceLastSave) {
        if(!isEditorWindow){
            throw new IllegalStateException("This is not a valid editor window.");
        }
        this.changedSinceLastSave = changedSinceLastSave;
    }

    public boolean isEditorWindow() {
        return isEditorWindow;
    }

}

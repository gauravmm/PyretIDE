/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/** This undomanager can have the significance of events it receives manually
 * be set to false. By changing this significance variable, this manager can deal with
 * our highlighting scheme without creating a heap of significant events every 
 * time the user types something in.
 *
 * @author miles
 */
       
public class ControlledUndoManager extends UndoManager {
    private boolean isHighlighting;

    public ControlledUndoManager() {
        super();
        this.setLimit(5000);
        isHighlighting = false;
    }

    /** Sets the isHighlighting value
     * 
     * @param newSignificance 
     */
    public void setIsHighlighting(boolean newSignificance) {
        isHighlighting = newSignificance;
    }

    @Override
    public boolean addEdit(UndoableEdit anEdit){
       //overridded to make edits insignificant if highlighting
       if(isHighlighting){
           return super.addEdit(new ControlledEdit(anEdit, false));
       }
       return super.addEdit(anEdit);
    }

    /** A hidden class of edits that serve solely to mask normal edits with
     * a new significance variable. Had to be done this way due to the fact
     * that edits cannot have their significance changed after instantiation
     * for some reason.
     */
    private class ControlledEdit implements UndoableEdit{
        private final UndoableEdit realEdit;
        private final boolean significance;
        
        public ControlledEdit(UndoableEdit edit, boolean significance0) {
            realEdit = edit;
            significance = significance0;
        }

        @Override
        public void undo() throws CannotUndoException {
            realEdit.undo();
        }

        @Override
        public boolean canUndo() {
            return realEdit.canUndo();
        }

        @Override
        public void redo() throws CannotRedoException {
            realEdit.redo();
        }

        @Override
        public boolean canRedo() {
            return realEdit.canRedo();
        }

        @Override
        public void die() {
            realEdit.die();
        }

        @Override
        public boolean addEdit(UndoableEdit anEdit) {
            return realEdit.addEdit(anEdit);
        }

        @Override
        public boolean replaceEdit(UndoableEdit anEdit) {
            return realEdit.replaceEdit(anEdit);
        }

        @Override
        public boolean isSignificant() {
            return significance;
        }

        @Override
        public String getPresentationName() {
            return realEdit.getPresentationName();
        }

        @Override
        public String getUndoPresentationName() {
            return realEdit.getUndoPresentationName();
        }

        @Override
        public String getRedoPresentationName() {
            return realEdit.getRedoPresentationName();
        }

    }
}
/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author miles
 */
       
public class ControlledUndoManager extends UndoManager {
    private boolean isHighlighting;

    public ControlledUndoManager(){
        super();
        this.setLimit(5000);
        isHighlighting = false;
    }

    public void setIsHighlighting(boolean newSignificance) {
        isHighlighting = newSignificance;
    }

    @Override
    public boolean addEdit(UndoableEdit anEdit){
       System.out.println(this.canUndo());
       if(isHighlighting){
           return super.addEdit(new ControlledEdit(anEdit, false));
       }
       return super.addEdit(anEdit);
    }

    private class ControlledEdit implements UndoableEdit{
        private UndoableEdit realEdit;
        private boolean significance;
        
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
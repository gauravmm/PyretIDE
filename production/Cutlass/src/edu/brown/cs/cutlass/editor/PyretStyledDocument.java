/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.util.Lumberjack;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/** A Styled document that has special undo/redo/highlight interactions.
 * This contains its own SyntaxHighlighter and ControlledUndoManager, which
 * it uses to make sure that highlights happen after each user edit, without
 * creating a massive heap of highlight-related edits.
 *
 * @author miles
 */
public class PyretStyledDocument extends DefaultStyledDocument {

    private final SyntaxHighlighter highlighter;
    private final StyledUndoPane parent;
    public final ControlledUndoManager undoer;

    PyretStyledDocument(StyledUndoPane parent) {
        super();
        highlighter = new SyntaxHighlighter(this, parent);
        if (parent == null) {
            throw new IllegalArgumentException("parent must not be null!");
        }
        this.parent = parent;

        undoer = new ControlledUndoManager();
        this.addUndoableEditListener(undoer);
        
    }

    @Override
    public void insertString(int offset, String content, AttributeSet s) {
        insertStringWithoutHighlight(offset, content, s);
        highlight();
    }

    @Override
    public void remove(int offset, int amt) {
        removeWithoutHighlight(offset, amt);
        highlight();
    }

    public void highlight() {
        int posDot = parent.getCaret().getDot();
        int posMark = parent.getCaret().getMark();

        undoer.setIsHighlighting(true);

        highlighter.highlight(posMark == posDot ? posDot : -1, false);

        undoer.setIsHighlighting(false);

        parent.getCaret().setDot(posMark);
        parent.getCaret().moveDot(posDot);
    }

    public void highlightAndIndent() {
        int posDot = parent.getCaret().getDot();

        undoer.setIsHighlighting(true);

        int newDot = highlighter.highlight(posDot, true);

        undoer.setIsHighlighting(false);

        parent.getCaret().setDot(newDot);
    }

    public void removeWithoutHighlight(int offSet, int amt) {
        try {
            super.remove(offSet, amt);
        } catch (BadLocationException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }

    public void insertStringWithoutHighlight(int offset, String content, AttributeSet s) {
        try {
            super.insertString(offset, content, s);
        } catch (BadLocationException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }

    public List<Integer> getLineStartOffsets() {
        return highlighter.getLastLineStartOffsets();
    }

    void showCallGraph() {
        highlighter.showCallGraph();
    }

    @Override
    public void replace(int start, int end, String replacement, AttributeSet s) {
        try {
            //undoer.setIsHighlighting(false);
            super.remove(start, (end - start));
            super.insertString(start, replacement, s);
            //undoer.setIsHighlighting(true);
        } catch (BadLocationException ex) {
            Logger.getLogger(PyretStyledDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

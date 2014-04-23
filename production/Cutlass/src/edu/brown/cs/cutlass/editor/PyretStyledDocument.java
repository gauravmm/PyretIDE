/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.util.Lumberjack;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Position;

/**
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

}

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.syntaxhighlighter.SyntaxHighlighter;
import edu.brown.cs.cutlass.util.Lumberjack;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author miles
 */
public class PyretStyledDocument extends DefaultStyledDocument {

    private final SyntaxHighlighter highlighter;
    private final StyledUndoPane parent;

    PyretStyledDocument(StyledUndoPane parent) {
        super();
        highlighter = new SyntaxHighlighter(this);
        if (parent == null) {
            throw new IllegalArgumentException("parent must not be null!");
        }
        this.parent = parent;
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

    private void highlight() {
        int pos = parent.getCaretPosition();
        highlighter.highlight();
        parent.setCaretPosition(pos);
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

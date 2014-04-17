/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.syntaxhighlighter.SyntaxHighlighter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author miles
 */
public class PyretStyledDocument extends DefaultStyledDocument {

    private SyntaxHighlighter highlighter;
    
    public PyretStyledDocument() {
        super();
        highlighter = new SyntaxHighlighter(this);
    }

    @Override
    public void insertString(int offset, String content, AttributeSet s) {
        try {
            super.insertString(offset, content, s);
        } catch (BadLocationException ex) {
            Logger.getLogger(PyretStyledDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        highlighter.highlight();
    }

    @Override
    public void remove(int offSet, int amt) {
        try {
            super.remove(offSet, amt);
        } catch (BadLocationException ex) {
            Logger.getLogger(PyretStyledDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        highlighter.highlight();
    }

    public void fakeRemove(int offSet, int amt) {
        try {
            super.remove(offSet, amt);
        } catch (BadLocationException ex) {
            Logger.getLogger(PyretStyledDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addString(int offset, String content, AttributeSet s) {
        try {
            super.insertString(offset, content, s);
        } catch (BadLocationException ex) {
            Logger.getLogger(PyretStyledDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

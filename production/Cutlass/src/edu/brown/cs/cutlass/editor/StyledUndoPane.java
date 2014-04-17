/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.syntaxhighlighter.SyntaxHighlighter;
import edu.brown.cs.cutlass.parser.tokenizer.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane implements DocumentListener{
    
    private UndoManager undoer;
   // private SyntaxHighlighter overDoc;
    
    public StyledUndoPane(CharSequence fileContent){
        super();
        
        StyledDocument document = new PyretStyledDocument(fileContent);

        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        document.addDocumentListener(this);
    }
    
    
    public static void main(String[] args){
        StyledUndoPane test = new StyledUndoPane("fun to-celsius(f): "
                + "\nf + 1"
                + "\nwhere:"
                + "\nto-celsius(1) is 2"
                + "\nend");
        
        JFrame j = new JFrame("test");
        j.add(test);
        j.setDefaultCloseOperation(3);
        j.setSize(500,500);
        j.setVisible(true);
        
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        //overDoc.highlight();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //overDoc.highlight();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //overDoc.highlight();
    }
    
}

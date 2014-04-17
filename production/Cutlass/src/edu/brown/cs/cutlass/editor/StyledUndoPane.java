/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.editor.syntaxhighlighter.SyntaxHighlighter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane implements DocumentListener{
    
    private UndoManager undoer;
    private final SyntaxHighlighter overDoc;
    
    public StyledUndoPane(CharSequence fileContent){
        super();
        
        StyledDocument document = new DefaultStyledDocument();
        overDoc = new SyntaxHighlighter(document);
        try {
            document.insertString(0, fileContent.toString(), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(StyledUndoPane.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        overDoc.highlight();
        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        document.addDocumentListener(this);
    }
    
    
    public static void main(String[] args){
        StyledUndoPane test = new StyledUndoPane("def function():\ncase(Link):\nend\nend");
        JFrame j = new JFrame("test");
        j.add(test);
        j.setSize(500,500);
        j.setVisible(true);
        
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        rehighlight();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        rehighlight();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        rehighlight();
    }

    private void rehighlight() {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                overDoc.highlight();
            }
            
        });
    }
    
}

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

/** An extended JEditorPane. Has the following features:
 * ~Highlights contents according to highlighting rules set by
 * the SyntaxHighlighter class.
 * ~Maintains an undo/redo stack and has visible undo/redo methods
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane implements DocumentListener {

    private UndoManager undoer;
    private final SyntaxHighlighter overDoc;
    private PyretStyledDocument document;

    public final static String testStr = "data BinTree:\n"
            + "  | leaf\n"
            + "  | node(value, left, right)\n"
            + "end\n"
            + "\n"
            + "fun tree-sum(t):\n"
            + "  doc: \"Calculate the sum of node values\"\n"
            + "  cases(BinTree) t:\n"
            + "    | leaf => 0\n"
            + "    | node(v, l, r) =>\n"
            + "      v + tree-sum(l) + tree-sum(r)\n"
            + "  end\n"
            + "where:\n"
            + "  tree-sum(leaf) is 0\n"
            + "  node4 = node(4, leaf, leaf)\n"
            + "  tree-sum(node(5, node4, leaf)) is 9\n"
            + "end";

    public StyledUndoPane(CharSequence fileContent) {
        super();

        document = new PyretStyledDocument();
        
        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        document.addDocumentListener(this);
        
        overDoc = new SyntaxHighlighter(document);
        overDoc.highlight(fileContent.toString());
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
        
        /*SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    overDoc.highlight(document.getText(0, document.getLength()));
                } catch (BadLocationException ex) {
                    Logger.getLogger(StyledUndoPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });*/
    }

    public static void main(String[] args) {
        StyledUndoPane test = new StyledUndoPane(testStr);
        JFrame j = new JFrame("test");
        j.add(test);
        j.setDefaultCloseOperation(3);
        j.setSize(500, 500);
        j.setVisible(true);

    }
}

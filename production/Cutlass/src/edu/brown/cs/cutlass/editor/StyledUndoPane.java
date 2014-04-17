/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

/**
 * An extended JEditorPane. Has the following features: ~Highlights contents
 * according to highlighting rules set by the SyntaxHighlighter class.
 * ~Maintains an undo/redo stack and has visible undo/redo methods
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane {

    private UndoManager undoer;
    private final PyretStyledDocument document;

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

        document = new PyretStyledDocument(this);

        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        document.insertString(0, fileContent.toString(), null);

        /* document.addDocumentListener(new DocumentListener() {}); */
    }

    public static void main(String[] args) {
        StyledUndoPane test = new StyledUndoPane(testStr);
        JFrame j = new JFrame("test");
        j.add(test);
        j.setDefaultCloseOperation(3);
        j.setSize(500, 500);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

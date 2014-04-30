/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.util.Option;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
/**
 *
 * @author Gaurav Manek
 */
public class StyledUndoPaneTest {

    public StyledUndoPaneTest() {
    }

   

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

    public static void main(String[] args) {
        StyledUndoPane test;
        test = new StyledUndoPane(testStr, new PyretHighlightedListener() {
            @Override
            public void highlighted(TokenParserOutput output, Option<Token> currentToken, EditorJumpToClient client) {
            }
        });
        /*JEditorPane test = new JEditorPane();
        Document d = new DefaultStyledDocument(null);
        UndoManager u = new UndoManager();
        d.addUndoableEditListener(u);
        test.setDocument(d);*/

        PaneTester tester = new PaneTester(test);

        JFrame j = new JFrame("test");
        j.setLayout(new BorderLayout());

        j.add(test, BorderLayout.CENTER);

        JButton but = new JButton("undo");
        but.addActionListener(tester);
        j.add(but, BorderLayout.WEST);
        but = new JButton("redo");
        but.addActionListener(tester);
        j.add(but, BorderLayout.EAST);

        j.setDefaultCloseOperation(3);
        j.setSize(500, 500);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Hidden class used for testing. Can listen to undo/redo events and try
     * undo/redoing the document content.
     *
     */
    private static class PaneTester implements ActionListener {

        private StyledUndoPane s;

        private PaneTester(StyledUndoPane s0) {
            s = s0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("undo")) {
                s.undo();
            } else {
                s.redo();
            }
        }
    }
    
        private static class PaneTester2 implements ActionListener {

        private UndoManager s;

        private PaneTester2(UndoManager s0) {
            s = s0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("undo")) {
                s.undo();
            } else {
                s.redo();
            }
        }
    }

}

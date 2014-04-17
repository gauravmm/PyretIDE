/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import edu.brown.cs.cutlass.util.Lumberjack;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

/**
 * An extended JEditorPane. Has the following features: ~Highlights contents
 * according to highlighting rules set by the SyntaxHighlighter class.
 * ~Maintains an undo/redo stack and has visible undo/redo methods
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane{

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
        undoer = new UndoManager();
        
        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        
        document.addUndoableEditListener(undoer);
        document.insertString(0, fileContent.toString(), null);

        this.addCaretListener(new CaretListenerImpl());


    }
    
    /** Tries to undo the last change to the document.
     *  Checks to see if it can undo first.
     *  
     *  maybe make these two methods synchronized??
     */
    public void undo(){
        if(undoer.canUndo()){
            undoer.undo();
        }
    }
    /** Tries to redo the last change to the document.
     *  Checks to see if it can redo first.
     * 
     */
    public void redo(){
        if(undoer.canRedo()){
            undoer.redo();
        }
    }
    public static void main(String[] args) {
        StyledUndoPane test = new StyledUndoPane(testStr);
        
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

    /** Hidden class used for testing. Can listen to undo/redo
     * events and try undo/redoing the document content.
     * 
     */
    private static class PaneTester implements ActionListener{
        private StyledUndoPane s;
        private PaneTester(StyledUndoPane s0){s = s0;}
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("undo")){
                s.undo();
                }
            else{
                s.redo();
            }
        }
    }
                
    private class CaretListenerImpl implements CaretListener {

        public CaretListenerImpl() {
        }

        final Object isReindentingMutex = new Object();
        boolean isReindenting = false;
        int lastPos = -1;

        @Override
        public void caretUpdate(CaretEvent e) {
            if (lastPos != e.getDot()) {
                synchronized (isReindentingMutex) {
                    if (isReindenting) {
                        return;
                    }
                    isReindenting = true;
                }

            lastPos = e.getDot();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        document.highlight();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Lumberjack.log(Lumberjack.Level.WARN, e);
                    } finally {
                        // We don't need to bother locking the release
                        synchronized (isReindentingMutex) {
                            isReindenting = false;
                        }
                    }
                }
            });
            }
        }
    }

}

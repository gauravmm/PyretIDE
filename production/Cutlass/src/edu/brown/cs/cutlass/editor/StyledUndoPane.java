/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.util.Lumberjack;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.StyledEditorKit;

/**
 * An extended JEditorPane. Has the following features: ~Highlights contents
 * according to highlighting rules set by the SyntaxHighlighter class.
 * ~Maintains an undo/redo stack and has visible undo/redo methods
 *
 * @author miles
 */
public class StyledUndoPane extends JEditorPane{

    private final PyretStyledDocument document;

    public StyledUndoPane(CharSequence fileContent) {
        super();

        document = new PyretStyledDocument(this);
        //undoer = new UndoManager();
        
        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        
        //document.addUndoableEditListener(undoer);
        document.insertString(0, fileContent.toString(), null);
        this.addKeyListener(new EditorKeyListener(document));

        this.addCaretListener(new CaretListenerImpl());


    }
    
    /** Tries to undo the last change to the document.
     *  Checks to see if it can undo first.
     *  
     *  maybe make these two methods synchronized??
     */
    public void undo(){
        if(document.undoer.canUndo()){
            document.undoer.undo();
        }
    }
    /** Tries to redo the last change to the document.
     *  Checks to see if it can redo first.
     * 
     */
    public void redo(){
        if(document.undoer.canRedo()){
           document.undoer.redo();
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

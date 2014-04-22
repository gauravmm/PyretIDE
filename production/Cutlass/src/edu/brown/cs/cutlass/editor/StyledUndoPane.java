/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
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
public class StyledUndoPane extends JEditorPane implements PyretHighlightedListener, EditorJumpToClient {

    private final PyretStyledDocument document;
    private final PyretHighlightedListener listener;

    public StyledUndoPane(CharSequence fileContent, PyretHighlightedListener listener) {
        super();
        this.listener = listener;
        
        // Document setup
        document = new PyretStyledDocument(this);
        this.setEditorKit(new StyledEditorKit());
        this.setDocument(document);
        document.insertString(0, fileContent.toString(), null);
        
        // Add listeners after content is updated.
        this.addKeyListener(new EditorKeyListener(document));
        this.addCaretListener(new CaretListenerImpl());
    }

    /**
     * Tries to undo the last change to the document. Checks to see if it can
     * undo first.
     *
     * maybe make these two methods synchronized??
     */
    public void undo() {
        if (document.undoer.canUndo()) {
            document.undoer.undo();
        }
    }

    /**
     * Tries to redo the last change to the document. Checks to see if it can
     * redo first.
     *
     */
    public void redo() {
        if (document.undoer.canRedo()) {
            document.undoer.redo();
        }
    }

    @Override
    public void handleJumpTo(int offset) {
        this.getCaret().setDot(offset);
    }

    @Override
    public EditorJumpTo createJumpTo(int offset) {
        return new EditorJumpTo(this, offset);
    }

    @Override
    public void highlighted(TokenParserOutput output, Option<Token> currentToken, EditorJumpToClient client) {
        listener.highlighted(output, currentToken, client);
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

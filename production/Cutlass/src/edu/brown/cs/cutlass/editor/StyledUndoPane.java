/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.ui.FindClient;
import edu.brown.cs.cutlass.ui.FrmFinder;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
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
public class StyledUndoPane extends JEditorPane implements PyretHighlightedListener, EditorJumpToClient, FindClient {
    
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
        // Absorb the tab keypress
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
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
        this.requestFocusInWindow();
    }
    
    @Override
    public EditorJumpTo createJumpTo(int offset) {
        return new EditorJumpTo(this, offset);
    }
    
    @Override
    public void highlighted(TokenParserOutput output, Option<Token> currentToken, EditorJumpToClient client) {
        listener.highlighted(output, currentToken, client);
    }
    
    public List<Integer> getLineStartOffsets() {
        return document.getLineStartOffsets();
    }
    
    public void reindent() {
        document.highlightAndIndent();
    }
    
    private Option<Pair<Integer, Integer>> locateNextMatch(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find) {
        // Find current cursor pos:
        int startPos = this.getCaret().getDot();
        Pattern toMatch = null;
        switch (type) {
            case LITERAL:
                find = Pattern.quote(find);
                if (wholeWords) {
                    find = "\\b" + find + "\\b";
                }
                try {
                    if (matchCase) {
                        toMatch = Pattern.compile(find);
                    } else {
                        toMatch = Pattern.compile(find, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                    }
                } catch (PatternSyntaxException ex) {
                    Lumberjack.log(Lumberjack.Level.ERROR, ex);
                }
                break;
            case WILDCARD:
                return new Option<>();
            case REGEXP:
                try {
                    if (matchCase) {
                        toMatch = Pattern.compile(find);
                    } else {
                        toMatch = Pattern.compile(find, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
                    }
                } catch (PatternSyntaxException ex) {
                    return new Option<>();
                }
                break;
            default:
                throw new AssertionError(type.name());
        }
        return new Option<>();
    }
    
    @Override
    public boolean findNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean replaceNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean replaceAll(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

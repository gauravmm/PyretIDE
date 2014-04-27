/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.ui.FindClient;
import edu.brown.cs.cutlass.ui.FrmFinder;
import edu.brown.cs.cutlass.ui.FrmFinder.FindType;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

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
        document.undoer.die();
        
        // Add listeners after content is updated.
        this.addKeyListener(new EditorKeyListener(document));
        this.addCaretListener(new CaretListenerImpl());
        // Absorb the tab keypress
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK), new AbstractAction() {
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
        System.out.println("redo attempt");
        System.out.println(document.undoer.canRedo());
        
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
        Option<Pattern> p = this.createPattern(type, matchCase, forwards, wholeWords, find);
        if (p.hasData()) {
            try {
                return locateNextMatchHelper(p.getData(), forwards, startPos, true);
            } catch (BadLocationException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
            }
        }

        return new Option<>();
    }

    private Option<Pattern> createPattern(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find) {
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
                break;
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

        if (toMatch == null) {
            return new Option<>();
        }

        return new Option<>(toMatch);
    }

    private Option<Pair<Integer, Integer>> locateNextMatchHelper(Pattern p, boolean forwards, int cursorPos, boolean notYetWrapped) throws BadLocationException {
        if (!forwards) {
            throw new UnsupportedOperationException("Only forward matches supported.");
        }
        int length = document.getLength();
        int startPos = -1;
        int endPos = -1;

        // This is the obvious, not the quick way to do this:
        if (notYetWrapped) {
            startPos = cursorPos;
            endPos = length;
        } else {
            startPos = 0;
            endPos = cursorPos;
        }

        assert startPos >= 0;
        assert endPos >= 0;

        if (startPos >= endPos) {
            if (notYetWrapped) {
                return locateNextMatchHelper(p, forwards, cursorPos, false);
            } else {
                return new Option<>();
            }
        } else {
            Matcher m = p.matcher(document.getText(0, document.getLength()));
            m.region(startPos, endPos);

            if (m.find(startPos)) {
                return new Option<>(new Pair<>(m.start(), m.end()));
            } else {
                if (notYetWrapped) {
                    return locateNextMatchHelper(p, forwards, cursorPos, false);
                } else {
                    return new Option<>();
                }
            }
        }
    }

    @Override
    public boolean findNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find) {
        Option<Pair<Integer, Integer>> locateNextMatch = this.locateNextMatch(type, matchCase, forwards, wholeWords, find);
        if (locateNextMatch.hasData()) {
            Pair<Integer, Integer> nextMatch = locateNextMatch.getData();
            this.getCaret().setDot(nextMatch.getX());
            this.getCaret().moveDot(nextMatch.getY());
            this.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean replaceNext(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        Option<Pair<Integer, Integer>> locateNextMatch = this.locateNextMatch(type, matchCase, forwards, wholeWords, find);
        if (locateNextMatch.hasData()) {
            try {
                Pair<Integer, Integer> nextMatch = locateNextMatch.getData();
                String text = document.getText(0, document.getLength());
                this.setText(text.substring(0, nextMatch.getX()) + replace + text.substring(nextMatch.getY()));
                this.getCaret().setDot(nextMatch.getX());
                this.getCaret().moveDot(nextMatch.getX() + replace.length());
                this.requestFocus();
                return true;
            } catch (BadLocationException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean replaceAll(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        Option<Pattern> createPattern = this.createPattern(type, matchCase, forwards, wholeWords, find);
        if (createPattern.hasData()) {
            try {
                this.setText(document.getText(0, document.getLength()).replaceAll(createPattern.getData().pattern(), type == FindType.REGEXP ? Matcher.quoteReplacement(replace) : replace));
                this.requestFocus();
                return true;
            } catch (BadLocationException ex) {
                Lumberjack.log(Lumberjack.Level.ERROR, ex);
            }
        }
        return false;
    }

    void showCallGraph() {
        document.showCallGraph();
    }

    void toggleComment() {
        try {
            List<Integer> lineStartOffsets = this.getLineStartOffsets();
            // Get selection:
            Integer curSt = this.getCaret().getDot();
            Integer curEnd = this.getCaret().getMark();
            int lnSt = processBinarySearch(Collections.binarySearch(lineStartOffsets, curSt));
            int lnEnd = processBinarySearch(Collections.binarySearch(lineStartOffsets, curEnd));

            String str = document.getText(0, document.getLength());

            for (int ln = Math.max(lnSt, lnEnd); ln >= Math.min(lnSt, lnEnd); --ln) {
                Integer pos = lineStartOffsets.get(ln);
                if (pos + 1 < str.length()) {
                    if (str.substring(pos, pos + 1).equals("#")) {
                        str = str.substring(0, pos).concat(str.substring(pos + 1));
                    } else {
                        str = str.substring(0, pos).concat("#").concat(str.substring(pos));
                    }
                } else {
                    str = str.substring(0, pos).concat("#").concat(str.substring(pos));
                }
            }
            
            document.remove(0, document.getLength());
            document.insertString(0, str, null);
        } catch (BadLocationException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
    }

    private int processBinarySearch(int v) {
        if (v >= 0) {
            return v;
        } else {
            return -(v + 1) - 1;
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

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeDefault;
import edu.brown.cs.cutlass.ui.FindClient;
import edu.brown.cs.cutlass.ui.FrmFinder;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
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
     * Just a link to the document's undo method.
     */
    public void undo() {
        document.undo();
    }

    /**
     * Just a link to the document's redo method.
     */
    public void redo() {
        document.redo();
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
        switch (type) {
            case LITERAL:
                find = Pattern.quote(find);
                if (wholeWords) {
                    find = "\\b" + find + "\\b";
                }
                break;
            case WILDCARD:
                find = Pattern.quote(find);
                if (wholeWords) {
                    find = "\\b" + find + "\\b";
                }
                find = find.replaceAll("\\*", "\\\\E" + TokenTypeDefault.wordCharRegex + "*\\\\Q");
                find = find.replaceAll("\\?", "\\\\E" + TokenTypeDefault.wordCharRegex + "\\\\Q");
                find = find.replaceAll("\\\\Q\\\\E", "");
                break;
            case REGEXP:
            default:
                throw new AssertionError(type.name());
        }

        Pattern toMatch = null;
        try {
            if (matchCase) {
                toMatch = Pattern.compile(find);
            } else {
                toMatch = Pattern.compile(find, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            }
        } catch (PatternSyntaxException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
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
        if (this.getSelectedText() != null) {
            this.setCaretPosition(this.getCaretPosition() + 1);
        }
        Option<Pair<Integer, Integer>> locateNextMatch = this.locateNextMatch(type, matchCase, forwards, wholeWords, find);
        if (locateNextMatch.hasData()) {
            Pair<Integer, Integer> nextMatch = locateNextMatch.getData();
            this.getCaret().setDot(nextMatch.getY());
            this.getCaret().moveDot(nextMatch.getX());
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
            //try {
            Pair<Integer, Integer> nextMatch = locateNextMatch.getData();
            boolean multipleOps = document.undoer.getIsHighlighting();
            int offset = 0;
            if (!multipleOps) {
                document.insertString(0, " ", null);
                document.undoer.setIsHighlighting(true);
                offset = 1;
            }
            //Offset by one due to dummy insertion at beginning
            document.replace(nextMatch.getX() + offset, nextMatch.getY() + offset, replace, null);
            if (!multipleOps) {
                document.remove(0, 1);
                document.undoer.setIsHighlighting(false);
            }
            this.requestFocus();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean replaceAll(FrmFinder.FindType type, boolean matchCase, boolean forwards, boolean wholeWords, String find, String replace) {
        document.insertString(0, " ", null);
        document.undoer.setIsHighlighting(true);

        boolean replacedSomething = true;
        while (replacedSomething) {
            replacedSomething = this.replaceNext(type, matchCase, forwards, wholeWords, find, replace);
        }

        document.remove(0, 1);
        document.undoer.setIsHighlighting(false);

        return true;
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
            int currPos = this.getCaretPosition();
            this.replaceNext(FrmFinder.FindType.LITERAL, false, true, false, document.getText(0, document.getLength()), str);
            this.setCaretPosition(currPos + Math.abs(lnEnd - lnSt) + 1);
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

        int temp;

        public CaretListenerImpl() {
            temp = 0;
        }

        final Object isReindentingMutex = new Object();
        boolean isReindenting = false;
        int lastPos = -1;

        @Override
        public void caretUpdate(CaretEvent e) {
            temp++;
            if (true || lastPos != e.getDot()) {
                synchronized (isReindentingMutex) {
                    if (isReindenting) {
                        return;
                    }
                    isReindenting = true;
                }

                lastPos = e.getDot();
                boolean actuallyRun = document.isUndoing();
                SwingUtilities.invokeLater(new HighlightRunnable(actuallyRun));
            }
        }

        private class HighlightRunnable implements Runnable {

            boolean wasUndoing;

            private HighlightRunnable(boolean wasUndoing0) {
                wasUndoing = wasUndoing0;
            }

            @Override
            public void run() {
                if (wasUndoing) {
                    synchronized (isReindentingMutex) {
                        isReindenting = false;
                    }
                    return;
                }
                try {
                    document.highlight();
                } catch (Exception e) {
                    Lumberjack.log(Lumberjack.Level.WARN, e);
                } finally {
                    synchronized (isReindentingMutex) {
                        isReindenting = false;
                    }
                }
            }
        }
    }
}

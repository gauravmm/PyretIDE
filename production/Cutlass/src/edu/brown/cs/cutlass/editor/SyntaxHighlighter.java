/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.PyretFeatureExtractor;
import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPaired;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPairedOpening;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParser;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePaired;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleAnnotation;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyleError;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePaired;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeAnnotation;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeDefault;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypePairedOpenParen;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.text.BadLocationException;

/**
 *
 * @author dilip
 */
public class SyntaxHighlighter {

    private PyretStyledDocument sdoc;
    private final StyledUndoPane listener;

    SyntaxHighlighter(PyretStyledDocument d, StyledUndoPane l) {
        this.sdoc = addAllStyles(d);
        this.listener = l;
    }

    /**
     * Highlights the current document
     *
     *
     */
    public void highlight() {
        highlight(-1, false);
    }

    /**
     *
     * @param position The position of the dot
     * @param reindent If true, reindent the code while you're at it.
     * @return The position of the dot, if reindenting is requested. 0
     * otherwise.
     */
    public int highlight(int position, boolean reindent) {

        //Convert entire contents of document into Lines
        TokenParserOutput parseTokens;
        try {
            parseTokens = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength()));
        } catch (BadLocationException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
            return 0;
        }
        List<Line> tokenLines = parseTokens.getTokenLines();

        //Clear the document of text
        sdoc.removeWithoutHighlight(0, sdoc.getLength());

        // Format the Annotations
        Collection<List<Token>> annotations = parseTokens.getTokenCollected().get(TokenTypeAnnotation.getInstance()).values();
        for (List<Token> anntype : annotations) {
            for (Token ann : anntype) {
                processAnnotations(ann);
            }
        }

        // Process document using current token
        // NOTE: MUST NOT CHANGE reindent here:
        Option<Token> opt = getCurrentToken(tokenLines, position);
        if (!reindent) {
            if (opt.hasData()) {
                Token t = opt.getData();
                if (t.getType() instanceof TokenTypePaired) {
                    TokenPaired ttp = (TokenPaired) t;
                    if (ttp.other != null) {
                        ttp.setStyle(TokenStylePaired.getInstance());
                        ttp.other.setStyle(TokenStylePaired.getInstance());
                    }
                } else if (t.getType() instanceof TokenTypeDefault) {
                    // Highlight each type of TokenTypeDefault with the same value
                    List<Token> get = parseTokens.getTokenCollected().get(TokenTypeDefault.getInstance()).get(t.getValue());
                    if (get != null) {
                        for (Token similarTokens : get) {
                            similarTokens.setStyle(TokenStylePaired.getInstance());
                        }
                    }
                }
            }
        }

        // Handle reindent here:
        Pair<Integer, Integer> charsBeforeAfter = new Pair(0, position);
        if (reindent) {
            List<Line> indentedLines = new ArrayList<>();
            for (Line l : tokenLines) {
                Pair<Line, Pair<Integer, Integer>> toIndentedLine = l.toIndentedLine(charsBeforeAfter.getX(), charsBeforeAfter.getY());
                indentedLines.add(toIndentedLine.getX());
                charsBeforeAfter = toIndentedLine.getY();
            }
            tokenLines = indentedLines;
        }

        Iterator<Line> it = tokenLines.iterator();

        while (it.hasNext()) {
            Line l = it.next();
            List<Token> line_tokens = l.getContents();
            for (Token t : line_tokens) {
                sdoc.insertStringWithoutHighlight(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
            }
        }

        listener.highlighted(parseTokens, opt, listener);
        return charsBeforeAfter.getX() + charsBeforeAfter.getY();
    }

    public void updateDocument(PyretStyledDocument newdoc) {
        this.sdoc = addAllStyles(newdoc);
    }

    private PyretStyledDocument addAllStyles(PyretStyledDocument d) {
        for (TokenStyle ts : TokenStyles.getAllStyles()) {
            ts.applyTo(d);
        }
        return d;
    }

    private void processAnnotations(Token ann) {
        Option<Token> wordToken = PyretFeatureExtractor.getNextToken(ann, TokenTypeDefault.getInstance());
        if (wordToken.hasData()) {
            ann = wordToken.getData();
            ann.setStyle(TokenStyleAnnotation.getInstance());
        }

        Option<Token> openParenToken = PyretFeatureExtractor.getNextToken(ann, TokenTypePairedOpenParen.getInstance());
        if (openParenToken.hasData()) {
            TokenPairedOpening opParen = (TokenPairedOpening) openParenToken.getData();
            opParen.setStyle(TokenStyleAnnotation.getInstance());
            if (opParen.other != null) {
                Token currTok = opParen;
                // Set the entire parenthesis range to be of style Annotation:
                while (currTok != opParen.other) {
                    currTok = currTok.getNextToken();
                    currTok.setStyle(TokenStyleAnnotation.getInstance());
                }
            }
        }

        // If no annotation is found:
        if (!(wordToken.hasData() || openParenToken.hasData())) {
            ann.setStyle(TokenStyleError.getInstance());
        }
    }

    private Option<Token> getCurrentToken(List<Line> lines, int pos) {
        if (pos < 0) {
            return new Option<>();
        }
        // Check if the cursor is in this position:

        int linenum = Collections.binarySearch(lines, new Integer(pos));
        if (linenum < 0) {
            return new Option<>();
        }
        Line currLine = lines.get(linenum);

        int toknum = Collections.binarySearch(currLine.getContents(), new Integer(pos));
        if (toknum < 0) {
            return new Option<>();
        }
        Token t = currLine.getContents().get(toknum);

        if (t.getOffset() <= pos && pos < (t.getOffset() + t.getLength())) {
            return new Option<>(t);
        } else {
            //System.out.format("%d\t%d\t%d\t%s%n", t.getOffset(), t.getLength(), pos, t.getValue());
            throw new IllegalStateException("This should not be possible");
        }
    }
}

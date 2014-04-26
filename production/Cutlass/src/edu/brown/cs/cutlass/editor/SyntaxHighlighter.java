/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.PyretFeatureExtractor;
import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.LineIndentMetadata;
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
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeSinglePunct;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeWhitespace;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import edu.brown.cs.cutlass.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.text.BadLocationException;

/**
 *
 * @author dilip
 */
public class SyntaxHighlighter {

    private PyretStyledDocument sdoc;
    private final StyledUndoPane listener;
    private List<Integer> lastLineStartOffsets = Arrays.asList(0);

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
        LineIndentMetadata lineIM = new LineIndentMetadata(0, position, true);
        if (reindent) {
            List<Line> indentedLines = new ArrayList<>();
            for (Line l : tokenLines) {
                Pair<Line, LineIndentMetadata> toIndentedLine = l.toIndentedLine(lineIM);
                indentedLines.add(toIndentedLine.getX());
                lineIM = toIndentedLine.getY();
            }
            tokenLines = indentedLines;
        }

        List<Integer> newLineStartOffsets = new ArrayList<>();
        newLineStartOffsets.add(0);
        Iterator<Line> it = tokenLines.iterator();
        while (it.hasNext()) {
            Line l = it.next();
            List<Token> line_tokens = l.getContents();
            for (Token t : line_tokens) {
                sdoc.insertStringWithoutHighlight(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
                if (t.getType() instanceof TokenTypeWhitespace && t.getValue().endsWith(Line.LINE_TERMINATOR)) {
                    newLineStartOffsets.add(sdoc.getLength());
                }
            }
        }
        lastLineStartOffsets = Collections.unmodifiableList(newLineStartOffsets);

        listener.highlighted(parseTokens, opt, listener);
        return lineIM.charsBefore + lineIM.charsAfter;
    }

    public void showCallGraph() {
        try {
            listener.highlighted(TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength())), new Option<Token>(), listener);
        } catch (BadLocationException ex) {
            Lumberjack.log(Lumberjack.Level.ERROR, ex);
        }
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

        // Skip <Patameterized type> 
        Option<Token> openAngle = PyretFeatureExtractor.getNextToken(ann, TokenTypeSinglePunct.getInstance());
        if (openAngle.hasData()) {
            Token opAngle = openAngle.getData();
            if (opAngle.getValue().equals("<")) {

                opAngle.setStyle(TokenStyleAnnotation.getInstance());

                // Get a list of all tokens until the matching closing angle brackets, pairing the angle brackets along the way.
                // Don't actually change the metadata in the tokens. We'll save that for when we write a full parser
                int angleBracketCount = 1;
                Token currToken = opAngle;
                while (true) {
                    Option<Token> nextToken = PyretFeatureExtractor.getNextToken(currToken, TokenTypeSinglePunct.getInstance(), false);
                    if (nextToken.hasData()) {
                        switch (nextToken.getData().getValue()) {
                            case "<":
                                angleBracketCount++;
                                break;
                            case ">":
                                angleBracketCount--;
                                break;
                            default:
                                break;
                        }
                        if (angleBracketCount == 0) {
                            // Perform the highlighting
                            Token endingToken = nextToken.getData();
                            Token styleLoopToken = opAngle;
                            styleLoopToken.setStyle(TokenStyleAnnotation.getInstance());
                            do {
                                styleLoopToken = styleLoopToken.getNextToken();
                                styleLoopToken.setStyle(TokenStyleAnnotation.getInstance());
                            } while (styleLoopToken != endingToken);
                            ann = endingToken; // Advance the current ann pointer to the end of the <> sequence.
                            break; // Check for predicates
                        }
                    } else {
                        // Error! set the angle bracket as unclosed and return
                        opAngle.setStyle(TokenStyleError.getInstance());
                        return;
                    }
                    currToken = nextToken.getData();
                }
            } else {
                // This is when a single punctuation exists that should not be here. Ignore it.
                return;
            }
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

    public List<Integer> getLastLineStartOffsets() {
        return lastLineStartOffsets;
    }
}

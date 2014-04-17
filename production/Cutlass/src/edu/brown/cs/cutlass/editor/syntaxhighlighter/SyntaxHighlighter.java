/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.syntaxhighlighter;

import edu.brown.cs.cutlass.editor.PyretStyledDocument;
import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenPaired;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParser;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.parser.tokenizer.TokenTypePaired;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStylePaired;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;
import edu.brown.cs.cutlass.parser.tokenizer.tokentypes.TokenTypeDefault;
import edu.brown.cs.cutlass.util.Lumberjack;
import edu.brown.cs.cutlass.util.Option;
import java.util.Collections;
import java.util.List;
import javax.swing.text.BadLocationException;

/**
 *
 * @author dilip
 */
public class SyntaxHighlighter {

    private PyretStyledDocument sdoc;

    public SyntaxHighlighter(PyretStyledDocument d) {
        this.sdoc = addAllStyles(d);
    }

    /**
     * Highlights the current document
     *
     *
     */
    public void highlight() {
        highlight(-1);
    }

    public void highlight(int position) {
        try {
            //Convert entire contents of document into Lines
            TokenParserOutput parseTokens = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength()));
            List<Line> tokenLines = parseTokens.getTokenLines();

            //Clear the document of text
            sdoc.removeWithoutHighlight(0, sdoc.getLength());
            // Process document using current token
            Option<Token> opt = getCurrentToken(tokenLines, position);
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
                }
            }

            //Iterate over every Line of document
            for (Line l : tokenLines) {
                List<Token> line_tokens = l.getContents();

                //Iterate over every Token of every Line
                for (Token t : line_tokens) {
                    //Insert the string represented by each token with its appropriate color
                    sdoc.insertStringWithoutHighlight(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
                }
                sdoc.insertStringWithoutHighlight(sdoc.getLength(), Line.LINE_TERMINATOR, null);

            }
            //sdoc = tempDoc;
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
            // Do bracket highlighting
        } else {
            
        }
        return new Option<>();
    }
}

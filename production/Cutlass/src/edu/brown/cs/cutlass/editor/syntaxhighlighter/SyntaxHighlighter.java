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
import edu.brown.cs.cutlass.util.Lumberjack;
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
        System.err.println(position);
        try {
            //Convert entire contents of document into Lines
            TokenParserOutput parseTokens = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength()));
            List<Line> token_lines = parseTokens.getTokenLines();

            //Clear the document of text
            sdoc.removeWithoutHighlight(0, sdoc.getLength());

            //Iterate over every Line of document
            for (Line l : token_lines) {
                List<Token> line_tokens = l.getContents();

                //Iterate over every Token of every Line
                for (Token t : line_tokens) {
                    // Check if the cursor is in this position:
                    if (t.getOffset() <= position && position < (t.getOffset() + t.getLength() + 1)) {
                        // Do bracket highlighting
                        if (t.getType() instanceof TokenTypePaired) {
                            TokenPaired ttp = (TokenPaired) t;
                            if (ttp.other != null) {
                                ttp.setStyle(TokenStylePaired.getInstance());
                                ttp.other.setStyle(TokenStylePaired.getInstance());
                            }
                        }
                    }
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
}

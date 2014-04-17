/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.syntaxhighlighter;

import edu.brown.cs.cutlass.editor.PyretStyledDocument;
import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParser;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyle;
import edu.brown.cs.cutlass.parser.tokenizer.styles.TokenStyles;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /** Highlights the current document
     * 
     * 
     */
    public void highlight() {
        try {
            //Convert entire contents of document into Lines
            List<Line> token_lines = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength())).getTokenLines();

            //Clear the document of text
            sdoc.removeWithoutHighlight(0, sdoc.getLength());

            //Iterate over every Line of document
            for (Line l : token_lines) {
                List<Token> line_tokens = l.getContents();
                
                
                //Iterate over every Token of every Line
                for (Token t : line_tokens) {
                    // Check if the cursor is in this position:
                    
                    //Insert the string represented by each token with its appropriate color
                    sdoc.insertStringWithoutHighlight(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
                }
                sdoc.insertStringWithoutHighlight(sdoc.getLength(), Line.LINE_TERMINATOR, null);

            }
            //sdoc = tempDoc;
        } catch (BadLocationException ex) {
            System.out.println("uh oh");
            Logger.getLogger(SyntaxHighlighter.class.getName()).log(Level.SEVERE, null, ex);
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

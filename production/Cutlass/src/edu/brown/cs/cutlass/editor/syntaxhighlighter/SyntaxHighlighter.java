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
import javax.swing.text.StyledDocument;

/**
 *
 * @author dilip
 */
public class SyntaxHighlighter {

    private PyretStyledDocument sdoc;

    public SyntaxHighlighter(PyretStyledDocument d) {
        this.sdoc = addAllStyles(d);
    }

    /** Highlights a specific entered string
     * 
     * @Param put the String to highlight and replace the current document with
     */
    public void highlight(String put) {
        //Convert entire contents of document into Lines
        List<Line> token_lines = TokenParser.parseTokens(put).getTokenLines();

        //Clear the document of text
        sdoc.fakeRemove(0, sdoc.getLength());

        //Iterate over every Line of document
        for (Line l : token_lines) {
            List<Token> line_tokens = l.getContents();

            //sdoc.insertString(sdoc.getLength(), l.toIndentedString(), null);
            //Iterate over every Token of every Line
            for (Token t : line_tokens) {
                //Insert the string represented by each token with its appropriate color
                sdoc.addString(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
                System.out.println(t.getType().getClass().getCanonicalName());
                System.out.println(t.getTokenStyle().getStyle());
                System.out.println();
                //System.out.println(t.getTokenStyle().getName());
            }
            sdoc.addString(sdoc.getLength(), "\n", null);
        }
        //sdoc = tempDoc;
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
            sdoc.fakeRemove(0, sdoc.getLength());

            //Iterate over every Line of document
            for (Line l : token_lines) {
                List<Token> line_tokens = l.getContents();
                
                
                //Iterate over every Token of every Line
                for (Token t : line_tokens) {
                    //Insert the string represented by each token with its appropriate color
                    sdoc.addString(sdoc.getLength(), t.getValue(), t.getTokenStyle().getStyle());
                    //System.out.println(t.getType().getClass().getCanonicalName());
                    //System.out.println(t.getTokenStyle().getStyle());
                    //System.out.println();
                    //System.out.println(t.getTokenStyle().getName());
                }
                sdoc.addString(sdoc.getLength(), "\n", null);

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

/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor.syntaxhighlighter;

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
    private final StyledDocument sdoc;
    
    public SyntaxHighlighter(StyledDocument d){
        this.sdoc = addAllStyles(d);
    }
    
    public void highlight(){
        try {
            //Convert entire contents of document into Lines
            List<Line> token_lines = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength())).getTokenLines();
            //Clear the document of text
            sdoc.remove(0, sdoc.getLength());
            //Iterate over every Line of document
            for(Line l : token_lines){
                List<Token> line_tokens = l.getContents();
                //Iterate over every Token of every Line
                for(Token t : line_tokens){
                    //Insert the string represented by each token with its appropriate color
                    sdoc.insertString(t.getOffset(),t.getValue(),t.getTokenStyle().getStyle());
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(SyntaxHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private StyledDocument addAllStyles(StyledDocument d) {
        for(TokenStyle ts : TokenStyles.getAllStyles()){
            ts.applyTo(d);
        }
        return d;
    }
}

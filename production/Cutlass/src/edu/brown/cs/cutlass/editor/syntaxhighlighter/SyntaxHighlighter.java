/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor.syntaxhighlighter;

import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.Token;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParser;
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
        this.sdoc = d;
    }
    
    public void highlight(){
        try {
            List<Line> token_lines = TokenParser.parseTokens(sdoc.getText(0, sdoc.getLength())).getTokenLines();
            sdoc.remove(0, sdoc.getLength());
            for(Line l : token_lines){
                List<Token> line_tokens = l.getContents();
                for(Token t : line_tokens){
                    sdoc.insertString(t.getOffset(),t.getValue(),t.getStyle());
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(SyntaxHighlighter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

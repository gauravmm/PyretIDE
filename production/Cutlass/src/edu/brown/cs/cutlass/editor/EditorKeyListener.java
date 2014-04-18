/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import edu.brown.cs.cutlass.parser.tokenizer.Line;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParser;
import edu.brown.cs.cutlass.parser.tokenizer.TokenParserOutput;
import edu.brown.cs.cutlass.util.Lumberjack;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.BadLocationException;

/**
 *
 * @author dilip
 */
class EditorKeyListener implements KeyListener {
    
    private final PyretStyledDocument psdoc;
    private int prevCode;

    public EditorKeyListener(PyretStyledDocument pd) {
        this.psdoc = pd;
        this.prevCode = 0;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int currCode = e.getKeyCode();
        //System.out.println(currCode);
        if(prevCode == 73){
            if(currCode == 17){
                //System.out.println("INDENT ALL");
                psdoc.highlightAndIndent();
            }
        }
        prevCode = currCode;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    private void indentAllLines(PyretStyledDocument psdoc) {

    }
    
}

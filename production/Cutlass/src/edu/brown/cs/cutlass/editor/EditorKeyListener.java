/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */

package edu.brown.cs.cutlass.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author dilip
 */
class EditorKeyListener implements KeyListener {
    
    private final PyretStyledDocument psdoc;

    public EditorKeyListener(PyretStyledDocument pd) {
        this.psdoc = pd;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ctrl + I or TAB auto-indent
        if((e.isControlDown() && e.getKeyCode() == KeyEvent.VK_I) || e.getKeyCode() == KeyEvent.VK_TAB){
            e.consume();
            psdoc.highlightAndIndent();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }
    
}

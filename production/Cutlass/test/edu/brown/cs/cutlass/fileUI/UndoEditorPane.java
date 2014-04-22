/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.brown.cs.cutlass.fileUI;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.undo.UndoManager;

/** An UndoEditorPane is a JEditorPane that has a few extra visible methods
 * relating to undo/redo stacks and obtaining the contained document.
 *
 * @author miles
 */
public class UndoEditorPane extends JEditorPane{
    
    private UndoManager undoer;
    private Document document;
    
    /** Default constructor for creating empty panes. Will be useful if we want
     * to be able to edit other file types (like plain files, for example).
     */
    public UndoEditorPane() {
        super();
        
        document = new PlainDocument();
        undoer = new UndoManager();
        
        setDocument(document);
        document.addUndoableEditListener(undoer);
    }
    
    /** CharSequence constructor for instantiating document content. Sets the
     * starting document (and thus starting visible text) to a document containing
     * the inputted character sequence.
     * 
     * @Param content The data to place in this UndoEditorPane's document.
     */
    public UndoEditorPane(CharSequence content) {
        super();
        
        document = new PlainDocument();
        undoer = new UndoManager();
        
        setDocument(document);
        setText(content.toString());
        document.addUndoableEditListener(undoer);
    }
    
    /**Creates an UndoEditorPane using a given document.
     * This might become more important when we try to have multiple
     * displays of the same file.
     * 
     * @param document0 the document to display and keep an undo/redo stack of.
     */
    public UndoEditorPane(Document document0){
        super();
        
        document = document0;
        undoer = new UndoManager();
        
        setDocument(document);
        document.addUndoableEditListener(undoer);
    }
    
    
    
    
    /** Tries to undo the last edit done to the content of this pane.
     *  Checks if there is anything to undo first, so this should not
     * throw a cannot undo error unless concurrent processes mess with the pane
     * at the same time.
     */
    public void undo(){
        if(undoer.canUndo()){
            undoer.undo();
        }
    }
    
    /** Tries to redo the last edit done to the content of this pane.
     *  Checks if there is anything to redo first, so this should not
     * throw a cannot redo error unless concurrent processes mess with the pane
     * at the same time.
     */
    public void redo(){
        if(undoer.canRedo()){
            undoer.redo();
        }
    }
    /* testing
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = 0; i < document.getLength(); i++){
            g.setColor(i % 2 == 0 ? Color.RED : Color.BLUE);
            g.drawString("a",0,i * 10);
        }
    }
    */
}

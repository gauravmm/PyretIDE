/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

/**
 * A utility class that allows any receiving object to tell the editor to jump
 * to a particular position in the file without knowing anything about the
 * editor.
 *
 * @author Gaurav Manek
 */
public class EditorJumpTo {

    private final EditorJumpToClient client;
    private final int offset; // We can always switch to another form of identification, such as a function name, etc.

    public EditorJumpTo(EditorJumpToClient client, int offset) {
        this.client = client;
        this.offset = offset;
    }
    
    public void jump(){
        client.handleJumpTo(offset);
    }

}

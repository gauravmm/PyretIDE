/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor;

import java.util.Objects;

/**
 * A utility class that allows any receiving object to tell the editor to jump
 * to a particular position in the file without knowing anything about the
 * editor.
 *
 * @author Gaurav Manek
 */
public class EditorJumpTo implements Comparable<EditorJumpTo> {

    private final EditorJumpToClient client;
    private final int offset; // We can always switch to another form of identification, such as a function name, etc.

    public EditorJumpTo(EditorJumpToClient client, int offset) {
        this.client = client;
        this.offset = offset;
    }
    
    public void jump(){
        client.handleJumpTo(offset);
    }

    @Override
    public int compareTo(EditorJumpTo o) {
        return Integer.compare(this.offset, o.offset);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.client);
        hash = 47 * hash + this.offset;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EditorJumpTo other = (EditorJumpTo) obj;
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (this.offset != other.offset) {
            return false;
        }
        return true;
    }
    
}

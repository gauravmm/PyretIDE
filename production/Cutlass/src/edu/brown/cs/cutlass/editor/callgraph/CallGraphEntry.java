/*
 * Cutlass - Pyret IDE
 * For CSCI 0320 Spring 2014, Term Project
 */
package edu.brown.cs.cutlass.editor.callgraph;

import edu.brown.cs.cutlass.editor.EditorJumpTo;
import edu.brown.cs.cutlass.util.Option;
import java.awt.Color;
import java.util.Objects;

/**
 * Holds the data necessary to render the QuickNavigation box.
 *
 * @author Gaurav Manek
 */
public class CallGraphEntry implements Comparable<CallGraphEntry> {

    public final String name;
    public final boolean isCurrent;
    public final boolean callsCurrent;
    public final boolean isCalledByCurrent;
    public final Option<String> dataOf;
    public final EditorJumpTo jumpTo;
    public final Option<Color> backgroundColor;

    public CallGraphEntry(String name, boolean isCurrent, boolean callsCurrent, boolean isCalledByCurrent, Option<String> dataOf, EditorJumpTo jumpTo, Option<Color> backgroundColor) {
        this.name = name;
        this.isCurrent = isCurrent;
        this.callsCurrent = callsCurrent;
        this.isCalledByCurrent = isCalledByCurrent;
        this.dataOf = dataOf;
        this.jumpTo = jumpTo;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public int compareTo(CallGraphEntry o) {
        // Sort all Data before functions
        int rv = -1 * Boolean.compare(this.dataOf.hasData(), o.dataOf.hasData());
        // Sort same category by offset
        if (rv == 0) {
            rv = jumpTo.compareTo(o.jumpTo);
        }
        return rv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + (this.isCurrent ? 1 : 0);
        hash = 79 * hash + (this.callsCurrent ? 1 : 0);
        hash = 79 * hash + (this.isCalledByCurrent ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.dataOf);
        hash = 79 * hash + Objects.hashCode(this.jumpTo);
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
        final CallGraphEntry other = (CallGraphEntry) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.isCurrent != other.isCurrent) {
            return false;
        }
        if (this.callsCurrent != other.callsCurrent) {
            return false;
        }
        if (this.isCalledByCurrent != other.isCalledByCurrent) {
            return false;
        }
        if (!Objects.equals(this.dataOf, other.dataOf)) {
            return false;
        }
        if (!Objects.equals(this.jumpTo, other.jumpTo)) {
            return false;
        }
        return true;
    }
    
}
